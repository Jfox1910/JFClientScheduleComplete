package Model;

import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Appointments implements Initializable {

    public static Customers customers;
    public static Appointments appointments;

    private int apptIDCol;
    private String apptTitleCol;
    private String apptDescriptionCol;
    private String apptLocationCol;
    private int apptContactCol;
    private String apptTypeCol;
    private LocalDateTime apptStartTimeCol;
    private LocalDateTime apptEndTimeCol;
    private int apptCustomerIDCol;
    private int apptUserIDCol;

    public Appointments(int apptIDCol, String apptTitleCol, String apptDescriptionCol, String apptLocationCol, int apptContactCol, String apptTypeCol, LocalDateTime apptStartTimeCol, LocalDateTime apptEndTimeCol, int apptCustomerIDCol, int apptUserIDCol) {
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

    //Getters
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

    public LocalDateTime getApptStartTimeCol() {
        return apptStartTimeCol;
    }

    public LocalDateTime getApptEndTimeCol() {
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

    public void setApptStartTimeCol(LocalDateTime apptStartTimeCol) {
        this.apptStartTimeCol = apptStartTimeCol;
    }

    public void setApptEndTimeCol(LocalDateTime apptEndTimeCol) {
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
