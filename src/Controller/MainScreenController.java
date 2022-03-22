package Controller;

import Dao.DaoAppointments;
import Dao.DaoCountries;
import Dao.DaoCustomers;
import Dao.DaoDivisions;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import Controller.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML private TableView<Appointments> apptTableview;
    @FXML private TableColumn<Appointments, Integer> apptIDCol;
    @FXML private TableColumn<Appointments, String> apptTitleCol;
    @FXML private TableColumn<Appointments, String> apptDescriptionCol;
    @FXML private TableColumn<Appointments, String> apptLocationCol;
    @FXML private TableColumn<Appointments, String> apptContactCol;
    @FXML private TableColumn<Appointments, String> apptTypeCol;
    @FXML private TableColumn<Appointments, LocalDateTime> apptStartTimeCol;
    @FXML private TableColumn<Appointments, LocalDateTime> apptEndTimeCol;
    @FXML private TableColumn<Appointments, Integer> apptCustomerIDCol;
    @FXML private TableColumn<Appointments, Integer> apptUserIDCol;

    @FXML private TableView<Customers> customersTableView;
    @FXML private TableColumn<Customers, Integer> customerIdCol;
    @FXML private TableColumn<Customers, String> customerNameCol;
    @FXML private TableColumn<Customers, String> customerAddyCol;
    @FXML private TableColumn<Customers, String> customerZipCol;
    @FXML private TableColumn<Countries, String> customerPhoneCol;
    @FXML private TableColumn<Customers, String> customerCreatedDateCol;
    @FXML private TableColumn<Customers,String> customerCreatedCol;
    @FXML private TableColumn<Customers, Timestamp> customerUpdatedOnCol;
    @FXML private TableColumn<Customers, String> customerUpdatedByCol;
    @FXML private TableColumn<Customers, Integer> customerDivisionCol;

    @FXML private TextField addCustomerID;
    @FXML private TextField CustomerName;
    @FXML private TextField CustomerAddress;
    @FXML private TextField CustomerZip;
    @FXML private TextField CustomerPhone;
    @FXML private ComboBox customerCountry;
    @FXML private ComboBox customerDivision;

    Customers modifyCustomers;
    int retrieveDivisionID = 0;
    int CustomerId;
    String loggedInUser;
    private boolean newCustomer = true;

    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    private ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();
    public ObservableList<Appointments> allAppointments = DaoAppointments.getAllAppointments();

    private ObservableList<Customers> customers;
    private ObservableList<Appointments> appointments;

    public static Customers customer;


    /*

    TODO LIST
    Division to string on modify box
    cleanup functionality and move into customerController class?
    Add appointments
     */

    //----ALL APPOINTMENT TAB METHODS----

    //Add Appointments Method
    public void onActionAddAppt(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addApptScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Modify Appointments Method
    public void onActionModAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("modApptScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Cancel an appointment method
    public void onActionCancelAppt(ActionEvent event) throws IOException {

        //NEEDS TO BE IMPLEMENTED

        System.out.println("Test CANCEL Appointments Button.");

    }

    //Load Reports Method
    public void onActionLoadReports(ActionEvent event) throws IOException {

        //DECIDE ON WHAT TO REPORT

        System.out.println("Test LOAD Reports Button.");
    }


    //----ALL CUSTOMER TAB METHODS----

    //Add a customer method (Contained within the customer tab)
    public void onActionSaveCustomer
    (ActionEvent event) throws IOException {

        //CustomerController.saveCustomer();

        //Retrieves the customer's info from the fields.
        String customerName = CustomerName.getText();
        String customerAddress = CustomerAddress.getText();
        String customerZip = CustomerZip.getText();
        String customerPhone = CustomerPhone.getText();
        int divisionId = customerDivision.getSelectionModel().getSelectedIndex() + 1;

        //Check that a name, address and phone has been entered and gives an alert if it isn't there.
        if (customerName.isEmpty() ||customerAddress.isEmpty() || customerPhone.isEmpty() || customerZip.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All customer fields must be filled before saving.");
            alert.showAndWait();
            return;
        }if (newCustomer = true) {
            //popup confirmation confirming that a customer is about to be added.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new customer.");
            alert.setContentText("By clicking OK, you will be adding " + CustomerName.getText() + " to the system. Are you sure you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DaoCustomers.newCustomer(customerName, customerAddress, customerZip, customerPhone, divisionId);
                //Confirmation that the customer has been added.
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Success!");
                alert2.setContentText(CustomerName.getText() + " has been added.");
                Optional<ButtonType> result2 = alert2.showAndWait();
                //Reloads the customer table view with the updated information
                if (result2.get() == ButtonType.OK) {
                    customersTableView.setItems(DaoCustomers.getAllCustomers());
                }
            }
        }else if (newCustomer = false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new customer.");
            alert.setContentText("By clicking OK, you will be updating" + CustomerName.getText() + "'s . Are you sure you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Testing modify customer");
                DaoCustomers.modifyCustomer(customerName, customerAddress, customerZip, customerPhone, loggedInUser, divisionId, CustomerId);
            }
        }
    }

    //Modifies an existing customer. Throws an error if a name isn't selected, otherwise modifies the customer.
    public void onActionModifyCustomer(ActionEvent event) throws IOException {
        if (customersTableView.getSelectionModel().getSelectedItem() != null){

            newCustomer = false;
            int divID = customerDivision.getSelectionModel().getSelectedIndex() +1;
            modifyCustomers = customersTableView.getSelectionModel().getSelectedItem();


            CustomerId = modifyCustomers.getCustomerId();
            addCustomerID.setText(String.valueOf(modifyCustomers.getCustomerId()));
            CustomerName.setText(String.valueOf(modifyCustomers.getCustomerName()));
            CustomerAddress.setText(String.valueOf(modifyCustomers.getCustomerAddy()));
            CustomerPhone.setText(String.valueOf(modifyCustomers.getCustomerPhone()));
            CustomerZip.setText(String.valueOf(modifyCustomers.getCustomerZip()));
            customerCountry.setValue(modifyCustomers.getCustomerDivision());
            customerDivision.setValue(modifyCustomers.getCustomerDivision());

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
                alert.setHeaderText("The selected customer will be deleted from the database!");
                alert.setContentText("This action cannot be undone. Are you sure you wish to continue?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //Reloads the customer table view with the updated information
                    DaoCustomers.deleteCustomer(selectedCustomer.getCustomerId());
                    customersTableView.setItems(DaoCustomers.getAllCustomers());

                    //Confirmation that the customer has been deleted.
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("SUCCESS!");
                    alert2.setContentText("The customer has been deleted from the system.");
                    alert2.showAndWait();
                }

        }
    }

    public void onActionCancel(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ATTENTION!");
        alert.setHeaderText("Cancel customer update.");
        alert.setContentText("This will cancel the customer update. Are you sure you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //CustomerId.setText("");
            CustomerName.clear();
            CustomerAddress.clear();
            CustomerZip.clear();
            CustomerPhone.clear();
            customerCountry.getItems().clear();
            customerCountry.getItems().addAll(getAllCountryNames());
            customerDivision.getItems().clear();
            customersTableView.setItems(DaoCustomers.getAllCustomers());
        }


    }

    //Handles populating the country and division comboboxes
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

    //US division selections
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

    //Canadian division selections
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

    //UK division selections
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


    @Override
    public void initialize (URL location, ResourceBundle resources){

        //Initializes the customer/country combobox
        customerCountry.getItems().addAll(getAllCountryNames());


        //Appointments
        appointments = DaoAppointments.getAllAppointments();
        apptTableview.setItems(appointments);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptIDCol"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitleCol"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescriptionCol"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocationCol"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("apptContactCol"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptTypeCol"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptStartTimeCol"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptEndTimeCol"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerIDCol"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("apptUserIDCol"));

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
