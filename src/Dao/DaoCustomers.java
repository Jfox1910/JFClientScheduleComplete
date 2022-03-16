package Dao;

import Model.Customers;
import Model.loginUser;
import Controller.ModCustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;

public class DaoCustomers {

    private static final Connection connection = JDBC.getConnection();
   // private static final ObservableList<Customers> customers = FXCollections.observableArrayList();

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
    public static void newCustomer(String customerName, String customerAddy, String customerZipCode, String customerPhone,int customerDivision){

        try {
            //setting the customer ID to 1 then adding 1 to the highest ID.
            int customerID = 1;
            try {
                Statement id = connection.createStatement();
                ResultSet rs = id.executeQuery("select max(Customer_ID) as Last_Customer from customers");
                if (rs.next()) {
                    customerID = rs.getInt("Last_Customer") + 1;
                }
                id.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

            String sqlnewCustomer = "INSERT INTO customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = now(), Created_By = ?,last_update = now(),last_updated_by = ?, Division_ID = ?;";
            PreparedStatement psnewCustomer = JDBC.getConnection().prepareStatement(sqlnewCustomer);

            psnewCustomer.setInt(1, customerID);
            psnewCustomer.setString(2,customerName);
            psnewCustomer.setString(3,customerAddy);
            psnewCustomer.setString(4,customerZipCode);
            psnewCustomer.setString(5,customerPhone);
            psnewCustomer.setString(6, null);
            psnewCustomer.setString(7, null);
            //psnewCustomer.setString(8, null);
            psnewCustomer.setInt(8, customerDivision);
            //psnewCustomer.setString(7,JDBC.getLoginUser());
           // psnewCustomer.setString(8, null);


           /* psnewCustomer.setString(5, Utils.getUTCDateTimeString());
            psnewCustomer.setString(6, Utils.getUser().getUserName());
            psnewCustomer.setString(7, Utils.getUTCDateTimeString());
            psnewCustomer.setString(8, Utils.getUser().getUserName());
            psnewCustomer.setInt(9,customerDivision);*/

            //customers.setCustomerCreatedCol(JDBC.getLoginUser());
            psnewCustomer.executeUpdate();
            psnewCustomer.close();
            getAllCustomers();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    //Modifies a selected customer and updates the database
    public static void modifyCustomer(String modifyCustomerName, String modifyCustomerAddress, String modifyCustomerZip, String modifyCustomerPhone, String customerUpdatedBy, Integer modifyCustomerDivision){
        try {
            String sqlModifyCustomer = "UPDATE customers WHERE Customer_ID = ? SET customerName=?, customerAddress=?, customerZipCode=?, customerPhone=?, customerUpdatedBy=?, customerDivision=?";
            PreparedStatement psmodifyCustomer = JDBC.getConnection().prepareStatement(sqlModifyCustomer);

            psmodifyCustomer.setString(1,modifyCustomerName);
            psmodifyCustomer.setString(2,modifyCustomerAddress);
            psmodifyCustomer.setString(3,modifyCustomerZip);
            psmodifyCustomer.setString(4,modifyCustomerPhone);
            psmodifyCustomer.setString(5,customerUpdatedBy = "TEST");
            psmodifyCustomer.setInt(6,modifyCustomerDivision);

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
