package Controller;

import Dao.*;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
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
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML private TextField addCustomerID;
    @FXML private TextField addCustomerName;
    @FXML private TextField addCustomerAddy;
    @FXML private TextField addCustomerPostal;
    @FXML private TextField addCustomerPhone;
    @FXML private TextField addCustomerCreatedBy;
    @FXML private ComboBox addCustomerCountry;
    @FXML private ComboBox addCustomerDivision;
    @FXML private Button addCustomerButton;
    private ComboBox<String> clearCountry;

    @FXML private TextField username;

    int retrieveDivisionID = 0;
    int divisionId = 0;

    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    private ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();
    public ObservableList<Appointments> allAppointments = DaoAppointments.getAllAppointments();

    private Stage stage;
    private Scene scene;
    private Parent root;

    /* TODO LIST
    MUST HAVE
    Add customer to DB
    ALERT for deleting or modifying
    fix handleCountryBox populating issue. Add a clear function ----addCustomerCountry.setValue(null);----
    link to DaoCustomers
    get customerID

    NICE TO HAVE ITEMS
    maybe simplfy countryname methods? Kinda excessive
    Change labels based on country selection? What are the UK "divisions" called?
    lambda function. Alerts?
     */

    public void onActionAddCustomer(ActionEvent actionEvent){

        //Retrieves the customers info from the fields.
        String customerName = addCustomerName.getText();
        String customerAddress = addCustomerAddy.getText();
        String customerZip = addCustomerPostal.getText();
        String customerPhone = addCustomerPhone.getText();
        String loggedInUser = username.getText();
        int divisionId = addCustomerDivision.getSelectionModel().getSelectedIndex();



        //Check that a name, address and phone has been entered and gives an alert if it isn't there.
        if (customerName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("A name must be entered for the customer.");
            alert.showAndWait();
            return;
        }if (customerAddress.isEmpty() || customerPhone.isEmpty() || customerZip.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("Complete contact information must be entered for the customer.");
            alert.showAndWait();
            return;
        }else {
            DaoCustomers.newCustomer(customerName, customerAddress, customerZip, customerPhone, loggedInUser, divisionId);
        }
        System.out.println("Testing Add Customer Button");
    }

    //Handles populating the country combobox in the addCustomerScreen
    public void handleCountryComboBox(ActionEvent actionEvent){

        addCustomerCountry.getItems().addAll(getAllCountryNames());

        if(addCustomerCountry.getSelectionModel().getSelectedItem() != null) {
            Object selectedCountry = addCustomerCountry.getSelectionModel().getSelectedItem();
            String countryDivision = selectedCountry.toString();
            //clearCountry.getSelectionModel().clearSelection();
            //clearCountry.clear();

            if (countryDivision.equalsIgnoreCase("U.S")) {
                addCustomerDivision.setItems(getUSDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("UK")) {
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

    public int handleDivisionComboBox(ActionEvent actionEvent){
        if(addCustomerDivision.getSelectionModel().getSelectedItem() != null) {
            Object selectedDivision = addCustomerDivision.getSelectionModel().getSelectedItem();

            String d = selectedDivision.toString();
            for (int i = 0; i < DaoDivisions.getAllDivisions().size(); i++) {
                if (d.equalsIgnoreCase(DaoDivisions.getAllDivisions().get(i).getDivisionName())) {
                    retrieveDivisionID = DaoDivisions.getAllDivisions().get(i).getDivisionID();
                    break;
                }
            }
        }
        return retrieveDivisionID;
    }

  /*  public void handleComboBoxClosed(ActionEvent actionEvent){
        addCustomerCountry.setValue(null);
        System.out.println("Testing clear method");
    }*/


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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initializes the customer/country combobox
        addCustomerCountry.getItems().addAll(getAllCountryNames());



    }
}
