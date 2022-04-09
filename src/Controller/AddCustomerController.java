package Controller;

import Dao.*;
import Model.*;
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

    @FXML private TextField CustomerName;
    @FXML private TextField CustomerAddress;
    @FXML private TextField CustomerZip;
    @FXML private TextField CustomerPhone;
    @FXML private ComboBox customerCountry;
    @FXML private ComboBox customerDivision;
    @FXML private ComboBox userCombo;

    int retrieveDivisionID = 0;
    int retrieveUserID = 0;

    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    public ObservableList<Divisions> UKDivisionList = DaoDivisions.getUKTerritories();
    public ObservableList<loginUser> allTheUsers = DaoLogin.getAllUsers();
    public ObservableList<Appointments> allAppointments = DaoAppointments.getAllAppointments();

    private Stage stage;
    private Scene scene;
    private Parent root;

//Add a customer method (Contained within the customer tab)
    public void onActionSaveCustomer(ActionEvent event) throws IOException {

//Retrieves the customer's info from the fields.
        String customerName = CustomerName.getText();
        String customerAddress = CustomerAddress.getText();
        String customerZip = CustomerZip.getText();
        String customerPhone = CustomerPhone.getText();
        int divisionID = retrieveDivisionID;
        userCombo.getSelectionModel().getSelectedItem();
        System.out.println(retrieveUserID);
        int userID = retrieveUserID;

//Check that a name, address and phone has been entered and gives an alert if it isn't there.
        if (customerName.isEmpty() || customerAddress.isEmpty() || customerPhone.isEmpty() || customerZip.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All customer fields must be filled before saving.");
            alert.showAndWait();
        }else
        {

//popup confirmation using a LAMBDA EXPRESSION confirming that a customer is about to be added.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new customer.");
            alert.setContentText("By clicking OK, you will be adding " + CustomerName.getText() + " to the system. Are you sure you wish to continue?");
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    DaoCustomers.newCustomer(userID, customerName, customerAddress, customerZip, customerPhone, divisionID);


//Confirmation that the customer has been added.
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Success!");
                    alert2.setContentText(CustomerName.getText() + " has been added.");
                    alert2.showAndWait();

                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getClassLoader().getResource("View/mainScreen.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }));
        }
    }


//Handles populating the country and division comboboxes
    public void handleCountryComboBox(ActionEvent actionEvent){

        String selectedCustomerCountry = customerCountry.getValue().toString();
        customerDivision.setItems(divisionsByCountry(selectedCustomerCountry));

/*        if(customerCountry.getSelectionModel().getSelectedItem() != null) {
            Object selectedCountry = customerCountry.getSelectionModel().getSelectedItem();
            String countryDivision = selectedCountry.toString();

            if (countryDivision.equalsIgnoreCase("U.S")) {
                customerDivision.setItems(getUSDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("UK")) {
                customerDivision.setItems(getUKDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("Canada")) {
                customerDivision.setItems(getCanadaDivisionNames());
            }
        }*/
    }


//Handles populating the user combobox

    public void handleUserCombobox(ActionEvent actionEvent){

        Object selectedUser = userCombo.getSelectionModel().getSelectedItem();
        String allUserNames = selectedUser.toString();

        allUserNames.equalsIgnoreCase("User_Name");
    }

    public ObservableList<String> allUserNames(){
        ObservableList<String> allUserNames = FXCollections.observableArrayList();
        for (loginUser allUsers : allTheUsers){
            String loginName;
            loginName = allUsers.getUserName();
            allUserNames.add(loginName);
        }return allUserNames;
    }

//Retrieves the country names from the database
/*    public ObservableList<String> allCountryNames(){
        ObservableList<String> allCountryNames = FXCollections.observableArrayList();
        for (Countries allCountry : allCountries) {
            String countryName;
            countryName = allCountry.getName();
            allCountryNames.add(countryName);
        }
        return allCountryNames;
    }*/

//Handler for the division combobox.
    public int handleDivisionComboBox(ActionEvent actionEvent){
        if(customerDivision.getSelectionModel().getSelectedItem() != null) {
            Object selectedDivision = customerDivision.getSelectionModel().getSelectedItem();
            String division = selectedDivision.toString();
            for (int i = 0; i < DaoDivisions.getAllDivisions().size(); i++) {
                if (division.equalsIgnoreCase(DaoDivisions.getAllDivisions().get(i).getDivisionName())) {
                    retrieveDivisionID = DaoDivisions.getAllDivisions().get(i).getDivisionID();
                    break;
                }
            }
        }
        return retrieveDivisionID;
    }

/*//US division selections
    public ObservableList<String> getUSDivisionNames(){
        ObservableList<String> USDivisionNames = FXCollections.observableArrayList();
        for (Divisions divisions : usDivisionsList) {
            String americans;
            americans = divisions.getDivisionName();
            USDivisionNames.add(americans);
        }
        return USDivisionNames;
    }

//Canadian division selections
    public ObservableList<String> getCanadaDivisionNames(){
        ObservableList<String> CanadaDivisionNames = FXCollections.observableArrayList();
        for (Divisions divisions : canadianDivisionList) {
            String canadians;
            canadians = divisions.getDivisionName();
            CanadaDivisionNames.add(canadians);
        }
        return CanadaDivisionNames;
    }

//UK division selections
    public ObservableList<String> getUKDivisionNames(){
        ObservableList<String> UKDivisionNames = FXCollections.observableArrayList();
        for (Divisions divisions : UKDivisionList) {
            String british;
            british = divisions.getDivisionName();
            UKDivisionNames.add(british);
        }
        return UKDivisionNames;
    }*/


//Exit to the main screen
    public void onActionMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All unsaved information will be lost. Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/mainScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
    }

    public ObservableList countryList(){
        ObservableList<String> countries = FXCollections.observableArrayList();

        for(Countries country : DaoCountries.getAllCountries()){
            countries.add(country.getName());
        }
        return countries;
    }

    public ObservableList divisionList(){
        ObservableList<String> divisions = FXCollections.observableArrayList();

        for(Divisions division : DaoDivisions.getAllDivisions()){
            divisions.add(division.getDivisionName());
        }
        return divisions;
    }

    public ObservableList divisionsByCountry(String modCustomerCountry){
        ObservableList<String> divisions = FXCollections.observableArrayList();

        for(String divisionName : DaoDivisions.getAllByCountry(modCustomerCountry)){
            divisions.add(divisionName);
        }
        return divisions;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerCountry.setItems(countryList());
        customerDivision.setItems(divisionList());

        //Initializes the customer/country combobox
       // customerCountry.getItems().addAll(allCountryNames());
        userCombo.getItems().addAll(allUserNames());





    }
}
