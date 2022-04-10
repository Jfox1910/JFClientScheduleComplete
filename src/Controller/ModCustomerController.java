package Controller;

import Dao.DaoAppointments;
import Dao.DaoCountries;
import Dao.DaoCustomers;
import Dao.DaoDivisions;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import Controller.MainScreenController;
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

public class ModCustomerController implements Initializable{


    @FXML private TextField CustomerID;
    @FXML private TextField CustomerName;
    @FXML private TextField CustomerAddress;
    @FXML private TextField CustomerZip;
    @FXML private TextField CustomerPhone;
    @FXML private ComboBox customerCountry;
    @FXML private ComboBox customerDivision;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final Customers customerAppt = Appointments.customers;
    private Customers customers = MainScreenController.getSelectedCustomer();
    int retrieveDivisionID = 0;

    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    public ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();
    public ObservableList<Appointments> allAppointments = DaoAppointments.getAllAppointments();

    //private ObservableList<Customers> customers;

    private ObservableList<Appointments> appointments;

    private final ObservableList<Countries> countries = FXCollections.observableArrayList();
    private final ObservableList<String> divID = FXCollections.observableArrayList();
    private final Customers modifyCustomer = AddApptController.customers;


//Exits back to the main screen
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


//ALL country names
/*    public ObservableList<String> getAllCountryNames(){
        ObservableList<String> allCountryNames = FXCollections.observableArrayList();
        for(int i = 0; i < allCountries.size(); i++)
        {
            String countryName;
            countryName = allCountries.get(i).getName();
            allCountryNames.add(countryName);
        }return allCountryNames;
    }
//US States
    public ObservableList<String> getUSDivisionNames(){
        ObservableList<String> USDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < usDivisionsList.size(); i++)
        {String americans;
            americans = usDivisionsList.get(i).getDivisionName();
            USDivisionNames.add(americans);
        }return USDivisionNames;
    }
//CANADIAN Territories
    public ObservableList<String> getCanadaDivisionNames(){
        ObservableList<String> CanadaDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < canadianDivisionList.size(); i++)
        {String canadians;
            canadians = canadianDivisionList.get(i).getDivisionName();
            CanadaDivisionNames.add(canadians);
        }return CanadaDivisionNames;
    }
//UK Territories
    public ObservableList<String> getUKDivisionNames(){
        ObservableList<String> UKDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < UKDivisionList.size(); i++)
        {String british;
            british = UKDivisionList.get(i).getDivisionName();
            UKDivisionNames.add(british);
        }return UKDivisionNames;
    }*/


//Handles populating the COUNTRY combobox customer tableview
    public void handleCountryComboBox(){

        String selectedCustomerCountry = customerCountry.getValue().toString();
        customerDivision.setItems(divisionsByCountry(selectedCustomerCountry));

/*        String getCountry;
        String getDivision = null;
        ObservableList<Divisions> selectedCustomerDiv;
        int customerDiv = customers.getCustomerDivision();
        if (customerDiv <= 54) {
            selectedCustomerDiv = DaoDivisions.getUsStates();
            for (Divisions divisions : selectedCustomerDiv) {
                if (divisions.getDivisionID() == customerDiv) {
                    getDivision = divisions.getDivisionName();
                }
            }
            getCountry = "U.S";
        } else if (customerDiv > 55 && customerDiv < 99) {
            getCountry = "Canada";
            selectedCustomerDiv = DaoDivisions.getCanadianTerritories();
            for (Divisions divisions : selectedCustomerDiv) {
                if (divisions.getDivisionID() == customerDiv) {
                    getDivision = divisions.getDivisionName();
                }
            }
        } else {
            getCountry = "UK";
            selectedCustomerDiv = DaoDivisions.getUKTerritories();
            for (Divisions divisions : selectedCustomerDiv) {
                if (divisions.getDivisionID() == customerDiv) {
                    getDivision = divisions.getDivisionName();
                }

            }
        }*/


    }

