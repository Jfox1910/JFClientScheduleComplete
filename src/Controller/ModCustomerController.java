package Controller;

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

    public void onActionSelectCountry(ActionEvent event) throws IOException{
        System.out.println("TESTING COUNTRY SELECTION.");
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
        //Customers = DaoCustomers.getAllCustomers();
        //customersTableView.setItems(customers);
       /* customerName.setText(String.valueOf(modifyCustomer.getCustomerNameCol()));
        customerId.setText(String.valueOf(modifyCustomer.getCustomerIdCol()));
        customerAddress.setText(String.valueOf(modifyCustomer.getCustomerAddyCol()));
        customerZip.setText(String.valueOf(modifyCustomer.getCustomerZipCol()));
        customerPhone.setText(String.valueOf(modifyCustomer.getCustomerPhoneCol()));*/
        //divID.getSelectionModel().select(Customers.getCustomerDivisionCol);
        //customerCountry.getSelectionModel().select(Customers.getCustomerDivisionCol);
    }
}
