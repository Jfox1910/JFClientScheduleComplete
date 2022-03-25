package Dao;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoCountries {
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries country = new Countries(countryId,countryName);
                countries.add(country);
            }
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countries;
    }
}
