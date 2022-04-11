package Controller;

import Dao.DaoContacts;
import Dao.DaoCountries;
import Dao.DaoCustomers;
import Dao.DaoLogin;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddApptController implements Initializable {
    public static Customers customers;
    public static Appointments appointments;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField titleField;
    @FXML private TextField locationTextfield;
    @FXML private ComboBox customerCombobox;
    @FXML private ComboBox descriptionTextfield;
    @FXML private ComboBox typeCombobox;
    @FXML private ComboBox contactCombobox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox startHour;
    @FXML private ComboBox startMinute;
    @FXML private ComboBox endHour;
    @FXML private ComboBox endMinute;



    //Exit to the main screen
    public void onActionMainScreen(javafx.event.ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All unsaved information will be lost. Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/mainScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Pulls all customers from the customers table and places them in an OL that is initialized when the add appointment screen
     * is loaded
     * @return customers
     */
    public ObservableList customerList(){
        ObservableList<String> customers = FXCollections.observableArrayList();

        for(Customers customer : DaoCustomers.getAllCustomers()){
            customers.add(customer.getCustomerName());
        }
        return customers;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCombobox.setItems(customerList());

        ObservableList<String> contactDropdown = FXCollections.observableArrayList();
        ObservableList<String> customerDropdown = FXCollections.observableArrayList();
        ObservableList<String> loginUser = FXCollections.observableArrayList();


    }



}
