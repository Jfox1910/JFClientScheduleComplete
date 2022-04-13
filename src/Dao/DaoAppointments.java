package Dao;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;
import java.time.LocalDateTime;
import Dao.DaoCustomers;

/**
 Appointment table database access
 */
public final class DaoAppointments {

    private static final Connection connection = JDBC.getConnection();
    public static DaoCustomers customer;

    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

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
                Timestamp appointmentStartTS = rs.getTimestamp("Start");
                Timestamp appointmentEndTS = rs.getTimestamp("End");
                LocalDateTime apptStartTimeCol = appointmentStartTS.toLocalDateTime();
                LocalDateTime apptEndTimeCol = appointmentEndTS.toLocalDateTime();
                int apptCustomerIDCol = rs.getInt("Customer_ID");
                int apptUserIDCol = rs.getInt("User_ID");
                Appointments appointment = new Appointments(apptIDCol, apptTitleCol, apptDescriptionCol, apptLocationCol, apptContactCol, apptTypeCol, apptStartTimeCol,
                        apptEndTimeCol, apptCustomerIDCol, apptUserIDCol);

                appointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }

    public static void newAppointment(String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, String createdBy, String updatedBy, int customerID, String user, int contactID){

//Selects the highest existing appointment ID then adds 1 to it to increment sequentially.
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

            String sql = "INSERT INTO appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = now(), Created_By = ?, last_update = now(), last_updated_by = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, appointmentID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, startTime);
            ps.setTimestamp(7, endTime);
            ps.setString(8, createdBy);
            ps.setString(9, updatedBy);
            ps.setInt(10, customerID);
            ps.setString(11, user);
            ps.setInt(12, contactID);
            //ps.setInt(6, userID);
            //ps.setString(7, JDBC.getLoginUser());

            ps.execute();
            ps.close();
            getAllAppointments();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


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
}
