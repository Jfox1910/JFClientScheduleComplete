package Dao;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBCustomers {
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> customerList = FXCollections.observableArrayList();

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
                String customerZipCol = rs.getString("Postal_Code");
                String customerPhoneCol = rs.getString("Phone");
                String customerCreatedDateCol = rs.getString("Create_Date");
                String customerCreatedCol = rs.getString("Created_By");
                Timestamp customerUpdatedOnCol = rs.getTimestamp("Last_Update");
                String customerUpdatedByCol = rs.getString("Last_Updated_By");
                int customerDivisionCol = rs.getInt("Division_ID");

                Customers customers = new Customers(customerIdCol,customerNameCol,customerAddyCol,customerZipCol,customerPhoneCol,customerCreatedDateCol,customerCreatedCol,customerUpdatedOnCol,customerUpdatedByCol,customerDivisionCol);
                customerList.add(customers);
               /* int customerIdCol = rs.getInt("Customer_ID");
                String customerNameCol = rs.getString("Customer_Name");
                String customerAddyCol = rs.getString("Address");
                //String customerStateCol = rs.getString("Division_ID");
                String customerZipCol = rs.getString("Postal_Code");
                String customerPhoneCol = rs.getString("Phone");
                int customerCountryCol = rs.getInt("Country_ID");*/

                //Customers customer = new Customers();
                //customers.add(customer);

                /*Customers customer = new Customers(customerIdCol, customerNameCol,customerAddyCol,customerStateCol,customerZipCol, customerCountryCol, customerPhoneCol);
                customers.add(customer);*/
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }
}
