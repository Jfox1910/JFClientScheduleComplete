package Controller;

import Dao.DaoDivisions;
import Dao.DaoCountries;
import Model.Countries;
import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML private TextField addCustomerID;
    @FXML private TextField addCustomerName;
    @FXML private TextField addCustomerAddy;
    @FXML private TextField addCustomerPostal;
    @FXML private TextField addCustomerPhone;
    @FXML private ComboBox addCustomerCountry;
    @FXML private ComboBox addCustomerDivision;
    @FXML private Button addCustomerButton;

    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    private ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();
    //public ObservableList<Divisions> allDivisions = DaoDivisions.getAllDivisions;

    private Stage stage;
    private Scene scene;
    private Parent root;


    //Handles populating the country combobox in the addCustomerScreen
    public void handleCountryComboBox(ActionEvent actionEvent){

        addCustomerCountry.getItems().addAll(getAllCountryNames());

        if(addCustomerCountry.getSelectionModel().getSelectedItem() != null) {
            Object selectedCountry = addCustomerCountry.getSelectionModel().getSelectedItem();
            String countryDivision = selectedCountry.toString();
            if (countryDivision.equalsIgnoreCase("United States")) {
                addCustomerDivision.setItems(getUSDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("United Kingdom")) {
                addCustomerDivision.setItems(getUKDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("Canada")) {
                addCustomerDivision.setItems(getCanadaDivisionNames());
            }

        }
    }

    //Retrieves the country names from DaoCountries
    public ObservableList<String> getAllCountryNames(){
        ObservableList<String> allCountryNames = FXCollections.observableArrayList();
        for(int i = 0; i < allCountries.size(); i++)
        {
            String countryName;
            countryName = allCountries.get(i).getName();
            allCountryNames.add(countryName);
        }
        return allCountryNames;
    }

    public ObservableList<String> getUSDivisionNames(){
        ObservableList<String> USDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < usDivisionsList.size(); i++)
        {
            String americans;
            americans = usDivisionsList.get(i).getDivisionName();
            USDivisionNames.add(americans);
        }
        return USDivisionNames;
    }

    public ObservableList<String> getCanadaDivisionNames(){
        ObservableList<String> CanadaDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < canadianDivisionList.size(); i++)
        {
            String canadians;
            canadians = canadianDivisionList.get(i).getDivisionName();
            CanadaDivisionNames.add(canadians);
        }
        return CanadaDivisionNames;
    }

    public ObservableList<String> getUKDivisionNames(){
        ObservableList<String> UKDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < UKDivisionList.size(); i++)
        {
            String british;
            british = UKDivisionList.get(i).getDivisionName();
            UKDivisionNames.add(british);
        }
        return UKDivisionNames;
    }

/*    public int handleDivisionComboBox(ActionEvent actionEvent){
        if(addCustomerDivision.getSelectionModel().getSelectedItem() != null) {
            Object selectedDivision = addCustomerDivision.getSelectionModel().getSelectedItem();

            String d = selectedDivision.toString();
            for (int i = 0; i < DaoDivisions.getAllDivisions().size(); i++) {
                if (d.equalsIgnoreCase(DaoDivisions.getAllDivisions().get(i).getDivisionName())) {
                    selectedDivisionID = DaoDivisions.getAllDivisions().get(i).getDivisionID();
                    break;
                }

            }
        }
        return selectedDivisionID;
    }*/


    public void onActionAddCustomer(){

        //Dao.DaoCustomers.newCustomer();

        //.setVisibleRowCount(5);
        //.getSelectionModel().selectFirst();
        System.out.println("Testing Add Customer Button");

    }

    //Exit to the main screen
    public void onActionMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All unsaved information will be lost. Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
    }

    /*public int onActionSelectCountry(ActionEvent actionEvent){

    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initializes the customer/country combobox
        addCustomerCountry.getItems().addAll(getAllCountryNames());



    }
}
