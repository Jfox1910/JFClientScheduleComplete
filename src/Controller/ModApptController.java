package Controller;

import Dao.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.util.ResourceBundle;


public class ModApptController implements Initializable {

    public static Customers customers;
    public static Appointment appointment;

   // private final Utils utils = Utils.getInstance();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField Appointment_ID;
    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private ComboBox customerCombobox;
    @FXML private ComboBox contactCombobox;
    @FXML private ComboBox userComboBox;

    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<LocalTime> startHourCombo;
    @FXML private ComboBox<LocalTime> endHourCombo;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm MM-dd-yyyy");


    private Appointment selectedAppointment = MainScreenController.getSelectedAppointment();


    /**
     * Collects the information from the fields and updates the appointment in the database. Checks for and alerts the user if the fields aren't filled and when a customer is being updated.
     * Additional alert for a successful update. LAMBDA function that eliminates a bit of code.
     * @param event
     * @throws IOException
     * @throws ParseException
     */
    public void onActionUpdate(javafx.event.ActionEvent event) throws IOException, ParseException {

        int Appointment_ID = selectedAppointment.getAppointment_ID();
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        LocalDate date = appointmentDate.getValue();
        LocalDateTime start = LocalDateTime.of(date, startHourCombo.getValue());
        LocalDateTime end = LocalDateTime.of(date, endHourCombo.getValue());
        int customerID = Customers.getCustomerIDByName(customerCombobox.getValue().toString());
        int User_ID = userComboBox.getSelectionModel().getSelectedIndex();
        int contactID = Contacts.getContactIDByName(contactCombobox.getValue().toString());

       Appointment updateAppt = new Appointment(Appointment_ID,title,location,description,type,start,end,customerID,User_ID,contactID);

        if (titleField.getText().isEmpty() || locationField.getText().isEmpty() || descriptionField.getText().isEmpty() || typeField.getText().isEmpty() || contactCombobox.getItems().isEmpty() || userComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All fields and dropdown menus must be filled before saving.");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new appointment.");
            alert.setContentText("By clicking OK, you will be updating the appointment. Are you sure you wish to continue?");
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {

                    AppointmentDAO.updateAppointment(updateAppt);

                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Success!");
                    alert2.setContentText("Appointment has been updated.");
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
     * Handles exiting back to the main screen.
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



    /**
     * Loads the modify appointment screen fields with the information of the selected appointment.
     * @param
     */
    public void getAppointment(Appointment selectedAppointment){

        Appointment_ID.setText(String.valueOf(selectedAppointment.getAppointment_ID()));
        titleField.setText(String.valueOf(selectedAppointment.getTitle()));
        locationField.setText(String.valueOf(selectedAppointment.getLocation()));
        descriptionField.setText(String.valueOf(selectedAppointment.getDescription()));
        typeField.setText(String.valueOf(selectedAppointment.getType()));
        customerCombobox.setValue(CustomersDao.setCustomerName(selectedAppointment.getCustomer_ID()));
        contactCombobox.setValue(ContactsDao.setContactName(selectedAppointment.getContact_ID()));
        appointmentDate.setValue(selectedAppointment.getStart().toLocalDate());
        startHourCombo.setValue(LocalTime.from(selectedAppointment.getStart()));
        endHourCombo.setValue(LocalTime.from(selectedAppointment.getEnd()));
    }


    /**
     * An observable list holding the users from the database. Used to initialize the "User" combobox.
     * @return allUserNames
     */
    private ObservableList<String> userList(){
        ObservableList<String> allUserNames = FXCollections.observableArrayList();
        for (User allUsers : UserDao.getAllUsers()){
            String loginName;
            loginName = allUsers.getUserName();
            allUserNames.add(loginName);
        }return allUserNames;
    }


    /**
     * An observable list holding customers from the database. Used to initialize the "Customer" combobox.
     * @return customers
     */
    private ObservableList customerList(){
        ObservableList<String> customers = FXCollections.observableArrayList();
        for(Customers customer : CustomersDao.getAllCustomers()){
            customers.add(String.valueOf(customer.getCustomerId()) + " : " + customer.getCustomerName());
        }
        return customers;
    }


    /**
     * An observable list holding contacts from the database. Used to initialize the "Contact" combobox.
     * @return allContactNames
     */
    private ObservableList<String> contactList(){
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        for (Contacts contacts : ContactsDao.getAllContacts()){
            allContactNames.add(String.valueOf(contacts.getContactID() + " : " + contacts.getContactName()));
        }return allContactNames;
    }


    /**
     * Prevents a previous date from being selected for an appointment. Throws an alert and resets the datepicker.
     * @param actionEvent
     */
    public void handleDateChoice(ActionEvent actionEvent) {

        long days = ChronoUnit.DAYS.between(LocalDate.now(), appointmentDate.getValue());
        if (days < 0){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("ERROR!");
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.setContentText("A date on or after " + LocalDate.now() + " must be selected.");
            alert.showAndWait();
            appointmentDate.getEditor().clear();
        }
    }

/*    *//**
     * Prevents a previous time on the current day from being selected.
     * @param
     */
/*    public void handleTimeChoice(ActionEvent actionEvent) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), appointmentDate.getValue());
        if (days == 0){
            long timer = ChronoUnit.MINUTES.between(LocalTime.now(), LocalTime.parse(startHourCombo.getValue()));
            if (timer < 60){
                Alert eAlert = new Alert(Alert.AlertType.NONE);
                eAlert.setTitle("ERROR!");
                eAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                eAlert.setContentText("You are selecting a time in the past. A time on or after " + LocalTime.now().plusMinutes(60).format(DateTimeFormatter.ofPattern("HH:mm")) + " must be selected.");
                eAlert.showAndWait();
                appointmentDate.getEditor().clear();
            }
        }
    }*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startHourCombo.setItems(Utils.getStartTimeList());

        endHourCombo.setItems(Utils.getEndTimeList());

        getAppointment(selectedAppointment);
        customerCombobox.setItems(customerList());
        contactCombobox.setItems(contactList());
        userComboBox.setItems(userList());


    }
}

