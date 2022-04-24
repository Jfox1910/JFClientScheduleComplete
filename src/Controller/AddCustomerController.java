package Controller;

import Dao.*;
import Model.*;
import Model.User;
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

    @FXML private TextField CustomerName;
    @FXML private TextField CustomerAddress;
    @FXML private TextField CustomerZip;
    @FXML private TextField CustomerPhone;
    @FXML private ComboBox customerCountry;
    @FXML private ComboBox customerDivision;
    User loginuser;

    int retrieveDivisionID = 0;
    int retrieveUserID = 0;

    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Add a customer method (Contained within the customer tab) has a LAMBDA alert for customer added and null fields.
     * @param event
     * @throws IOException
     */
    public void onActionSaveCustomer(ActionEvent event) throws IOException {

    /**
    * Retrieves the customer's info from the fields.
    */
        String customerName = CustomerName.getText();
        String customerAddress = CustomerAddress.getText();
        String customerZip = CustomerZip.getText();
        String customerPhone = CustomerPhone.getText();
        int divisionID = retrieveDivisionID;
        int userID = retrieveUserID;
        //Used to verify a division has been selected
        int checkDivision = customerDivision.getSelectionModel().getSelectedIndex() +1;

        if (customerName.isEmpty() || customerAddress.isEmpty() || customerPhone.isEmpty() || customerZip.isEmpty() || customerCountry.getItems().isEmpty() || checkDivision <=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All customer fields must be filled before saving.");
            alert.showAndWait();
        }else
        {

    /**
    *Popup confirmation using a LAMBDA EXPRESSION confirming that a customer is about to be added.
    * The use of the lambda expression simplified the method and minimized the amount of code.
    */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new customer.");
            alert.setContentText("By clicking OK, you will be adding " + CustomerName.getText() + " to the system. Are you sure you wish to continue?");
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    CustomersDao.newCustomer(userID, customerName, customerAddress, customerZip, customerPhone, divisionID);

    /**
    * Confirmation that the customer has been added.
    */
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


    /**
     * Handles populating the country and division comboboxes
     * @param actionEvent
     */
    public void handleCountryComboBox(ActionEvent actionEvent){
        String selectedCustomerCountry = customerCountry.getValue().toString();
        customerDivision.setItems(divisionsByCountry(selectedCustomerCountry));

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
     * Exits to the main screen. Uses a LAMBDA expression.
     * The use of the lambda expression simplified the method and minimized the amount of code.
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


    /**
     * An observable list holding the divisions from the database. Used to initialize the "Division" combobox.
     * @return divisions
     */
    public ObservableList divisionsByCountry(String modCustomerCountry){
        ObservableList<String> divisions = FXCollections.observableArrayList();

        for(String divisionName : DivisionsDao.getAllByCountry(modCustomerCountry)){
            divisions.add(divisionName);
        }
        return divisions;
    }


    /**
     * Initializes the country combobox which then sets the division combobox
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerCountry.setItems(countryList());


    }
}
