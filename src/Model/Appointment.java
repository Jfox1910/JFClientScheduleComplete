package Model;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Appointment implements Initializable {

    public static Customers customers;
    public static Appointment appointment;

    private int appt_ID;
    private String apptTitleCol;
    private String apptDescriptionCol;
    private String apptLocationCol;
    private int apptContactCol;
    private String apptTypeCol;
    private Timestamp apptStartTime;
    private Timestamp apptEndTime;
    private int apptCustomerIDCol;
    private int apptUserIDCol;
    private String apptStart;
    private int apptEnd;

    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customer_id;
    private int contact_id;
    private int user_id;


    public Appointment(int appt_ID, String apptTitleCol, String apptDescriptionCol, String apptLocationCol, int apptContactCol, String apptTypeCol, Timestamp apptStartTime, Timestamp apptEndTime, int apptCustomerIDCol, int apptUserIDCol) {
        this.appt_ID = appt_ID;
        this.apptTitleCol = apptTitleCol;
        this.apptDescriptionCol = apptDescriptionCol;
        this.apptLocationCol = apptLocationCol;
        this.apptContactCol = apptContactCol;
        this.apptTypeCol = apptTypeCol;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.apptCustomerIDCol = apptCustomerIDCol;
        this.apptUserIDCol = apptUserIDCol;
    }

    public Appointment(int customerID) {
        this.apptCustomerIDCol = customerID;
    }

    public Appointment(int appointmentID, String title, String description, String location, String type, String startTime, int endTime, int customerID, int contactID) {
        this.appt_ID = appointmentID;
        this.apptTitleCol = title;
        this.apptDescriptionCol = description;
        this.apptLocationCol = location;
        this.apptTypeCol = type;
        this.apptStart = startTime;
        this.apptEnd = endTime;
        this.apptCustomerIDCol = customerID;
        this.apptContactCol = contactID;

    }

    public Appointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }


    //Getters

    public static Appointment getAppointment() {return appointment;}

    public int getContactID() { return  apptContactCol;}

    public int getCustomerID() {
        return apptCustomerIDCol;
    }

    public int getAppt_ID() {
        return appt_ID;
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

    public Timestamp getApptStartTime() {
        return apptStartTime;
    }

    public Timestamp getApptEndTime() {
        return apptEndTime;
    }

    public int getApptCustomerIDCol() {
        return apptCustomerIDCol;
    }

    public int getApptUserIDCol() {
        return apptUserIDCol;
    }

    //Setters
    public static void setAppointment(Appointment appointment) {Appointment.appointment = appointment;}

    public void setCustomerId(int customerID) {
        this.apptCustomerIDCol = customerID;
    }

    public void setContactID(int contactID) { this.apptContactCol = contactID; }

    public void setAppt_ID(int appt_ID) {
        this.appt_ID = appt_ID;
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

    public void setApptStartTime(Timestamp apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public void setApptEndTime(Timestamp apptEndTime) {
        this.apptEndTime = apptEndTime;
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
