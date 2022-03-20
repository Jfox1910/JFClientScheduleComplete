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

    //private static Customers selectedCustomer;
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




   /* public Customers(int customerId, String customerName, String customerAddy, String customerZip, String customerPhone, String customerCreatedDate,
                     String customerCreated, Timestamp customerUpdatedOn, String customerUpdatedBy, int customerDivision) {
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
    }*/



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


        //public static Customers getSelectedCustomer(){return selectedCustomer;}

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

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

        public void setCustomerCreatedDate(String customerCreatedDate) {
            this.customerCreatedDate = customerCreatedDate;
        }

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

/*        public int getCustomerIdCol() {
            return customerId;
        }

        public String getCustomerNameCol() {
            return customerName;
        }

        public String getCustomerAddyCol() {
            return customerAddy;
        }

        public String getCustomerZipCol() {
            return customerZip;
        }

        public String getCustomerPhoneCol() {
            return customerPhone;
        }

        public String getCustomerCreatedDateCol() {
            return customerCreatedDate;
        }

        public String getCustomerCreatedCol() {
            return customerCreated;
        }

        public Timestamp getCustomerUpdatedOnCol() {
            return customerUpdatedOn;
        }

        public String getCustomerUpdatedByCol() {
            return customerUpdatedBy;
        }

        public int getCustomerDivisionCol() {
            return customerDivision;
        }

        //Setters



        public void setCustomerIdCol(int customerId) {
            this.customerId = customerId;
        }

        public void setCustomerNameCol(String customerNameCol) {
            this.customerName = customerNameCol;
        }

        public void setCustomerAddyCol(String customerAddyCol) {
            this.customerAddy = customerAddyCol;
        }

        public void setCustomerZipCol(String customerZipCol) {
            this.customerZip = customerZipCol;
        }

        public void setCustomerPhoneCol(String customerPhoneCol) {
            this.customerPhone = customerPhoneCol;
        }

        public void setCustomerCreatedDateCol(String customerCreatedDateCol) {this.customerCreatedDate = customerCreatedDateCol;}

        public void setCustomerCreatedCol(String customerCreatedCol) {
            this.customerCreated = customerCreatedCol;
        }

        public void setCustomerUpdatedOnCol(Timestamp customerUpdatedOnCol) {this.customerUpdatedOn = customerUpdatedOnCol;}

        public void setCustomerUpdatedByCol(String customerUpdatedByCol) {this.customerUpdatedBy = customerUpdatedByCol;}

        public void setCustomerDivisionCol(int customerDivisionCol) {
            this.customerDivision = customerDivisionCol;
        }*/
    }
