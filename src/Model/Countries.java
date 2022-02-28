package Model;

public class Countries {
    private int countryID;
    private String countryName;

    public Countries(int id, String name){
        this.countryID = id;
        this.countryName = name;
    }

    public int getId(){return countryID;}

    public String getName(){return countryName;}
}
