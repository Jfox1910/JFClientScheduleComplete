package Model;

/**
 * User model object class.
 */
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String createdOn;
    private String createdBy;
    private String lastUpdatedBy;
    private String lastUpdatedOn;

    //Constructor
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = password;
    }


    public User(String userName){
        this.userName = userName;
    }
    public User(int user_id) {this.userId = user_id; }


    //Getters
    public int getUserId() {return userId;}

    public String getUserName() {return userName;}

    public String getUserPassword() {return userPassword;}

    public String getCreatedOn() {return createdOn;}

    public String getCreatedBy() {return createdBy;}

    public String getLastUpdatedBy() {return lastUpdatedBy;}

    public String getLastUpdatedOn() {return lastUpdatedOn;}

    public String userNameString() {return this.userName;}


    /**
     * override for handling string issues in the comboboxes.
     * @return userId
     */
    @Override
    public String toString() {return (userId + " : " + userName);}


    //Setters
    public void setUserId(int userId) {this.userId = userId;}

    public void setUserName(String userName) {this.userName = userName;}

    public void setUserPassword(String userPassword) {this.userPassword = userPassword;}

    public void setCreatedOn(String createdOn) {this.createdOn = createdOn;}

    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}

    public void setLastUpdatedOn(String lastUpdatedOn) {this.lastUpdatedOn = lastUpdatedOn;}

}
