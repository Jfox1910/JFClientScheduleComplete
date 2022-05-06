package Model;


/**
 * Contact model object class.
 */
public class Contacts {

    private int contactID;
    private String contactName;
    private String reportContactName;
    private int reportContactID;
    private Contacts contacts;

    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public Contacts(String reportContactName, int reportContactID){
        this.contactName = reportContactName;
        this.reportContactID = reportContactID;
    }


    /**
     * Contacts Getters
     * @return contactID, contactName, contactEmail
     */
    public int getContactID() {return contactID;}

    public String getContactName() {return contactName;}

    public String getReportContactName() {return reportContactName;}

    public int getReportContactID() {return reportContactID;}

    public Contacts getContacts() {return contacts;}

    public String getContactToString(){return "[" + contactID + "]" + contactName;}
    public Contacts getContact(){return contacts;}


    /**
     * override for handling strings in the comboboxes.
     * @return contactID
     */
    @Override
    public String toString() {return (contactID + " : " + contactName);}


    /**
     * Contacts Setters
     * @param contactID
     */
    public void setContactID(int contactID) {this.contactID = contactID;}

    public void setContactName(String contactName) {this.contactName = contactName;}

    public int getContact_ID() {return contactID;}

    public void setReportContactName(String reportContactName) {this.reportContactName = reportContactName;}

    public void setReportContactID(int reportContactID) {this.reportContactID = reportContactID;}

    public void setContacts(Contacts contacts){this.contacts = contacts;}
}
