package Dao;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> customers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            /**
             * REPAIR Tableview columns to match DB tables
             */

            while(rs.next()){
                int customerIdCol = rs.getInt("Customer_ID");
                String customerNameCol = rs.getString("Customer_Name");
                String customerAddyCol = rs.getString("Address");
                String customerStateCol = rs.getString("Division_ID");
                int customerZipCol = rs.getInt("Postal_Code");
                int customerCountryCol = rs.getInt("Country_ID");
                int customerPhoneCol = rs.getInt("Phone");

                Customers customer = new Customers(customerIdCol, customerNameCol,customerAddyCol,customerStateCol,customerZipCol, customerCountryCol, customerPhoneCol);
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customers;
    }
}
