package Dao;

import Model.Appointment;
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
    //private static final String tzOffset = DaoUser.loggedInUser().getTzOffset();
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


    public static void newAppointment(Appointment appt, Timestamp startTime, Timestamp endTime){

            int customerID = appt.getCustomerID();
            int contactID = appt.getContactID();
            String title = appt.getApptTitleCol();
            String description = appt.getApptDescriptionCol();
            String location = appt.getApptLocationCol();
            String type = appt.getApptTypeCol();
            Timestamp start = startTime;
            Timestamp end = endTime;

        try {
            //String sql = "INSERT INTO appointments VALUES(NULL,?,?,?,?,?,?,NULL,?,NULL,?,?,?,?)";
            String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            //String sql = "INSERT INTO appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = now(), Created_By = ?, last_update = now(), Customer_ID = ?, User_ID = ?, Contact_ID = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);


            ps.setTimestamp(5, startTime);
            ps.setTimestamp(6, endTime);
            ps.setString(7, Utils.getInstance().getLocalDateTimeString());
            ps.setString(8, DaoUser.getLoggedinUser().getUserName());
            ps.setString(9, Utils.getInstance().getLocalDateTimeString());
            ps.setString(10,DaoUser.getLoggedinUser().getUserName());
            ps.setInt(11, customerID);
            ps.setInt(12, DaoUser.getLoggedinUser().getUserId());
            ps.setInt(13, contactID);


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
