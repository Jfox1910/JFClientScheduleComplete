package Model;

import java.time.LocalDateTime;

/**
 * Reports model object class. Used in reports.
 */
public class Reports {

    private String month;
    private String appointmentTotal;
    private String type;

    private int Appointment_ID;
    private String title;
    private String appointmentType;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int Customer_ID;
    private int Contact_ID;
    private int day;
    private String totalAppointments;

    public Reports(String month, String totalNumberOfAppointments, String type, int Appointment_ID, String title, String appointmentType, String description, LocalDateTime start, LocalDateTime end, int Customer_ID, int Contact_ID, int day, String totalAppointments) {
        this.month = month;
        this.appointmentTotal = totalNumberOfAppointments;
        this.type = type;
        this.Appointment_ID = Appointment_ID;
        this.title = title;
        this.appointmentType = appointmentType;
        this.description = description;
        this.start = start;
        this.end = end;
        this.Customer_ID = Customer_ID;
        this.Contact_ID = Contact_ID;
        this.day = day;
        this.totalAppointments = totalAppointments;
    }

    public Reports(int day, int Customer_ID, String totalAppointments) {
        this.day = day;
        this.Customer_ID = Customer_ID;
        this.totalAppointments = totalAppointments;
    }

    public String getMonth() {return month;}

    public void setMonth(String month) {this.month = month;}

    public String getAppointmentTotal() {return appointmentTotal;}

    public void setAppointmentTotal(String appointmentTotal) {this.appointmentTotal = appointmentTotal;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public int getAppointment_ID() {return Appointment_ID;}

    public void setAppointment_ID(int appointment_ID) {Appointment_ID = appointment_ID;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAppointmentType() {return appointmentType;}

    public void setAppointmentType(String appointmentType) {this.appointmentType = appointmentType;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public LocalDateTime getStart() {return start;}

    public void setStart(LocalDateTime start) {this.start = start;}

    public LocalDateTime getEnd() {return end;}

    public void setEnd(LocalDateTime end) {this.end = end;}

    public int getCustomer_ID() {return Customer_ID;}

    public void setCustomer_ID(int customer_ID) {Customer_ID = customer_ID;}

    public int getContact_ID() {return Contact_ID;}

    public void setContact_ID(int contact_ID) {Contact_ID = contact_ID;}

    public int getDay() {return day;}

    public void setDay(int day) {this.day = day;}

    public String getTotalAppointments() {return totalAppointments;}

    public void setTotalAppointments(String totalCustomerAppointments) {this.totalAppointments = totalCustomerAppointments;}
}
