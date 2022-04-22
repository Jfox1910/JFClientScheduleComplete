package Dao;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDao {
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries country = new Countries(countryID,countryName);
                countries.add(country);
            }
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countries;
    }

    public static int getCountryName(String countryName){
        int countryID = 0;
        try {
            String sql = "SELECT * from countries WHERE country = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                countryID = rs.getInt("Country_ID");
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
    }
    return countryID;
}

    public static String getCountryID(int countryID){
        String countryName = null;
        try {
            String sql = "SELECT * from countries WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                countryName = rs.getString("Country");
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryName;
    }


}
