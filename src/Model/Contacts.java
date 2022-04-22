package Model;

/**
 * Contact model object class.
 */
public class Contacts {

    private int contactID;
    private String contactName;

    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public static int getContactIDByName(String contactName) {
        int contactID = Integer.parseInt(contactName.substring(0, contactName.indexOf(":")));
        return contactID;
    }


    /**
     * Contacts Getters
     * @return contactID, contactName, contactEmail
     */
    public int getContactID() {return contactID;}

    public String getContactName() {return contactName;}


    /**
     * Contacts Setters
     * @param contactID
     */
    public void setContactID(int contactID) {this.contactID = contactID;}

    public void setContactName(String contactName) {this.contactName = contactName;}
}
