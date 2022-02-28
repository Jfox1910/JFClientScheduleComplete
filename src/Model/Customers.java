package Model;

//Customer Class
public class Customers {

    private int customerIdCol;
    private String customerNameCol;
    private String customerAddyCol;
    private String customerStateCol;
    private int customerZipCol;
    private int customerCountryCol;
    private int customerPhoneCol;

    //Constructor
    public Customers(int customerIdCol, String customerNameCol, String customerAddyCol, String customerStateCol, int customerZipCol, int customerCountryCol, int customerPhoneCol) {
        this.customerIdCol = customerIdCol;
        this.customerNameCol = customerNameCol;
        this.customerAddyCol = customerAddyCol;
        this.customerStateCol = customerStateCol;
        this.customerZipCol = customerZipCol;
        this.customerCountryCol = customerCountryCol;
        this.customerPhoneCol = customerPhoneCol;
    }

    //Getters
    public int getCustomerIdCol() {
        return customerIdCol;
    }

    public String getCustomerNameCol() {
        return customerNameCol;
    }

    public String getCustomerAddyCol() {
        return customerAddyCol;
    }

    public String getCustomerStateCol() {
        return customerStateCol;
    }

    public int getCustomerZipCol() {
        return customerZipCol;
    }

    public Integer getCustomerCountryCol() {
        return customerCountryCol;
    }

    public int getCustomerPhoneCol() {
        return customerPhoneCol;
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

    public void setCustomerStateCol(String customerStateCol) {
        this.customerStateCol = customerStateCol;
    }

    public void setCustomerZipCol(int customerZipCol) {
        this.customerZipCol = customerZipCol;
    }

    public void setCustomerCountryCol(Integer customerCountryCol) {
        this.customerCountryCol = customerCountryCol;
    }

    public void setCustomerPhoneCol(int customerPhoneCol) {
        this.customerPhoneCol = customerPhoneCol;
    }
}
