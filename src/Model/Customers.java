package Model;

import Dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;


/**
 * Customer model object class.
 */
    public class Customers {
        private int customerId = 0;
        private String customerName;
        private String customerAddy;
        private String customerZip;
        private String customerPhone;
        private String customerCreatedDate;
        private String customerCreated;
        private Timestamp customerUpdatedOn;
        private String customerUpdatedBy;
        private int customerCountry;
        private String customerCountryName;
        private int customerDivision;
        private String divisionName;
        private ObservableList<Appointment> appt = FXCollections.observableArrayList();


        public Customers(int customerId, String customerName, String customerAddy, String customerZip, String customerPhone, String customerCreatedDate, String customerCreated, Timestamp customerUpdatedOn, String customerUpdatedBy, int customerDivision, String divisionName) {
            this.customerId = customerId;
            this.customerName = customerName;
            this.customerAddy = customerAddy;
            this.customerZip = customerZip;
            this.customerPhone = customerPhone;
            this.customerCreatedDate = customerCreatedDate;
            this.customerCreated = customerCreated;
            this.customerUpdatedOn = customerUpdatedOn;
            this.customerUpdatedBy = customerUpdatedBy;
            this.customerDivision = customerDivision;
            this.divisionName = divisionName;
        }


        public Customers(int customerId, String customerName, String customerAddy, String customerZip, String customerPhone, int customerCountry, String customerCountryName, int customerDivision, String divisionName){
            this.customerId = customerId;
            this.customerName = customerName;
            this.customerAddy = customerAddy;
            this.customerZip = customerZip;
            this.customerPhone = customerPhone;
            this.customerCountry = customerCountry;
            this.customerCountryName = customerCountryName;
            this.customerDivision = customerDivision;
            this.divisionName = divisionName;
        }

    public Customers(int customerId, String customerName) {
            this.customerId = customerId;
            this.customerName = customerName;
    }


    /**
     *Customer Getters
     * @return customerId, customerName, customerAddy, customerZip, customerPhone, customerCreatedDate,
     * customerCreated, customerUpdatedOn, customerUpdatedBy, customerCountry, divisionName
     */

    public int getCustomerId() {return customerId;}

    public String getCustomerName() {return customerName;}

    public String getCustomerAddy() {return customerAddy;}

    public String getCustomerZip() {return customerZip;}

    public String getCustomerPhone() {return customerPhone;}

    public String getCustomerCreatedDate() {return customerCreatedDate;}

    public String getCustomerCreated() {return customerCreated;}

    public Timestamp getCustomerUpdatedOn() {return customerUpdatedOn;}

    public String getCustomerUpdatedBy() {return customerUpdatedBy;}

    public int getCustomerCountry() {return customerCountry;}

    public String getCustomerCountryName() {return customerCountryName;}

    public ObservableList<Appointment> getAppt() {return appt;}

    public int getCustomerDivision() {return customerDivision;}

    public String getDivisionName() {return divisionName;}

    public ObservableList<Appointment> getCustomerAppointments() {return new AppointmentDAO().getAppointmentsByCustomer(customerId);}


    /**
     * override for handling strings in the comboboxes.
     * @return customerId
     */
    @Override
    public String toString() {return (customerId + " : " + customerName);}


    /**
     *Customer Setters
     *
     * @param customerId
     * customerName
     * customerAddy
     * customerZip
     * customerPhone
     * customerCreatedDate
     * customerCreated
     * customerUpdatedOn
     * customerUpdatedBy
     * customerCountry
     * customerDivision
     * customerDivisionName
     */
        public void setCustomerId(int customerId) {this.customerId = customerId;}

        public void setCustomerName(String customerName) {this.customerName = customerName;}

        public void setCustomerAddy(String customerAddy) {this.customerAddy = customerAddy;}

        public void setCustomerZip(String customerZip) {this.customerZip = customerZip;}

        public void setCustomerPhone(String customerPhone) {this.customerPhone = customerPhone;}

        public void setCustomerCreatedDate(String customerCreatedDate) {this.customerCreatedDate = customerCreatedDate;}

        public void setCustomerCreated(String customerCreated) {this.customerCreated = customerCreated;}

        public void setCustomerUpdatedOn(Timestamp customerUpdatedOn) {this.customerUpdatedOn = customerUpdatedOn;}

        public void setCustomerUpdatedBy(String customerUpdatedBy) {this.customerUpdatedBy = customerUpdatedBy;}

        public void setCustomerCountry(int customerCountry) {this.customerCountry = customerCountry;}

        public void setCustomerCountryName(String customerCountryName) {this.customerCountryName = customerCountryName;}

        public void setCustomerDivision(int customerDivision) {this.customerDivision = customerDivision;}

        public void setDivisionName(String customerDivisionName) {this.divisionName = customerDivisionName;}

        public void setAppt(ObservableList<Appointment> appt){this.appt = appt;}

}
