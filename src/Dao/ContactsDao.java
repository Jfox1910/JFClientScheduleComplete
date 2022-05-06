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
     * Used in populating various contact combo boxes
     * @return
     */
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


    private static Contacts createContact(ResultSet r) throws SQLException {
        return new Contacts(
                r.getInt("Contact_ID"),
                r.getString("Contact_Name")
        );
    }
}
