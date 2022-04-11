package Model;

public class loginUser {
    private int userId;
    private String userName;
    private String userPassword;
    private String createdOn;
    private String createdBy;
    private String lastUpdatedBy;
    private String lastUpdatedOn;

    //Constructor
    public loginUser(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = password;
    }

    //Getters
    public int getUserId() {return userId;}

    public String getUserName() {return userName;}

    public String getUserPassword() {return userPassword;}

    public String getCreatedOn() {return createdOn;}

    public String getCreatedBy() {return createdBy;}

    public String getLastUpdatedBy() {return lastUpdatedBy;}

    public String getLastUpdatedOn() {return lastUpdatedOn;}

    public String userNameString() {return this.userName;}


    //Setters
    public void setUserId(int userId) {this.userId = userId;}

    public void setUserName(String userName) {this.userName = userName;}

    public void setUserPassword(String userPassword) {this.userPassword = userPassword;}

    public void setCreatedOn(String createdOn) {this.createdOn = createdOn;}

    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}

    public void setLastUpdatedOn(String lastUpdatedOn) {this.lastUpdatedOn = lastUpdatedOn;}
}
