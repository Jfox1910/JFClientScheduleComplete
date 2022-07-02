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

    /**
     * Used to pull appointments by type in the reports screen
     * @return appointmentList
     */
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
}
