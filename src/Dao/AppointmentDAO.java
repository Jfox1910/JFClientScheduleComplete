package Dao;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 Appointment table database access
 */
public final class AppointmentDAO {

    public static CustomersDao customer;


    /**
     * Used to pull all existing appointments from the database.
     * @return appointments
     */
    public static ObservableList<Appointment> getAllAppointments(){

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Appointment appt = createAppointment(rs);
                appointments.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointments;
    }

    public static ObservableList<Appointment> getAppointmentsByCustomer(int customerID){

        ObservableList<Appointment> customerAppointment = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ResultSet results = ps.executeQuery();

            while (results.next()) {
                customerAppointment.add(createAppointment(results));
            }

            return customerAppointment;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerAppointment;
    }


    /**
     * Used to add a new appointment to the database.
     * @param appt
     */
    public static void newAppointment(Appointment appt){

        Date date = new Date(new java.util.Date().getTime());

        LocalDateTime startTime;
        LocalDateTime endTime;
        startTime = appt.getStart();
        endTime = appt.getEnd();

        try {
            String sql = "insert into appointments set Title=?, Description=?, Location=?, Type=?, Start= ?, End=?, Create_Date=now(), Created_By=?, Customer_ID=?, User_ID=?, Contact_ID=?, Last_Updated_by=?, Last_Update=now();";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getLocation());
            ps.setString(4, appt.getType());
            ps.setTimestamp(5, Timestamp.valueOf(startTime));
            ps.setTimestamp(6, Timestamp.valueOf(endTime));
            ps.setString(7, UserDao.getLoggedinUser().getUserName());
            ps.setInt(8, appt.getCustomer_ID());
            ps.setInt(9, UserDao.getLoggedinUser().getUserId());
            ps.setInt(10, appt.getContact_ID());
            ps.setString(11, UserDao.getLoggedinUser().getUserName());

            ps.execute();
            ps.close();
            getAllAppointments();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


    /**
     * DB access used in the modify customer method.
     */

   public static void updateAppointment(Appointment updateAppt){

        try {
            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=NOW(), Last_Updated_By = ?, Customer_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1,updateAppt.getTitle());
            ps.setString(2,updateAppt.getDescription());
            ps.setString(3,updateAppt.getLocation());
            ps.setString(4,updateAppt.getType());
            ps.setTimestamp(5, Timestamp.valueOf(updateAppt.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(updateAppt.getEnd()));
            ps.setInt(7, updateAppt.getUser_ID());
            ps.setInt(8, updateAppt.getCustomer_ID());
            ps.setInt(9, updateAppt.getContact_ID());
            ps.setInt(10, updateAppt.getAppointment_ID());

            ps.execute();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


    /**
     * Deletes the selected APPOINTMENT from the DB.
     * @param appointmentID
     */
    public static void deleteAppointment(int appointmentID) {

        String sql = "DELETE from appointments where Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);

            ps.execute();

        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public static void deleteCustomerSpecificAppointments(int appointmentID) {

        String sql = "DELETE from appointments where Appointment_ID = ? AND Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);

            ps.execute();

        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }


    /**
     * Used to check for impeding appointments within the next 15 minutes of login.
     * @param now
     * @return impendingAppointments
     */
    public static ObservableList<Appointment> getImpendingAppt(LocalDateTime now) {

        ObservableList<Appointment> impendingAppointment = FXCollections.observableArrayList();
        LocalDateTime plusFifteen = now.plusMinutes(15);

        String query = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query );
            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setTimestamp(2, Timestamp.valueOf(plusFifteen));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment appointment = createAppointment(rs);
                impendingAppointment.add(appointment);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return impendingAppointment;
    }


    /**
     * Gets the appointments by dates. Used with the week/month radio buttons in the mainscreen.
     * @param begin
     * @param end
     * @return selectedAppointmentDates
     */
    public static ObservableList<Appointment> getAppointmentDates(LocalDate begin, LocalDate end) {

        ObservableList<Appointment> selectAppointmentDates = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(begin.atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(end.atTime(23, 59, 59, 999)));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = createAppointment(rs);
                selectAppointmentDates.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectAppointmentDates;
    }

    /**
     * Used in checking for overlapping appointments.
     * @param start
     * @param end
     * @param customerID
     * @param appointmentID
     * @return
     */
    public static boolean checkForOverlap(Timestamp start, Timestamp end, int customerID, int appointmentID)  {
        boolean overlap = false;

        try {

            String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND Appointment_ID <> ? AND (? = start OR ? = end) or ( ? < start and ? > end) or (? > start AND ? < end) or (? > start AND ? < end)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.setInt(2, appointmentID);
            ps.setTimestamp(3, start);
            ps.setTimestamp(4, end);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setTimestamp(7, start);
            ps.setTimestamp(8, start);
            ps.setTimestamp(9, end);
            ps.setTimestamp(10, end);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                overlap = true;
            }
            else {
                overlap = false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return overlap;
    }


    /**
     * New appointment object.
     * @param rs
     * @return
     * @throws SQLException
     */
    private static Appointment createAppointment(ResultSet rs) throws SQLException {

        Appointment appointment = new Appointment(
                rs.getInt("Appointment_ID"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getString("Location"),
                rs.getString("Type"),
                rs.getTimestamp("Start").toLocalDateTime(),
                rs.getTimestamp("End").toLocalDateTime(),
                rs.getInt("Customer_ID"),
                rs.getInt("User_ID"),
                rs.getInt("Contact_ID")

        );
        appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
        appointment.setCustomer_ID(appointment.getCustomer_ID());
        appointment.setUser_ID(UserDao.getLoggedinUser().getUserId());
        appointment.setContact_ID(rs.getInt("Contact_ID"));
        appointment.setUserName(UserDao.getLoggedinUser().getUserName());
    return appointment;

    }
}