        /*String selectedCountry = customerCountry.getValue().toString();
        customerDivision.setItems(divisionsByCountry(selectedCountry));

        if(customerDivision.getItems().isEmpty()){
            customerDivision.setDisable(true);
        }
        else{
            customerDivision.setDisable(false);
        }*/
        //if(customerCountry.getSelectionModel().getSelectedItem() != null) {


/*            Object selectedCountry = customerCountry.getSelectionModel().getSelectedItem();
            String countryDivision = selectedCountry.toString();

            if (countryDivision.equalsIgnoreCase("U.S")) {
                customerDivision.setItems(getUSDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("UK")) {
                customerDivision.setItems(getUKDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("Canada")) {
                customerDivision.setItems(getCanadaDivisionNames());
            }
        //}
    }
    //US division selections
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

//Handles populating the DIVISION combobox customer tableview
/*    public int handleDivisionComboBox(ActionEvent actionEvent){
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
    }*/


//Collects the information from the fields and updates the customer in the database
    public void onActionUpdateCustomer(ActionEvent actionEvent) throws IOException {

        int customerId = Integer.parseInt(CustomerID.getText());
        String customerName = CustomerName.getText();
        String customerAddress = CustomerAddress.getText();
        String customerZip = CustomerZip.getText();
        String customerPhone = CustomerPhone.getText();
        int modCustomerDivision = DaoDivisions.getAllDivisionsByName(customerDivision.getValue().toString());


//Hold and confirm that a customer is about to be updated. If Okd moves forward with the operation and modifies the selected customer then reloads the main table.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Updating an existing customer.");
            alert.setContentText("By clicking OK, you will be updating " + CustomerName.getText() + "'s information. Are you sure you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DaoCustomers.modifyCustomer(customerId, customerName, customerAddress, customerZip, customerPhone, modCustomerDivision);
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Success!");
                alert2.setContentText(CustomerName.getText() + " has been updated.");
                alert2.showAndWait();

                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("View/mainScreen.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
    }

//Loads the modify customer screen fields with the information of the selected customer.
    public void getCustomer(Customers customers){
        CustomerID.setText(String.valueOf(customers.getCustomerId()));
        CustomerName.setText(String.valueOf(customers.getCustomerName()));
        CustomerAddress.setText(String.valueOf(customers.getCustomerAddy()));
        CustomerPhone.setText(String.valueOf(customers.getCustomerPhone()));
        CustomerZip.setText(String.valueOf(customers.getCustomerZip()));
        customerCountry.setValue(DaoCustomers.getCountry(customers));
        customerDivision.setValue(customers.getDivisionName());

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getCustomer(customers);

        customerCountry.setItems(countryList());
        customerDivision.setItems(divisionList());


    /*    if(customerDivision.getItems() == null) {
            customerDivision.setDisable(true);
        }
        else {
            customerDivision.setDisable(false);
        }*/

        //Divisions divisionToModify = MainScreenController.getCustomerDivision();
        //customerCountry.getItems().addAll(getAllCountryNames());
        //int customerDivisionId = modifyCustomer.getCustomerDivision();
        //setInitialDivision(customerDivisionId);

        /*CustomerID.setText(String.valueOf(customerToModify.getCustomerId()));
        CustomerName.setText(String.valueOf(customerToModify.getCustomerName()));
        CustomerAddress.setText(String.valueOf(customerToModify.getCustomerAddy()));
        CustomerPhone.setText(String.valueOf(customerToModify.getCustomerPhone()));
        CustomerZip.setText(String.valueOf(customerToModify.getCustomerZip()));
        customerCountry.setValue(DaoCustomers.getCountry(customerToModify));
        customerDivision.setValue(customerToModify.getDivisionName());
        //customerDivision.setValue(customerToModify.getCustomerDivision());*/

    }
}
