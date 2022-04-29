package Dao;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;

/**
 * Customer DB access class
 */
public class CustomersDao {

    private static final Connection connection = JDBC.getConnection();


    /**
     * Gets all existing customers in the DB and stores them in a list.
     * @return customerList
     */
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
                int customerDivision = rs.getInt("Division_ID");

                String divisionName = DivisionsDao.getDivisionName(customerDivision);
                Customers customers = new Customers(customerId,customerName,customerAddress,customerPostalCode,customerPhone,customerCreateDate,customerCreatedBy,customerLastUpdate,customerLastUpdatedBy,customerDivision,divisionName);
                customerList.add(customers);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }


    /**
     * Select customer by division.
     * @param customers
     * @return divisionID
     */
    public static Integer getCustomerDivision(Customers customers){
        int customerID = customers.getCustomerId();
        int divisionID = 0;

        try {
            String sql = "SELECT Division_ID from customers WHERE Customer_ID = ?";
            PreparedStatement psgetCustomerDiv = JDBC.getConnection().prepareStatement(sql);
            psgetCustomerDiv.setInt(1, customerID);
            ResultSet rs = psgetCustomerDiv.executeQuery();
            if (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionID;
    }


    /**
     * Adds a new customer to the database.
     * @param userID
     * @param customerName
     * @param customerAddy
     * @param customerZipCode
     * @param customerPhone
     * @param customerDivision
     */
    public static void newCustomer(int userID, String customerName, String customerAddy, String customerZipCode, String customerPhone, int customerDivision){

//Selects the highest existing customer ID then adds 1 to it to increment sequentially. Probably not needed.
        try {
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
            psnewCustomer.setString(6, UserDao.getLoggedinUser().getUserName());
            psnewCustomer.setString(7, UserDao.getLoggedinUser().getUserName());
            psnewCustomer.setInt(8, customerDivision);

            psnewCustomer.execute();
            psnewCustomer.close();
            getAllCustomers();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


    /**
     * DB access used in the modify customer method.
     * @param customerId
     * @param customerName
     * @param customerAddy
     * @param customerZipCode
     * @param customerPhone
     * @param customerDivision
     */
    public static void modifyCustomer(int customerId, String customerName, String customerAddy, String customerZipCode, String customerPhone, int customerDivision){

        try {
            String sqlModifyCustomer = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Last_Update=NOW(),Phone=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID=? ";
            PreparedStatement psmodifyCustomer = JDBC.getConnection().prepareStatement(sqlModifyCustomer);

            psmodifyCustomer.setString(1,customerName);
            psmodifyCustomer.setString(2,customerAddy);
            psmodifyCustomer.setString(3,customerZipCode);
            psmodifyCustomer.setString(4,customerPhone);
            psmodifyCustomer.setString(5, UserDao.getLoggedinUser().getUserName());
            psmodifyCustomer.setString(5, UserDao.getLoggedinUser().getUserName());
            psmodifyCustomer.setInt(6,customerDivision);
            psmodifyCustomer.setInt(7,customerId);

            psmodifyCustomer.execute();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


    /**
     * Boolean used to check for existing appointments for a selected customer.
     * @param customers
     * @return
     */
    public static boolean customerDeleteValidation(Customers customers) {
        int customerID = customers.getCustomerId();
        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return false;
            } else {
                return true;
            }
        }catch(SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    /**
     * Deletes the selected CUSTOMER from the DB if the above boolean returns no associated appointments to the delete customer method in the mainscreen.
     * @param customerID
     */
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


    public static void getCustomerID (int customerID){

        String sql = "SELECT * from customers WHERE Customer_ID = ?";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String customerCreateDate = rs.getString("Create_Date");
                String customerCreatedBy = rs.getString("Created_By");
                Timestamp customerLastUpdate = rs.getTimestamp("Last_Update");
                String customerLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerDivision = rs.getInt("Division_ID");
                String divisionName = DivisionsDao.getDivisionName(customerDivision);

                Customers customers = new Customers(customerId,customerName,customerAddress,customerPostalCode,customerPhone,customerCreateDate,customerCreatedBy,customerLastUpdate,customerLastUpdatedBy,customerDivision,divisionName);

            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


    /**
     * Gets the customers name based off the ID given.
     * @param customerID
     * @return selectedCustomerName
     */
    public static String setCustomerName(int customerID) {
        String customerName = null;

        try {
            String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                customerName = rs.getString("Customer_Name");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String selectedCustomerName = String.valueOf(customerID) + ": " + customerName;
        return selectedCustomerName;
    }


    /**
     * Gets the customers country by division.
     * @param customers
     * @return
     */
    public static String getCountry(Customers customers){
        return CountriesDao.getCountryID(DivisionsDao.getCountryDivision(CustomersDao.getCustomerDivision(customers)));
    }
}
