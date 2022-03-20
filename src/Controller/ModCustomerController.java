package Controller;

import Dao.DaoAppointments;
import Dao.DaoCountries;
import Dao.DaoCustomers;
import Dao.DaoDivisions;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ModCustomerController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //SWITCH
    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    private ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();

    private final ObservableList<Countries> countries = FXCollections.observableArrayList();
    private final ObservableList<String> divID = FXCollections.observableArrayList();
    private final Customers modifyCustomer = AddApptController.customers;

    @FXML private TextField customerId;
    @FXML private TextField customerName;
    @FXML private TextField customerAddress;
    @FXML private TextField customerZip;
    @FXML private TextField customerPhone;
    @FXML private ComboBox customerDivision;
    @FXML private ComboBox customerCountry;

    int retrieveDivisionID = 0;
    Customers selectedCustomer;


    //Exits back to the main screen
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


    public ObservableList<String> getAllCountryNames(){
        ObservableList<String> allCountryNames = FXCollections.observableArrayList();
        for(int i = 0; i < allCountries.size(); i++)
        {
            String countryName;
            countryName = allCountries.get(i).getName();
            allCountryNames.add(countryName);
        }return allCountryNames;
    }

    public ObservableList<String> getUSDivisionNames(){
        ObservableList<String> USDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < usDivisionsList.size(); i++)
        {String americans;
            americans = usDivisionsList.get(i).getDivisionName();
            USDivisionNames.add(americans);
        }return USDivisionNames;
    }

    public ObservableList<String> getCanadaDivisionNames(){
        ObservableList<String> CanadaDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < canadianDivisionList.size(); i++)
        {String canadians;
            canadians = canadianDivisionList.get(i).getDivisionName();
            CanadaDivisionNames.add(canadians);
        }return CanadaDivisionNames;
    }

    public ObservableList<String> getUKDivisionNames(){
        ObservableList<String> UKDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < UKDivisionList.size(); i++)
        {String british;
            british = UKDivisionList.get(i).getDivisionName();
            UKDivisionNames.add(british);
        }return UKDivisionNames;
    }

    //Handles populating the country combobox in the addCustomerScreen
    public void handleCountryComboBox(ActionEvent actionEvent){
        //addCustomerCountry.getSelectionModel().clearSelection();
        //addCustomerName.clear();

        customerCountry.getItems().addAll(getAllCountryNames());

        if(customerCountry.getSelectionModel().getSelectedItem() != null) {
            Object selectedCountry = customerCountry.getSelectionModel().getSelectedItem();
            String countryDivision = selectedCountry.toString();

            if (countryDivision.equalsIgnoreCase("U.S")) {
                customerDivision.setItems(getUSDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("UK")) {
                customerDivision.setItems(getUKDivisionNames());
            } else if (countryDivision.equalsIgnoreCase("Canada")) {
                customerDivision.setItems(getCanadaDivisionNames());
            }
        }
    }

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

    public void onActionSelectCountry(ActionEvent event) throws IOException{
        System.out.println("TESTING COUNTRY SELECTION.");


    }

    //Retrieve the selected customer
    public void getSelectedCustomer(Customers customers){

        //SWITCH

        selectedCustomer = customers;
        customerId.setText(String.valueOf(customers.getCustomerId()));
        customerName.setText(String.valueOf(customers.getCustomerName()));
        customerCountry.getSelectionModel().select(customers.getCustomerDivision());
        customerAddress.setText(String.valueOf(customers.getCustomerAddy()));
        customerPhone.setText(String.valueOf(customers.getCustomerPhone()));
        customerZip.setText(String.valueOf(customers.getCustomerZip()));
    }


    public void onActionSaveChanges(ActionEvent event) throws IOException {

            //Retrieves the customer's info from the fields.
            String modifyCustomerName = customerName.getText();
            String modifyCustomerAddress = customerAddress.getText();
            String modifyCustomerZip = customerZip.getText();
            String modifyCustomerPhone = customerPhone.getText();
            String customerUpdatedBy = null;
            int modifyCustomerDivision = customerDivision.getSelectionModel().getSelectedIndex() + 1;

            //Check that a name, address and phone has been entered and gives an alert if it isn't there.
            if (customerName.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention!");
                alert.setContentText("A name must be entered for the customer.");
                alert.showAndWait();
                return;
            }if (customerAddress.getText().isEmpty() || customerPhone.getText().isEmpty() || customerZip.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention!");
                alert.setContentText("Complete contact information must be entered for the customer.");
                alert.showAndWait();
                return;
            }else {
                //popup confirmation confirming that a customer is about to be added.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modifying an existing customer.");
                alert.setContentText("By clicking OK, you will be modifying " + customerName.getText() + "'s information. Are you sure you wish to continue?");
                alert.showAndWait();
            }
            DaoCustomers.modifyCustomer(modifyCustomerName,modifyCustomerAddress, modifyCustomerZip, modifyCustomerPhone, customerUpdatedBy,modifyCustomerDivision);
            //popup alerting the user that a customer has been added to the db. Returns to the main screen when the OK button is clicked.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success!");
            alert.setContentText(customerName.getText() + " has been added.");
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

        customerCountry.getItems().addAll(getAllCountryNames());

        System.out.println(modifyCustomer.getCustomerId());
        customerId.setText(String.valueOf(modifyCustomer.getCustomerId()));
        customerName.setText(String.valueOf(modifyCustomer.getCustomerName()));
        customerCountry.getSelectionModel().select(modifyCustomer.getCustomerDivision());
        customerAddress.setText(String.valueOf(modifyCustomer.getCustomerAddy()));
        customerPhone.setText(String.valueOf(modifyCustomer.getCustomerPhone()));
        customerZip.setText(String.valueOf(modifyCustomer.getCustomerZip()));
    }
}
