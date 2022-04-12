package Controller;

import Dao.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
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

    @FXML private ComboBox userComboBox;
    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private ComboBox customerCombobox;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private ComboBox contactCombobox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox startHour;
    @FXML private ComboBox startMinute;
    @FXML private ComboBox endHour;
    @FXML private ComboBox endMinute;


    String user = null;


    /**
     * Exits back to the main screen
     * @param event
     * @throws IOException
     */
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


    public void onActionAdd(javafx.event.ActionEvent event) throws IOException{
        System.out.println("Testing ADD");


        /**
         * Retrieves the customer's info from the fields.
         */
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        String user = userComboBox.getValue().toString();
        String customer = customerCombobox.getValue().toString();


        /**
         * Check that a name, address and phone has been entered and gives an alert if it isn't there.
         */
        if (title.isEmpty() || location.isEmpty() || description.isEmpty() || type.isEmpty() || user.isEmpty() || customer.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All fields must be filled before saving.");
            alert.showAndWait();
        }else
        {

            /**
             *Popup confirmation using a LAMBDA EXPRESSION confirming that an appointment is about to be added.
             */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new appointment.");
            alert.setContentText("By clicking OK, you will be adding an appointment to the system. Are you sure you wish to continue?");
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    DaoAppointments.newAppointment(title,location,description,type);


                    /**
                     * Confirmation that the customer has been added.
                     */
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Success!");
                    alert2.setContentText("Appointment has been added.");
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

    public void handleUserCombobox(javafx.event.ActionEvent event){

        Object selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        String allUserNames = selectedUser.toString();

        allUserNames.equalsIgnoreCase("User_Name");
    }



    /** AN observable list holding the customers from the database. Used to initialize the "Customer" combobox.
     *
     * @return customers
     */
    private ObservableList customerList(){
        ObservableList<String> customers = FXCollections.observableArrayList();

        for(Customers customer : DaoCustomers.getAllCustomers()){
            customers.add(customer.getCustomerName());
        }
        return customers;
    }

    /**
     * An observable list holding the users from the database. Used to initialize the "User" combobox.
     * @return allUserNames
     */
    private ObservableList<String> userList(){
        ObservableList<String> allUserNames = FXCollections.observableArrayList();
        for (loginUser allUsers : DaoLogin.getAllUsers()){
            String loginName;
            loginName = allUsers.getUserName();
            allUserNames.add(loginName);
        }return allUserNames;
    }

    /**
     * An observable list holding contacts from the database. Used to initialize the "Contact" combobox.
     * @return allContactNames
     */
    private ObservableList<String> contactList(){
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        for (Contacts contacts : DaoContacts.getAllContacts()){
            String contactName = contacts.getContactName();
            allContactNames.add(contactName);
        }return allContactNames;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCombobox.setItems(customerList());
        userComboBox.setItems(userList());
        contactCombobox.setItems(contactList());



    }



}
