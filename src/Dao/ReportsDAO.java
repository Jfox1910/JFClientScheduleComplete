package Dao;

import Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsDAO {

/*    public static ObservableList getAppointmentsByType(){
        ObservableList<Reports> appointmentList = FXCollections.observableArrayList();
        try {
            String sql = "Select monthname(start) as month, type, count(*) as TotalNumberOfAppointments from appointments GROUP BY month, type ORDER BY month asc";
        }
    }*/

    public static ObservableList getTotalAppointments(){
        ObservableList<Reports> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT day(start) AS day, Customer_ID, COUNT(*) AS TotalCustomerAppointments FROM appointments GROUP BY day, type ORDER BY Customer_ID asc";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int day = rs.getInt("day");
                int customerID  = rs.getInt("Customer_ID");
                String totalCustomerAppointments = rs.getString("TotalCustomerAppointments");
                Reports reports = new Reports(day, customerID, totalCustomerAppointments);
                customerList.add(reports);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }
}
