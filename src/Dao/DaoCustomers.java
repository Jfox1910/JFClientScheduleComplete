package Dao;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
                String customerAddy = rs.getString("Address");
                String customerZip = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String customerCreatedDate = rs.getString("Create_Date");
                String customerCreated = rs.getString("Created_By");
                Timestamp customerUpdatedOn = rs.getTimestamp("Last_Update");
                String customerUpdatedBy = rs.getString("Last_Updated_By");
                int customerDivision = rs.getInt("Division_ID");

                Customers customers = new Customers(customerId,customerName,customerAddy,customerZip,customerPhone,customerCreatedDate,customerCreated,customerUpdatedOn,customerUpdatedBy,customerDivision);
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
            String sqlnewCustomer = "INSERT INTO customers VALUES(NULL,?,?,?,?,NOW(),?,NOW(),?,?)";
            PreparedStatement psnewCustomer = JDBC.getConnection().prepareStatement(sqlnewCustomer);


            assert psnewCustomer != null;
            psnewCustomer.setString(1,customerName);
            psnewCustomer.setString(2,customerAddy);
            psnewCustomer.setString(3,customerZipCode);
            psnewCustomer.setString(4,customerPhone);
            psnewCustomer.setString(5,customerCreatedBy);
            psnewCustomer.setInt(7,customerDivision);

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