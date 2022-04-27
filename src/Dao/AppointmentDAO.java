package Dao;

import Model.Appointment;
import Model.Customers;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

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
     * @param
     */

    public static void updateAppointment(Appointment updateAppt){


        try {
            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start= ?, End=?, Customer_ID=?, Last_Updated_By=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1,updateAppt.getTitle());
            ps.setString(2,updateAppt.getDescription());
            ps.setString(3,updateAppt.getLocation());
            ps.setString(4,updateAppt.getType());
            ps.setTimestamp(5, Timestamp.valueOf(updateAppt.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(updateAppt.getEnd()));
            ps.setInt(7, updateAppt.getCustomer_ID());
            ps.setInt(8, updateAppt.getUser_ID());
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
            PreparedStatement psdc = JDBC.getConnection().prepareStatement(sql);
            psdc.setInt(1, appointmentID);

            psdc.execute();

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
     * Gets all customer names from the DB using the ID provided in the dropdown.
     * @param customerName
     * @return customerID
     */
    public static int getCustomerByName(String customerName){
        int customerID = 0;

        try {
            String sql = "SELECT Customer_ID FROM client_schedule.customers WHERE Customer_Name = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                customerID = rs.getInt("Customer_ID");
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return customerID;
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

                //rs.getTimestamp("Start"),
                //rs.getTimestamp("End"),
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
