package Model;


/**
 * Country model object class.
 */
public class Countries {
    private int countryID;
    private String countryName;

    public Countries(int id, String name){
        this.countryID = id;
        this.countryName = name;
    }


    /**
     * Countries Getters
     * @return countryID, countryName
     */
    public int getId(){return countryID;}
    public String getName(){return countryName;}
}
