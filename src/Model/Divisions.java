package Model;

/**
 * Division object model class
 */
public class Divisions {

    private int divisionID;
    private String divisionName;

    public Divisions(int divisionID, String divisionName) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;

    }


    /**
     * Division Getters
     * @return divisionID, divisionName
     */
    public int getDivisionID() {return divisionID;}

    public String getDivisionName() {return divisionName;}


    /**
     * Division Setters
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {this.divisionID = divisionID;}

    public String divisionToString(){return "[" + divisionID + "]" + divisionName;}

    public void setDivisionName(String divisionName) {this.divisionName = divisionName;}
}


