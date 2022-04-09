package Dao;

import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoDivisions {
    public static ObservableList<Divisions> getUsStates(){
        //array list of all US divisions
        ObservableList<Divisions> usStates = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Divisions divisions = new Divisions(divisionID, divisionName);
                usStates.add(divisions);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return usStates;
    }


    public static ObservableList<Divisions> getCanadianTerritories() {
        ObservableList<Divisions> canadianDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID = 3";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Divisions divisions = new Divisions(divisionID, divisionName);
                canadianDivisions.add(divisions);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return canadianDivisions;
    }


    public static ObservableList<Divisions> getUKTerritories() {
        ObservableList<Divisions> UkTerritories = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID = 2";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Divisions divisions = new Divisions(divisionID, divisionName);
                UkTerritories.add(divisions);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return UkTerritories;
    }


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


    public static ObservableList<String> getAllByCountry(String modCustomerCountry){
        ObservableList<String>division = FXCollections.observableArrayList();
        int countryID = DaoCountries.getCountryName(modCustomerCountry);

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

    public static int getAllDivisionsByName(String divisionName){
        int divisionID = 0;

        try {
            String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division = ?";
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
