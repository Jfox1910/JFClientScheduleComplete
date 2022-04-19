package Dao;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *This is the USER class that checks the user table for the correct login credentials and stores it in an OL.
 */
public class DaoUser {

    private static User loggedinUser;
    public static User getLoggedinUser() { return loggedinUser; }


    /**
     *Get all users from the user table in the DB
     * @return list of all customers from the DB
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sqlQuery = ("SELECT User_ID, User_Name, Password FROM client_schedule.users");
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int loginId = rs.getInt("User_ID");
                String loginName = rs.getString("User_Name");
                String loginPassword = rs.getString("Password");

                User loginuser = new User(loginId, loginName, loginPassword);
                userList.add(loginuser);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }


    /**
     * boolean used in the login method to pull users and passwords from the DB for comparison. Also used to log additions and changes made to customers and appointments.
     * @param username
     * @param password
     * @return
     */
    public static boolean loggedInUser(String username, String password) throws SQLException {
        ObservableList<User> loggedInUser = FXCollections.observableArrayList();
        String sqlQuery = "select * from users where User_ID =? AND Password =? or User_Name=? AND Password =?";
        //JDBC.makePreparedStatement(query, con);
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
        assert ps != null;
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, username);
        ps.setString(4, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            loggedinUser = new User((rs.getInt("User_ID")));
            loggedinUser.setUserName(rs.getString("User_Name"));
            //liveUser.setPassword(rs.getString("Password"));
            //liveUser.setCreateDate(rs.getString("Create_Date"));
            //liveUser.setCreateBy(rs.getString("Created_By"));
            //liveUser.setLastUpdate(rs.getString("Last_Update"));
            loggedinUser.setLastUpdatedBy(rs.getString("Last_Updated_By"));
           // loggedInUser.setTzOffset(tzOffset);
            //Logger.log(liveUser.getUserName(), Boolean.TRUE);
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }
}
