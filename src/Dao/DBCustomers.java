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
            PreparedStatement psgetCustomers = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = psgetCustomers.executeQuery();

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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

    public void newCustomer(String customerName, String customerAddress, String customerZipCode, String customerPhone, String customerCreatedBy, String customerUpdatedBy, Integer customerDivision){
        try {
            String sqlnewCustomer = "INSERT INTO customers VALUES(NULL,?,?,?,?,NOW(),?,NOW(),?,?)";

            PreparedStatement psnewCustomer = JDBC.getConnection().prepareStatement(sqlnewCustomer);


            assert psnewCustomer != null;
            psnewCustomer.setString(1,customerName);
            psnewCustomer.setString(2,customerAddress);
            psnewCustomer.setString(3,customerZipCode);
            psnewCustomer.setString(4,customerPhone);
            psnewCustomer.setString(5,customerCreatedBy);
            psnewCustomer.setString(6, customerUpdatedBy);
            psnewCustomer.setInt(7, customerDivision);

            psnewCustomer.execute();

        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void modifyCustomer(){
        try {
            String sqlModifyCustomer = "";
            PreparedStatement psmodifyCustomer = JDBC.getConnection().prepareStatement(sqlModifyCustomer);

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }

    public void deleteCustomer(){
        try {
            String sqldeleteCustomer = "DELETE from customers WHERE Customer_ID = ?";
            PreparedStatement psdeleteCustomer = JDBC.getConnection().prepareStatement(sqldeleteCustomer);

            psdeleteCustomer.execute();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
