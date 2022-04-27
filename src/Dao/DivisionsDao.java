package Dao;

import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Division table DB access
 */
public class DivisionsDao {


    /**
     * Gets all divisions
     * @return allDivisions
     */
    public static ObservableList<Divisions> getAllDivisions(){
        ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Divisions divisions = new Divisions(divisionID, divisionName);
                allDivisions.add(divisions);
            }
            ps.close();
        } catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return allDivisions;
    }


    /**
     * Used in parsing divisions by their respective country.
     * @param divisionID
     * @return countryID
     */
    public static Integer getCountryDivision(int divisionID){
        int countryID = 0;

        try {
            String sql = "SELECT Country_ID from first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                countryID = rs.getInt("Country_ID");
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryID;
    }


    /**
     * Gets the division names
     * @param divisionID
     * @return
     */
    public static String getDivisionName(int divisionID){
        String divisionName = null;
        try {
            String sql = "SELECT Division FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                divisionName = rs.getString("Division");
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return divisionName;
    }


    /**
     * Provides names for countries used in various methods
     * @param modCustomerCountry
     * @return division
     */
    public static ObservableList<String> getAllByCountry(String modCustomerCountry){
        ObservableList<String>division = FXCollections.observableArrayList();
        int countryID = CountriesDao.getCountryName(modCustomerCountry);

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String divisionToString = rs.getString("Division");
                division.add(divisionToString);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return division;
    }


    /**
     * Gets divisions by name
     * @param divisionName
     * @return divisionID
     */
    public static int getAllDivisionsByName(String divisionName){
        int divisionID = 0;

        try {
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, divisionName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                divisionID = rs.getInt("Division_ID");
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return divisionID;
    }
}
