package Dao;

import Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsDAO {

    public static ObservableList getTotalAppointments(){

        ObservableList<Reports> customerTotalList = FXCollections.observableArrayList();

        try {

            String sql = "Select year(start) as year, Customer_ID, count(*) as TotalCustomerAppointments from appointments GROUP BY day, type ORDER BY Customer_ID asc";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int day = rs.getInt("year");
                int customerID  = rs.getInt("Customer_ID");
                String totalCustomerAppointments = rs.getString("TotalCustomerAppointments");
                Reports reports = new Reports(day, customerID, totalCustomerAppointments);
                customerTotalList.add(reports);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerTotalList;
    }
}
