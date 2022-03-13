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
                int divisonID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Divisions divisions = new Divisions(divisonID, divisionName);
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
                int divisonID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Divisions divisions = new Divisions(divisonID, divisionName);
                allDivisions.add(divisions);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return allDivisions;
    }

}
