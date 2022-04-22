package Model;


import java.time.LocalDateTime;

/**
 * Appointment model object class.
 */
public class Appointment {

    public static Customers customers;
    public static Appointment appointment;

    private int Appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String userName;
    private LocalDateTime start;
    private LocalDateTime end;
    private int Customer_ID;
    private int Contact_ID;
    private String ContactName;
    private int User_ID;


    public Appointment(int customerID) {this.Customer_ID = customerID;}

    public Appointment(String userName, String contactName){
        this.userName = userName;
        this.ContactName = contactName;}


    public Appointment(int Appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int Customer_ID, int User_ID, int Contact_ID) {
        this.Appointment_ID = Appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }


    /**
     * Appointment Getters
     * @return Appointment_ID
     * customers
     * appointment
     * title
     * description
     * location
     * type
     * userName
     * start
     * end
     * Customer_ID
     * Contact_ID
     * User_ID
     * ContactName
     */
    public int getAppointment_ID() {return Appointment_ID;}

    public static Customers getCustomers() {return customers;}

    public static Appointment getAppointment() {return appointment;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getLocation() {return location;}

    public String getType() {return type;}

    public String getUserName() {return userName;}

    public LocalDateTime getStart() {return start;}

    public LocalDateTime getEnd() {return end;}

    public int getCustomer_ID() {return Customer_ID;}

    public int getContact_ID() {return Contact_ID;}

    public int getUser_ID() {return User_ID;}

    public String getContactName() {return ContactName;}

    //Setters
    public void setAppointment_ID(int appointment_ID) {Appointment_ID = appointment_ID;}

    public static void setCustomers(Customers customers) {Appointment.customers = customers;}

    public static void setAppointment(Appointment appointment) {Appointment.appointment = appointment;}

    public void setTitle(String title) {this.title = title;}

    public void setDescription(String description) {this.description = description;}

    public void setLocation(String location) {this.location = location;}

    public void setType(String type) {this.type = type;}

    public void setUserName(String userName) {this.userName = userName;}

    public void setStart(LocalDateTime start) {this.start = start;}

    public void setEnd(LocalDateTime end) {this.end = end;}

    public void setCustomer_ID(int customer_ID) {this.Customer_ID = customer_ID;}

    public void setContact_ID(int contact_ID) {this.Contact_ID = contact_ID;}

    public void setUser_ID(int user_ID) {this.User_ID = user_ID;}

    public void setContactName(String contactName) {ContactName = contactName;}
}
