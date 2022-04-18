package Dao;

import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;
import utils.Utils;

import java.sql.*;
import java.time.LocalDateTime;

/**
 Appointment table database access
 */
public final class DaoAppointments {

    private static final Connection connection = JDBC.getConnection();
    private static final String tzOffset = User.getCurrentTimezoneOffset();
    public static DaoCustomers customer;

    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int apptIDCol = rs.getInt("Appointment_ID");
                String apptTitleCol = rs.getString("Title");
                String apptDescriptionCol = rs.getString("Description");
                String apptLocationCol = rs.getString("Location");
                int apptContactCol = rs.getInt("Contact_ID");
                String apptTypeCol = rs.getString("Type");
                Timestamp apptStartTimeCol = rs.getTimestamp("Start");
                Timestamp apptEndTimeCol = rs.getTimestamp("End");
                int apptCustomerIDCol = rs.getInt("Customer_ID");
                int apptUserIDCol = rs.getInt("User_ID");
                Appointment appointment = new Appointment(apptIDCol, apptTitleCol, apptDescriptionCol, apptLocationCol, apptContactCol, apptTypeCol, apptStartTimeCol,
                        apptEndTimeCol, apptCustomerIDCol, apptUserIDCol);

                appointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }


    public static void newAppointment(Appointment appt, String startTime, int endTime){

            int customerID = appt.getCustomerID();
            int contactID = appt.getContactID();
            String title = appt.getApptTitleCol();
            String description = appt.getApptDescriptionCol();
            String location = appt.getApptLocationCol();
            String type = appt.getApptTypeCol();
            String start = startTime;
            int end = endTime;

        try {
            int appointmentID = 1;
            try {
                Statement id = connection.createStatement();
                ResultSet rs = id.executeQuery("select max(Appointment_ID) as Last_Appointment from appointments");
                if (rs.next()) {
                    appointmentID = rs.getInt("Last_Appointment") + 1;
                }
                id.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

           String sql = "insert into appointments set Appointment_ID=?, Title=?, Description=?, Location=?, Type=?, Start=convert_tz(?, ?, '+00:00'), End=(convert_tz(?, ?, '+00:00') + interval ? minute ), Create_Date=now(), Created_By=?, Customer_ID=?, User_ID=?, Contact_ID=?, Last_Updated_by=?, Last_Update=now();";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, appointmentID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setString(6, startTime);
            ps.setString(7, tzOffset);
            ps.setString(8, startTime);
            ps.setString(9, tzOffset);
            ps.setInt(10, endTime);
            ps.setString(11, DaoUser.getLoggedinUser().getUserName());
            ps.setInt(12, customerID);
            ps.setInt(13, DaoUser.getLoggedinUser().getUserId());
            ps.setInt(14, contactID);
            ps.setString(15,DaoUser.getLoggedinUser().getUserName());

            ps.execute();
            ps.close();
            getAllAppointments();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes the selected APPOINTMENT from the DB.
     * @param appointmentID
     */
    public static void deleteAppointment(int appointmentID) {
        try {

            String sql = "DELETE from appointments where Appointment_ID = ?";

            PreparedStatement psdc = JDBC.getConnection().prepareStatement(sql);
            psdc.setInt(1, appointmentID);

            psdc.execute();

        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public ObservableList<Appointment> getAllByCustomerId(int id) {

        String query = "SELECT * FROM appointments WHERE Customer_ID = ?;";

        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try {

            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptIDCol = rs.getInt("Appointment_ID");
                String apptTitleCol = rs.getString("Title");
                String apptDescriptionCol = rs.getString("Description");
                String apptLocationCol = rs.getString("Location");
                int apptContactCol = rs.getInt("Contact_ID");
                String apptTypeCol = rs.getString("Type");
                Timestamp apptStartTimeCol = rs.getTimestamp("Start");
                Timestamp apptEndTimeCol = rs.getTimestamp("End");
                int apptCustomerIDCol = rs.getInt("Customer_ID");
                int apptUserIDCol = rs.getInt("User_ID");
                Appointment appointment = new Appointment(apptIDCol, apptTitleCol, apptDescriptionCol, apptLocationCol, apptContactCol, apptTypeCol, apptStartTimeCol,
                        apptEndTimeCol, apptCustomerIDCol, apptUserIDCol);

                apptList.add(appointment);
            }

            return apptList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static ObservableList<Appointment> getAppointments(int customerID, int days) {
        Appointment appointment;
        ObservableList<Appointment> Appts = FXCollections.observableArrayList();
        try {
            String sql = "select  c.Contact_Name, a.*, convert_tz(a.Start, '+00:00', ?) as StartFormat, convert_tz(a.End, '+00:00', ?) as EndFormat\n" +
                    " from appointments a join contacts c \n" +
                    " on a.Contact_ID = c.Contact_ID \n" +
                    " where a.Customer_ID = ? \n" +
                    " AND a.Start between now() and (now() + interval ? day);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            assert ps != null;
            ps.setString(1, tzOffset);
            ps.setString(2, tzOffset);
            ps.setInt(3, customerID);
            ps.setInt(4, days);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int apptIDCol = rs.getInt("Appointment_ID");
                String apptTitleCol = rs.getString("Title");
                String apptDescriptionCol = rs.getString("Description");
                String apptLocationCol = rs.getString("Location");
                int apptContactCol = rs.getInt("Contact_ID");
                String apptTypeCol = rs.getString("Type");
                Timestamp apptStartTimeCol = rs.getTimestamp("Start");
                Timestamp apptEndTimeCol = rs.getTimestamp("End");
                int apptCustomerIDCol = rs.getInt("Customer_ID");
                int apptUserIDCol = rs.getInt("User_ID");
                Appointment appointments = new Appointment(apptIDCol, apptTitleCol, apptDescriptionCol, apptLocationCol, apptContactCol, apptTypeCol, apptStartTimeCol,
                        apptEndTimeCol, apptCustomerIDCol, apptUserIDCol);
                Appts.add(appointments);
            }
            ps.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
        return Appts;
    }



}
