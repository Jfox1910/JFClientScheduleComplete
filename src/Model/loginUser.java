package Model;

public class loginUser {
    private String userName;
    private String userPassword;

    //Constructor
    public loginUser(String name, String password){
        this.userName = name;
        this.userPassword = password;
    }

    //Getters
    public String getUserName() {
        return userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    //Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
