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


    @FXML private Label CustomerID;
    @FXML private TextField CustomerName;
    @FXML private TextField CustomerAddress;
    @FXML private TextField CustomerZip;
    @FXML private TextField CustomerPhone;
    @FXML private TextField addCustomerCreatedBy;
    @FXML private ComboBox customerCountry;
    @FXML private ComboBox customerDivision;
    @FXML private Button onActionUpdateCustomer;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Customers modifyCustomers;
    int retrieveDivisionID = 0;
    int CustomerId;
    String userName;

    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    public ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();
    public ObservableList<Appointments> allAppointments = DaoAppointments.getAllAppointments();

    private ObservableList<Customers> customers;
    private ObservableList<Appointments> appointments;

    private final ObservableList<Countries> countries = FXCollections.observableArrayList();
    private final ObservableList<String> divID = FXCollections.observableArrayList();
    private final Customers modifyCustomer = AddApptController.customers;



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

    //Handles populating the COUNTRY combobox customer tableview
    public void handleCountryComboBox(ActionEvent actionEvent){

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

    //Handles populating the DIVISION combobox customer tableview
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

    //Retrieve the selected customer
    public void getSelectedCustomer(Customers customers){

        selectedCustomer = customers;
        CustomerID.setText(String.valueOf(customers.getCustomerId()));
        CustomerName.setText(String.valueOf(customers.getCustomerName()));
        customerCountry.getSelectionModel().select(customers.getCustomerDivision());
        CustomerAddress.setText(String.valueOf(customers.getCustomerAddy()));
        CustomerPhone.setText(String.valueOf(customers.getCustomerPhone()));
        CustomerZip.setText(String.valueOf(customers.getCustomerZip()));
    }


    public void onActionUpdateCustomer(ActionEvent actionEvent) throws IOException {

        //if (customersTableView.getSelectionModel().getSelectedItem() != null) {
            String customerName = CustomerName.getText();
            String customerAddress = CustomerAddress.getText();
            String customerZip = CustomerZip.getText();
            String customerPhone = CustomerPhone.getText();
            int modCustomerDivision = customerDivision.getSelectionModel().getSelectedIndex() + 1;

//Hold and confirm that a customer is about to be updated. If Okd moves forward with the operation and modifies the selected customer.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Updating an existing customer.");
            alert.setContentText("By clicking OK, you will be updating " + CustomerName.getText() + "'s information. Are you sure you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DaoCustomers.modifyCustomer(modifyCustomers.getCustomerId(), customerName, customerAddress, customerZip, customerPhone, modCustomerDivision);
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("SUCCESS!");
                alert2.setContentText("The customer has been updated.");
                alert2.showAndWait();
                //clearItems(actionEvent);
                //customersTableView.setItems(DaoCustomers.getAllCustomers());
            }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerCountry.getItems().addAll(getAllCountryNames());

        System.out.println(modifyCustomer.getCustomerId());
        CustomerID.setText(String.valueOf(modifyCustomer.getCustomerId()));
        CustomerName.setText(String.valueOf(modifyCustomer.getCustomerName()));
        customerCountry.getSelectionModel().select(modifyCustomer.getCustomerDivision());
        CustomerAddress.setText(String.valueOf(modifyCustomer.getCustomerAddy()));
        CustomerPhone.setText(String.valueOf(modifyCustomer.getCustomerPhone()));
        CustomerZip.setText(String.valueOf(modifyCustomer.getCustomerZip()));
    }
}
