package Dao;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class DaoCustomers {


    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";
            PreparedStatement psgetCustomers = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = psgetCustomers.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String customerCreateDate = rs.getString("Create_Date");
                String customerCreatedBy = rs.getString("Created_By");
                Timestamp customerLastUpdate = rs.getTimestamp("Last_Update");
                String customerLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerDivisionId = rs.getInt("Division_ID");

                Customers customers = new Customers(customerId,customerName,customerAddress,customerPostalCode,customerPhone,customerCreateDate,customerCreatedBy,customerLastUpdate,customerLastUpdatedBy,customerDivisionId);
                customerList.add(customers);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

    //Adds a new customer to the database.
    public static void newCustomer(String customerName, String customerAddy, String customerZipCode, String customerPhone, String customerCreatedBy, Integer customerDivision){

        try {
            String sqlnewCustomer = "INSERT INTO customers VALUES(NULL,?,?,?,?,NOW(),?,?)";
            PreparedStatement psnewCustomer = JDBC.getConnection().prepareStatement(sqlnewCustomer);


            assert psnewCustomer != null;
            psnewCustomer.setString(1,customerName);
            psnewCustomer.setString(2,customerAddy);
            psnewCustomer.setString(3,customerZipCode);
            psnewCustomer.setString(4,customerPhone);
           // psnewCustomer.setTime(6,createDate);
            psnewCustomer.setString(5,customerCreatedBy);
            psnewCustomer.setInt(6,customerDivision);

            psnewCustomer.execute();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    //Modifies a selected customer and updates the database
    public void modifyCustomer(String customerName, String customerAddy, String customerZipCode, String customerPhone, String customerUpdatedBy, Integer customerDivision){
        try {
            String sqlModifyCustomer = "UPDATE customers SET customerName = ?, customerAddress = ?, customerZipCode = ?, customerPhone = ?, customerUpdatedBy = ?, customerDivision = ?";
            PreparedStatement psmodifyCustomer = JDBC.getConnection().prepareStatement(sqlModifyCustomer);

            psmodifyCustomer.setString(1,customerName);
            psmodifyCustomer.setString(2,customerAddy);
            psmodifyCustomer.setString(3,customerZipCode);
            psmodifyCustomer.setString(4,customerPhone);
            psmodifyCustomer.setString(5,customerUpdatedBy);
            psmodifyCustomer.setInt(6,customerDivision);

            psmodifyCustomer.execute();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }

    //Deletes an exisiting customer from the database.
    public static void deleteCustomer(Integer customerID){
        try {
            String sqldeleteCustomer = "DELETE from customers WHERE Customer_ID = ?";
            PreparedStatement psdeleteCustomer = JDBC.getConnection().prepareStatement(sqldeleteCustomer);

            psdeleteCustomer.setInt(1, customerID);

            psdeleteCustomer.execute();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
