package Model;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String createdOn;
    private String createdBy;
    private String lastUpdatedBy;
    private String lastUpdatedOn;
    private String tzOffset;

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

    public String getTzOffset() { return tzOffset; }

    public static String getCurrentTimezoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = (offsetInMillis >= 0 ? "+" : "-") + offset;
        return offset;
    }

    //Setters
    public void setUserId(int userId) {this.userId = userId;}

    public void setUserName(String userName) {this.userName = userName;}

    public void setUserPassword(String userPassword) {this.userPassword = userPassword;}

    public void setCreatedOn(String createdOn) {this.createdOn = createdOn;}

    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}

    public void setLastUpdatedOn(String lastUpdatedOn) {this.lastUpdatedOn = lastUpdatedOn;}


    public void setTzOffset (String tzOffset) { this.tzOffset = tzOffset; }

}
