package Model;

import java.sql.Timestamp;

    //Customer Class
public class Customers {

    private int customerId;
    private String customerName;
    private String customerAddy;
    private String customerZip;
    private String customerPhone;
    private String customerCreatedDate;
    private String customerCreated;
    private Timestamp customerUpdatedOn;
    private String customerUpdatedBy;
    private int customerDivision;

    public Customers(String customerName){this.customerName = customerName;}
    //Constructor

        public Customers(int customerId, String customerName, String customerAddy, String customerZip, String customerPhone, String customerCreatedDate, String customerCreated, Timestamp customerUpdatedOn, String customerUpdatedBy, int customerDivision) {
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
        }




    //Getters

        public int getCustomerId() {
            return customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getCustomerAddy() {
            return customerAddy;
        }

        public String getCustomerZip() {
            return customerZip;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public String getCustomerCreatedDate() {
            return customerCreatedDate;
        }

        public String getCustomerCreated() {
            return customerCreated;
        }

        public Timestamp getCustomerUpdatedOn() {
            return customerUpdatedOn;
        }

        public String getCustomerUpdatedBy() {
            return customerUpdatedBy;
        }

        public int getCustomerDivision() {
            return customerDivision;
        }


        //Setters
        public void setCustomerId(int customerId) {this.customerId = customerId;}

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public void setCustomerAddy(String customerAddy) {
            this.customerAddy = customerAddy;
        }

        public void setCustomerZip(String customerZip) {
            this.customerZip = customerZip;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public void setCustomerCreatedDate(String customerCreatedDate) {this.customerCreatedDate = customerCreatedDate;}

        public void setCustomerCreated(String customerCreated) {
            this.customerCreated = customerCreated;
        }

        public void setCustomerUpdatedOn(Timestamp customerUpdatedOn) {
            this.customerUpdatedOn = customerUpdatedOn;
        }

        public void setCustomerUpdatedBy(String customerUpdatedBy) {
            this.customerUpdatedBy = customerUpdatedBy;
        }

        public void setCustomerDivision(int customerDivision) {
            this.customerDivision = customerDivision;
        }

    }
