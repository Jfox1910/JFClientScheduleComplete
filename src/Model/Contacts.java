package Model;

public class Contacts {

    private int contactID;
    private String contactName;
    private String contactEmail;

    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactID() {return contactID;}

    public String getContactName() {return contactName;}

    public String getContactEmail() {return contactEmail;}

    public void setContactID(int contactID) {this.contactID = contactID;}

    public void setContactName(String contactName) {this.contactName = contactName;}

    public void setContactEmail(String contactEmail) {this.contactEmail = contactEmail;}
}
