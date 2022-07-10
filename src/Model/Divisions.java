package Model;

import javafx.collections.ObservableList;
import static Dao.DivisionsDao.getAllDivisions;

/**
 * Division object model class
 */
public class Divisions {

    private int divisionID;
    private String divisionName;

    private int countryID;

    public Divisions(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;

        this.countryID = countryID;

    }

    public Divisions(int divisionID, String divisionName) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    public static Divisions divisionByCountry(int divisionID) {
        ObservableList<Divisions> divisions =  getAllDivisions();
        Divisions division = null;
        for (Divisions selectedDivision : divisions) {
            if (selectedDivision.getDivisionID() != divisionID) {
                continue;
            } else {
                division = selectedDivision;
                break;
            }
        }
        return division;
    }

    /**
     * Division Getters
     * @return divisionID, divisionName
     */
    public int getDivisionID() {return divisionID;}

    public String getDivisionName() {return divisionName;}

    public int getCountryID() {return countryID;}

    /**
     * Division Setters
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {this.divisionID = divisionID;}

    public String divisionToString(){return "[" + divisionID + "]" + divisionName;}

    public void setDivisionName(String divisionName) {this.divisionName = divisionName;}

    public void setCountryID(int countryID) {this.countryID = countryID;}

    /**
     * override for handling strings in the comboboxes.
     * @return divisionID : divisionName
     */
    @Override
    public String toString() {return (divisionID + " : " + divisionName);}

}


