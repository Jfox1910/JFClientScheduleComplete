package Model;

import java.sql.Timestamp;

    //Customer Class
public class Customers {

    private int customerIdCol;
    private String customerNameCol;
    private String customerAddyCol;
    private String customerZipCol;
    private String customerPhoneCol;
    private String customerCreatedDateCol;
    private String customerCreatedCol;
    private Timestamp customerUpdatedOnCol;
    private String customerUpdatedByCol;
    private int customerDivisionCol;

    private static Customers selectedCustomer;
    //Constructor
    public Customers(int customerIdCol, String customerNameCol, String customerAddyCol, String customerZipCol, String customerPhoneCol, String customerCreatedDateCol,
                     String customerCreatedCol, Timestamp customerUpdatedOnCol, String customerUpdatedByCol, int customerDivisionCol) {
        this.customerIdCol = customerIdCol;
        this.customerNameCol = customerNameCol;
        this.customerAddyCol = customerAddyCol;
        this.customerZipCol = customerZipCol;
        this.customerPhoneCol = customerPhoneCol;
        this.customerCreatedDateCol = customerCreatedDateCol;
        this.customerCreatedCol = customerCreatedCol;
        this.customerUpdatedOnCol = customerUpdatedOnCol;
        this.customerUpdatedByCol = customerUpdatedByCol;
        this.customerDivisionCol = customerDivisionCol;
    }

    //Getters
        public static Customers getSelectedCustomer(){return selectedCustomer;}

        public int getCustomerIdCol() {
            return customerIdCol;
        }

        public String getCustomerNameCol() {
            return customerNameCol;
        }

        public String getCustomerAddyCol() {
            return customerAddyCol;
        }

        public String getCustomerZipCol() {
            return customerZipCol;
        }

        public String getCustomerPhoneCol() {
            return customerPhoneCol;
        }

        public String getCustomerCreatedDateCol() {
            return customerCreatedDateCol;
        }

        public String getCustomerCreatedCol() {
            return customerCreatedCol;
        }

        public Timestamp getCustomerUpdatedOnCol() {
            return customerUpdatedOnCol;
        }

        public String getCustomerUpdatedByCol() {
            return customerUpdatedByCol;
        }

        public int getCustomerDivisionCol() {
            return customerDivisionCol;
        }

        //Setters
        public void setCustomerIdCol(int customerIdCol) {
            this.customerIdCol = customerIdCol;
        }

        public void setCustomerNameCol(String customerNameCol) {
            this.customerNameCol = customerNameCol;
        }

        public void setCustomerAddyCol(String customerAddyCol) {
            this.customerAddyCol = customerAddyCol;
        }

        public void setCustomerZipCol(String customerZipCol) {
            this.customerZipCol = customerZipCol;
        }

        public void setCustomerPhoneCol(String customerPhoneCol) {
            this.customerPhoneCol = customerPhoneCol;
        }

        public void setCustomerCreatedDateCol(String customerCreatedDateCol) {this.customerCreatedDateCol = customerCreatedDateCol;}

        public void setCustomerCreatedCol(String customerCreatedCol) {
            this.customerCreatedCol = customerCreatedCol;
        }

        public void setCustomerUpdatedOnCol(Timestamp customerUpdatedOnCol) {this.customerUpdatedOnCol = customerUpdatedOnCol;}

        public void setCustomerUpdatedByCol(String customerUpdatedByCol) {this.customerUpdatedByCol = customerUpdatedByCol;}

        public void setCustomerDivisionCol(int customerDivisionCol) {
            this.customerDivisionCol = customerDivisionCol;
        }
    }
