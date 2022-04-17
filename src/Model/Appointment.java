package Model;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Appointment implements Initializable {

    public static Customers customers;
    public static Appointment appointment;

    private int apptIDCol;
    private String apptTitleCol;
    private String apptDescriptionCol;
    private String apptLocationCol;
    private int apptContactCol;
    private String apptTypeCol;
    private Timestamp apptStartTimeCol;
    private Timestamp apptEndTimeCol;
    private int apptCustomerIDCol;
    private int apptUserIDCol;
    private Timestamp apptStart;
    private Timestamp apptEnd;


    public Appointment(int apptIDCol, String apptTitleCol, String apptDescriptionCol, String apptLocationCol, int apptContactCol, String apptTypeCol, Timestamp apptStartTimeCol, Timestamp apptEndTimeCol, int apptCustomerIDCol, int apptUserIDCol) {
        this.apptIDCol = apptIDCol;
        this.apptTitleCol = apptTitleCol;
        this.apptDescriptionCol = apptDescriptionCol;
        this.apptLocationCol = apptLocationCol;
        this.apptContactCol = apptContactCol;
        this.apptTypeCol = apptTypeCol;
        this.apptStartTimeCol = apptStartTimeCol;
        this.apptEndTimeCol = apptEndTimeCol;
        this.apptCustomerIDCol = apptCustomerIDCol;
        this.apptUserIDCol = apptUserIDCol;
    }

    public Appointment(int customerID) {
        this.apptCustomerIDCol = customerID;
    }

    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, int customerID, int contactID) {
        this.apptIDCol = appointmentID;
        this.apptTitleCol = title;
        this.apptDescriptionCol = description;
        this.apptLocationCol = location;
        this.apptTypeCol = type;
        this.apptStart = startTime;
        this.apptEnd = endTime;
        this.apptCustomerIDCol = customerID;
        this.apptContactCol = contactID;

    }


    //Getters



    public static Appointment getAppointment() {return appointment;}

    public static void setAppointment(Appointment appointment) {Appointment.appointment = appointment;}

    public int getContactID() { return  apptContactCol;}

    public int getCustomerID() {
        return apptCustomerIDCol;
    }

    public void setCustomerId(int customerID) {
        this.apptCustomerIDCol = customerID;
    }
    public void setContactID(int contactID) { this.apptContactCol = contactID; }


    public int getApptIDCol() {
        return apptIDCol;
    }

    public String getApptTitleCol() {
        return apptTitleCol;
    }

    public String getApptDescriptionCol() {
        return apptDescriptionCol;
    }

    public String getApptLocationCol() {
        return apptLocationCol;
    }

    public int getApptContactCol() {
        return apptContactCol;
    }

    public String getApptTypeCol() {
        return apptTypeCol;
    }

    public Timestamp getApptStartTimeCol() {
        return apptStartTimeCol;
    }

    public Timestamp getApptEndTimeCol() {
        return apptEndTimeCol;
    }

    public int getApptCustomerIDCol() {
        return apptCustomerIDCol;
    }

    public int getApptUserIDCol() {
        return apptUserIDCol;
    }

    //Setters
    public void setApptIDCol(int apptIDCol) {
        this.apptIDCol = apptIDCol;
    }

    public void setApptTitleCol(String apptTitleCol) {
        this.apptTitleCol = apptTitleCol;
    }

    public void setApptDescriptionCol(String apptDescriptionCol) {
        this.apptDescriptionCol = apptDescriptionCol;
    }

    public void setApptLocationCol(String apptLocationCol) {
        this.apptLocationCol = apptLocationCol;
    }

    public void setApptContactCol(int apptContactCol) {
        this.apptContactCol = apptContactCol;
    }

    public void setApptTypeCol(String apptTypeCol) {
        this.apptTypeCol = apptTypeCol;
    }

    public void setApptStartTimeCol(Timestamp apptStartTimeCol) {
        this.apptStartTimeCol = apptStartTimeCol;
    }

    public void setApptEndTimeCol(Timestamp apptEndTimeCol) {
        this.apptEndTimeCol = apptEndTimeCol;
    }

    public void setApptCustomerIDCol(int apptCustomerIDCol) {
        this.apptCustomerIDCol = apptCustomerIDCol;
    }

    public void setApptUserIDCol(int apptUserIDCol) {
        this.apptUserIDCol = apptUserIDCol;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
