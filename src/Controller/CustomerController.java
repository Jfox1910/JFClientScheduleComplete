/*
package Controller;

import Dao.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML public TableView<Customers> customersTableView;
    @FXML public TableColumn<Customers, Integer> customerIdCol;
    @FXML public TableColumn<Customers, String> customerNameCol;
    @FXML public TableColumn<Customers, String> customerAddyCol;
    @FXML public TableColumn<Customers, String> customerZipCol;
    @FXML public TableColumn<Countries, String> customerPhoneCol;
    @FXML public TableColumn<Customers, String> customerCreatedDateCol;
    @FXML public TableColumn<Customers,String> customerCreatedCol;
    @FXML public TableColumn<Customers, Timestamp> customerUpdatedOnCol;
    @FXML public TableColumn<Customers, String> customerUpdatedByCol;
    @FXML public TableColumn<Customers, Integer> customerDivisionCol;

    @FXML private TextField addCustomerID;
    @FXML private TextField addCustomerName;
    @FXML private TextField addCustomerAddy;
    @FXML private TextField addCustomerPostal;
    @FXML private TextField addCustomerPhone;
    @FXML private TextField addCustomerCreatedBy;
    @FXML private ComboBox addCustomerCountry;
    @FXML private ComboBox addCustomerDivision;


    @FXML public TextField CustomerName;
    @FXML public TextField CustomerAddress;
    @FXML public TextField CustomerZip;
    @FXML public TextField CustomerPhone;
    @FXML public ComboBox customerCountry;
    @FXML public ComboBox customerDivision;

    public static Customers customer;

    Customers modifyCustomers;
    int retrieveDivisionID = 0;
    int CustomerId;
    private boolean modifyCustomer = false;


    private ObservableList<Customers> customers;
    private ObservableList<Appointments> appointments;
    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    private ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();
    public ObservableList<Appointments> allAppointments = DaoAppointments.getAllAppointments();

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void saveCustomer(ActionEvent event) throws IOException {

        //Retrieves the customer's info from the fields.
        String customerName = CustomerName.getText();
        String customerAddress = CustomerAddress.getText();
        String customerZip = CustomerZip.getText();
        String customerPhone = CustomerPhone.getText();
        int divisionId = customerDivision.getSelectionModel().getSelectedIndex() + 1;

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
            //popup confirmation confirming that a customer is about to be added.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new customer.");
            alert.setContentText("By clicking OK, you will be adding " + CustomerName.getText() + " to the system. Are you sure you wish to continue?");
            alert.showAndWait();
        }
        DaoCustomers.newCustomer(customerName,customerAddress, customerZip, customerPhone, divisionId);
        //popup alerting the user that a customer has been added to the db. Returns to the main screen when the OK button is clicked.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setContentText(CustomerName.getText() + " has been added.");
        Optional<ButtonType> result = alert.showAndWait();
        //Reloads the customer table view with the updated information
        if (result.get() == ButtonType.OK) {
            customersTableView.setItems(DaoCustomers.getAllCustomers());
        }
    }

    //Modifies an existing customer. Throws an error if a name isn't selected, otherwise modifies the customer.
    public void onActionModifyCustomer(ActionEvent event) throws IOException {
        if (customersTableView.getSelectionModel().getSelectedItem() != null){

            modifyCustomer = true;
            int divID = customerDivision.getSelectionModel().getSelectedIndex() +1;
            modifyCustomers = customersTableView.getSelectionModel().getSelectedItem();

            CustomerId = modifyCustomers.getCustomerId();
            CustomerName.setText(String.valueOf(modifyCustomers.getCustomerName()));
            CustomerAddress.setText(String.valueOf(modifyCustomers.getCustomerAddy()));
            CustomerPhone.setText(String.valueOf(modifyCustomers.getCustomerPhone()));
            CustomerZip.setText(String.valueOf(modifyCustomers.getCustomerZip()));
            customerCountry.setValue(modifyCustomers.getCustomerDivision());
            customerDivision.setValue(modifyCustomers.getCustomerDivision());

            //DaoCustomers.modifyCustomer();
            System.out.println("Testing Modify Customer");
        }
        else {
            //Hold and alert the user that a customer name must be selected.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("A customer has not been selected. Please click on a customer name and try again.");
            alert.showAndWait();
        }
    }


    //Selects the customer and deletes them and any appointments they have scheduled.
    public void onActionDeleteCustomer(ActionEvent event) throws IOException{

        if (customersTableView.getSelectionModel().getSelectedItem() != null){
            Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

            for (int i = 0; i < appointments.size(); i++) {
                if (appointments.get(i).getApptIDCol() == selectedCustomer.getCustomerId()) {
                    DaoAppointments.deleteAppointment(appointments.get(i).getApptIDCol());
                }
            }

            //Hold and alert the user before deleting the selected customer.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("The selected customer will be deleted from the database.");
            alert.setContentText("Are you sure you wish to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Reloads the customer table view with the updated information
                DaoCustomers.deleteCustomer(selectedCustomer.getCustomerId());
                customersTableView.setItems(DaoCustomers.getAllCustomers());
            }

        }
        System.out.println("DELETE customer from database");

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



    */
/* TODO LIST
    MUST HAVE
    ALERT for deleting or modifying
    fix handleCountryBox populating issue

    NICE TO HAVE ITEMS
    Change labels based on country selection? What are the UK "divisions" called?
    lambda function. Alerts?
     *//*


*/
/*    public void onActionAddCustomer(ActionEvent event) throws IOException {

        System.out.printf("TESTING NEW CONTROLLER CLASS");

        //Retrieves the customer's info from the fields.
        String customerName = addCustomerName.getText();
        String customerAddress = addCustomerAddy.getText();
        String customerZip = addCustomerPostal.getText();
        String customerPhone = addCustomerPhone.getText();
        int divisionId = addCustomerDivision.getSelectionModel().getSelectedIndex() + 1;

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
            //popup confirmation confirming that a customer is about to be added.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new customer.");
            alert.setContentText("By clicking OK, you will be adding " + addCustomerName.getText() + " to the system. Are you sure you wish to continue?");
            alert.showAndWait();
        }
        DaoCustomers.newCustomer(customerName,customerAddress, customerZip, customerPhone, divisionId);
        //popup alerting the user that a customer has been added to the db. Returns to the main screen when the OK button is clicked.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setContentText(addCustomerName.getText() + " has been added.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }*//*


*/
/*    //Handles populating the country combobox in the addCustomerScreen
    public void handleCountryComboBox(ActionEvent actionEvent){
        //addCustomerCountry.getSelectionModel().clearSelection();
        //addCustomerName.clear();

        addCustomerCountry.getItems().addAll(getAllCountryNames());

        if(addCustomerCountry.getSelectionModel().getSelectedItem() != null) {
            Object selectedCountry = addCustomerCountry.getSelectionModel().getSelectedItem();
            String countryDivision = selectedCountry.toString();

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

    //
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

  *//*
*/
/*  public void handleComboBoxClosed(ActionEvent actionEvent){
        addCustomerCountry.setValue(null);
        System.out.println("Testing clear method");
    }*//*
*/
/*


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
    }*//*



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Customers
        customers = DaoCustomers.getAllCustomers();
        customersTableView.setItems(customers);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddyCol.setCellValueFactory(new PropertyValueFactory<>("customerAddy"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));

    }
}
*/
