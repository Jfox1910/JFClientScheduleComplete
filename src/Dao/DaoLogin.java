package Dao;

import Model.loginUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//This is the DAO class that checks the user table for the correct login credentials and stores it in an OL.
public class DaoLogin {

    public static ObservableList<loginUser> getAllUsers() {
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
}
