package Dao;

import Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static ObservableList getTotalAppointments(){
        ObservableList<Reports> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT day(start) AS day, Customer_ID, COUNT(*) AS total FROM appointments GROUP BY day, type ORDER BY Customer_ID asc";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int day = rs.getInt("day");
                int customerID  = rs.getInt("Customer_ID");
                String total = rs.getString("TotalCustomerAppointments");
                Reports reports = new Reports(day, customerID, total);
                customerList.add(reports);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }
}
