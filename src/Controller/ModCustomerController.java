package Controller;

import Dao.CountriesDao;
import static Dao.CountriesDao.getAllCountries;
import static Dao.DivisionsDao.getAllDivisions;
import static Dao.DivisionsDao.getCountryDivision;
import Dao.CustomersDao;
import Dao.DivisionsDao;
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
import java.util.ResourceBundle;


/**
 * The Modify Customer Controller Class
 */
public class ModCustomerController implements Initializable{

    @FXML private TextField CustomerID;
    @FXML private TextField CustomerName;
    @FXML private TextField CustomerAddress;
    @FXML private TextField CustomerZip;
    @FXML private TextField CustomerPhone;
    @FXML private ComboBox <Countries> customerCountry;
    @FXML private ComboBox <Divisions> customerDivision;

    private Stage stage;
    private Scene scene;
    private Parent root;
    int retrieveDivisionID = 0;

    private Customers customers = MainScreenController.getSelectedCustomer();


    /**
     * Loads the modify customer screen fields with the information of the selected customer.
     * @param customers
     */
    public void getCustomer(Customers customers){

        int thisCustomerDivision = customers.getCustomerDivision();
        int thisCustomerCountry = customers.getCustomerCountry();
        CustomerID.setText(String.valueOf(customers.getCustomerId()));
        CustomerName.setText(String.valueOf(customers.getCustomerName()));
        CustomerAddress.setText(String.valueOf(customers.getCustomerAddy()));
        CustomerPhone.setText(String.valueOf(customers.getCustomerPhone()));
        CustomerZip.setText(String.valueOf(customers.getCustomerZip()));
        System.out.println(thisCustomerCountry);


        customerCountry.getSelectionModel().select(thisCustomerCountry);
        System.out.println(thisCustomerCountry);

        handleCountryComboBox(null);
        System.out.println(thisCustomerCountry);
        customerDivision.setValue(Divisions.divisionByCountry(thisCustomerDivision));
        System.out.println(thisCustomerCountry);

        //customerCountry.setValue(Countries.countryByDivision(thisCustomerCountry));

        //customerCountry.setValue(Countries.countryByDivision(thisCustomerCountry));

        //customerCountry.getSelectionModel().select(thisCustomerCountry);

        //customerDivision.getSelectionModel().select(thisCustomerDivision);




    }


    /**
     * Exits back to the main screen. Alerts the user first. LAMBDA function that eliminates a bit of code.
     * @param event
     * @throws IOException
     */
    public void onActionMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All unsaved information will be lost. Are you sure you wish to continue?");
        alert.showAndWait().ifPresent((response -> {
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
        }));
    }


    /**
     * Collects the information from the fields and updates the customer in the database. Checks for and alerts the user if the fields aren't filled and when a customer is being updated.
     * Additional alert for a successful update. LAMBDA function that eliminates a bit of code.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionUpdateCustomer(ActionEvent actionEvent) throws IOException {

        int customerId = Integer.parseInt(CustomerID.getText());
        String customerName = CustomerName.getText();
        String customerAddress = CustomerAddress.getText();
        String customerZip = CustomerZip.getText();
        String customerPhone = CustomerPhone.getText();
        Divisions getCustomerDivision = customerDivision.getSelectionModel().getSelectedItem();
        int modCustomerDivision = getCustomerDivision.getDivisionID();
        //int modCustomerDivision = DivisionsDao.getAllDivisionsByName(customerDivision.getValue().toString());
        //Used to verify a division has been selected
        int checkDivision = customerDivision.getSelectionModel().getSelectedIndex() +1;

if (customerName.isEmpty() || customerAddress.isEmpty() || customerZip.isEmpty() || customerPhone.isEmpty() || customerCountry.getItems().isEmpty() || checkDivision <=0){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Attention!");
    alert.setContentText("All customer fields and comboboxes must be filled before saving.");
    alert.showAndWait();
    }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Updating an existing customer.");
            alert.setContentText("By clicking OK, you will be updating " + CustomerName.getText() + "'s information. Are you sure you wish to continue?");

            alert.showAndWait().ifPresent((response -> {
                CustomersDao.modifyCustomer(customerId, customerName, customerAddress, customerZip, customerPhone, modCustomerDivision);
                alert.setTitle("Success!");
                alert.setContentText(CustomerName.getText() + " has been updated.");
                alert.showAndWait();

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
            }));
        }
    }


    /**
     * Handles populating the COUNTRY combobox
     */
  /*  public void handleCountryComboBox(){

        String selectedCustomerCountry = customerCountry.getValue().toString();
        customerDivision.setItems(divisionsByCountry(selectedCustomerCountry));
    }*/

    public void handleCountryComboBox(ActionEvent actionEvent){
        Countries countries = customerCountry.getSelectionModel().getSelectedItem();
        int countryID = countries.getCountryID();
        customerDivision.setValue(null);
        customerDivision.setItems(getCountryDivision(countryID));

    }


    /**
     * An observable list holding the divisions by country from the database.
     * @return divisions
     */
    public ObservableList divisionsByCountry(String modCustomerCountry){

        ObservableList<String> divisionByCountry = FXCollections.observableArrayList();
        for(String divisionName : DivisionsDao.getAllByCountry(modCustomerCountry)){
            divisionByCountry.add(divisionName);
        }
        return divisionByCountry;
    }


    /**
     * Handler for the division combobox.
     * @param actionEvent
     * @return retrieveDivisionID
     */
    public int handleDivisionComboBox(ActionEvent actionEvent){
        if(customerDivision.getSelectionModel().getSelectedItem() != null) {

            Object selectedDivision = customerDivision.getSelectionModel().getSelectedItem();

            String division = selectedDivision.toString();
            for (int i = 0; i < DivisionsDao.getAllDivisions().size(); i++) {
                if (division.equalsIgnoreCase(DivisionsDao.getAllDivisions().get(i).getDivisionName())) {
                    retrieveDivisionID = DivisionsDao.getAllDivisions().get(i).getDivisionID();
                    break;
                }
            }
        }
        return retrieveDivisionID;
    }


    /**
     * An observable list holding the divisions from the database.
     * @return divisions
     */
    public ObservableList divisionList(){

        ObservableList<String> divisions = FXCollections.observableArrayList();

        if (divisions != null) {
            for (Divisions division : DivisionsDao.getAllDivisions()) {
                divisions.add(division.getDivisionName());
            }
        }
        return divisions;
    }


    /**
     * An observable list holding the countries from the database. Used to initialize the "Country" combobox.
     * @return countries
     */
    public ObservableList countryList(){

        ObservableList<String> countries = FXCollections.observableArrayList();
        for(Countries country : CountriesDao.getAllCountries()){
            countries.add(country.getName());
        }
        return countries;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerDivision.setItems(getAllDivisions());
        customerCountry.setItems(getAllCountries());
        getCustomer(customers);
        System.out.println(customers.getCustomerCountryName());


        //customerDivision.setItems(divisionList());
        //customerCountry.setItems(countryList());

        //divisionList();


    }
}
