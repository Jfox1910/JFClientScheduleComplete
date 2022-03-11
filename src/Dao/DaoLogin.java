package Dao;

import Model.loginUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoLogin {

    public static ObservableList<loginUser> getAllUsers() {
        ObservableList<loginUser> userList = FXCollections.observableArrayList();

        try {
            String sqlQuery = ("SELECT User_Name, Password FROM client_schedule.users");
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String loginName = rs.getString("User_Name");
                String loginPassword = rs.getString("Password");

                loginUser loginuser = new loginUser(loginName, loginPassword);
                userList.add(loginuser);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}
