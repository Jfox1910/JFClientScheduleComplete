package Dao;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * ContactDAO Class
 */
public class ContactsDao {


    /**
     * Gets all contacts in the DB
     *
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
     *
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

            if (rs.next()) {
                contactName = rs.getString("Contact_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String selectedCustomerName = contactID + ": " + contactName;
        return selectedCustomerName;
    }

    /**
     * Gets divisions by name
     *
     * @param contactName
     * @return divisionID
     */
    public static int getAllContactsByName(String contactName) {
        int contactID = 0;

        try {
            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contactID = rs.getInt("Contact_ID");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ;
        }
        return contactID;
    }

    public static ObservableList<Contacts> getContactList() {

        ObservableList<Contacts> listOfContacts = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Contact_Name, Contact_ID from contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("contact_Name");
                listOfContacts.add(new Contacts(contactID, contactName));
            }
        }
        catch(Exception e) {
        }
        return listOfContacts;
    }


    public static Optional<Contacts> getContact(int Contact_ID) {

        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?;";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            ps.setInt(1, Contact_ID);

            if (rs.next()) {
                return Optional.of(createContact(rs));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    private static Contacts createContact(ResultSet r) throws SQLException {
        return new Contacts(
                r.getInt("Contact_ID"),
                r.getString("Contact_Name")
        );
    }
}
