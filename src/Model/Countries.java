package Model;


import javafx.collections.ObservableList;
import static Dao.CountriesDao.getAllCountries;

/**
 * Country model object class.
 */
public class Countries {
    private int countryID;
    private String countryName;

    private int divisionID;

    private String divisionName;

    public Countries(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }


    /**
     * Countries Getters
     * @return countryID, countryName
     */
    public int getId(){return countryID;}
    public String getName(){return countryName;}
    public int getCountryID() {return countryID;}

    public void setCountryID(int countryID) {this.countryID = countryID;}

    public String getCountryName() {return countryName;}

    public void setCountryName(String countryName) {this.countryName = countryName;}

    public int getDivisionID() {return divisionID;}

    public void setDivisionID(int divisionID) {this.divisionID = divisionID;}

    public String getDivisionName() {return divisionName;}

    public void setDivisionName(String divisionName) {this.divisionName = divisionName;}

    public static Countries countryByDivision(int customerCountryID) {
        ObservableList<Countries> division =  getAllCountries();

        Countries country = null;

        for (Countries selectCountry : division) {
            if (selectCountry.getCountryID() != customerCountryID) {
                continue;
            } else {
                country = selectCountry;
                break;
            }

        }

        return country;
    }


    /**
     * override for handling strings in the comboboxes.
     * @return countryID : countryName
     */
    @Override
    public String toString() {return (countryName);}

}
