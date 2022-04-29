package Dao;

import Model.Appointment;
import Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReportsDAO {

    public static ObservableList getAppointmentsByType() {
        ObservableList<Reports> appointmentList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Customer_ID, monthname(start) AS month, type, COUNT(*) AS total FROM appointments GROUP BY month, type ORDER BY month ASC";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String month = rs.getString("month");
                String type = rs.getString("type");
                String total = rs.getString("total");
                Reports reports = new Reports(customerID, month, type, total);
                appointmentList.add(reports);

            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return appointmentList;
    }


    /**
     * Used to pull all existing appointments from the database.
     * @return appointments
     */
    public static ObservableList<Reports> getAllAppointmentsForContact( ){

        ObservableList<Reports> contactReport = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID, Contact_ID FROM appointments where Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
          // ps.setInt(1, contact);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("end").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");

                Reports reports = new Reports(appointmentID,title,appointmentType,description,start,end,customerID,contactID);
                contactReport.add(reports);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contactReport;
    }


    /**
     * Pulls all customers by their ID. Used in the making of reports.
     * @param id
     * @return apptList
     */
    public ObservableList<Appointment> getAllByCustomerId(int id) {

        String query = "SELECT * FROM appointments WHERE Customer_ID = ?;";
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("end").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");
            }
            return apptList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static ObservableList getTotalCustomers(){
        ObservableList<Reports> customerList = FXCollections.observableArrayList();
        try {
            //SELECT sum(vote_count) AS vote_count_sum FROM votes
            //String sql = "SELECT COUNT(Customer_ID, Appointment_ID) AS Customer_Total, SELECT COUNT(Appointment_ID) as Appointment_Total FROM appointments";
            String sql = "SELECT COUNT(Customer_ID) AS Customer_Total FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerTotal = rs.getInt("Customer_Total");
               // int appointmentTotal = rs.getInt("Appointment_Total");
                Reports reports = new Reports(customerTotal);
                customerList.add(reports);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

}
