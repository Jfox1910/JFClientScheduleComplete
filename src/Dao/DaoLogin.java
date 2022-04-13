package Dao;

import Model.loginUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This is the DAO class that checks the user table for the correct login credentials and stores it in an OL.
 *
 */
public class DaoLogin {

    private static loginUser loggedInUser;
    //public static loginUser getLoggedInUser(){return loggedInUser;}

    /** Get all users from the user table in the DB
     *
     * @return list of all customers from the DB
     */
    public static ObservableList<loginUser> getAllUsers(){
        ObservableList<loginUser> userList = FXCollections.observableArrayList();

        try {
            String sqlQuery = ("SELECT User_ID, User_Name, Password FROM client_schedule.users");
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int loginId = rs.getInt("User_ID");
                String loginName = rs.getString("User_Name");
                String loginPassword = rs.getString("Password");

                loginUser loginuser = new loginUser(loginId, loginName, loginPassword);
                userList.add(loginuser);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static ObservableList<loginUser> getLoggedInUser(){
        ObservableList<loginUser> loggedInUser = FXCollections.observableArrayList();
        try {
            String sqlQuery = ("SELECT User_ID, User_Name, Password FROM client_schedule.users");
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int loginId = rs.getInt("User_ID");
                String loginName = rs.getString("User_Name");
                String loginPassword = rs.getString("Password");

                loginUser loginuser = new loginUser(loginId, loginName, loginPassword);
                loggedInUser.add(loginuser);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return loggedInUser;
    }

    /**
     * boolean used in the login method to pull users and passwords from the DB for comparison.
     * @param username
     * @param password
     * @return
     */
    public static boolean loggedInUser(String username, String password) {
        try {
            String sqlQuery = "select 1 from users where User_Name = ? and Password = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();

            return resultSet.next();
        } catch (SQLException e){
            return false;
        }
    }


}
