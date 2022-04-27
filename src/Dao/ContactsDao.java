package Dao;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ContactDAO Class
 */
public class ContactsDao {


    /**
     * Gets all contacts in the DB
     * @return allContacts
     */
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contacts contacts = new Contacts(contactID, contactName);
                allContacts.add(contacts);
            }
            ps.close();
        } catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return allContacts;
    }

    /**
     * Gets the contacts name based off the ID given.
     * @param contactID
     * @return selectedCustomerName
     */
    public static String setContactName(int contactID) {
        String contactName = null;

        try {
            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                contactName = rs.getString("Contact_Name");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String selectedCustomerName = String.valueOf(contactID) + ": " + contactName;
        return selectedCustomerName;
    }

}