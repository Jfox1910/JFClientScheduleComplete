package Controller;

import Dao.*;
import Model.*;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.util.ResourceBundle;

public class ModApptController implements Initializable {

    public static Customers customers;
    public static Appointment appointment;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField Appointment_ID;
    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private ComboBox<Customers> customerCombobox;
    @FXML private ComboBox<Contacts> contactCombobox;
    @FXML private ComboBox<User> userComboBox;

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

        int appointmentID = Integer.parseInt(Appointment_ID.getText());
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        LocalDate date = appointmentDate.getValue();
        LocalDateTime start = LocalDateTime.of(date, startHourCombo.getValue());
        LocalDateTime end = LocalDateTime.of(date, endHourCombo.getValue());
        Contacts contact = contactCombobox.getValue();
        Customers customer = customerCombobox.getValue();
        User user = userComboBox.getValue();

        int customerID = customerCombobox.getSelectionModel().getSelectedIndex();
        Timestamp apptStart = Timestamp.valueOf(start);
        Timestamp apptEnd = Timestamp.valueOf(end);
        int checkUser = userComboBox.getSelectionModel().getSelectedIndex() +1;

         boolean overlap = AppointmentDAO.checkForOverlap(apptStart, apptEnd, customerID, -1);

        if (titleField.getText().isEmpty() || locationField.getText().isEmpty() || descriptionField.getText().isEmpty() || typeField.getText().isEmpty() || contactCombobox.getItems().isEmpty() || checkUser <=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All fields and dropdown menus must be filled before saving.");
            alert.showAndWait();
        }else if (start.equals(end) || start.isAfter(end)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setContentText("Start time must be before the end time.");
            alert.showAndWait();
        }else if (overlap == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setContentText(customerCombobox.getValue() + "\n"
                    + "has a conflicting appointment previously scheduled." + "\n"
                    + "Please choose a different time and try again.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding a new appointment.");
            alert.setContentText("By clicking OK, you will be updating appointment # " + selectedAppointment.getAppointment_ID() + " Are you sure you wish to continue?");
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {

                    Appointment updateAppt = new Appointment(appointmentID,title,location,description,type,start,end,customer.getCustomerId(), user.getUserId(),contact.getContact_ID());
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
     */
    public void getAppointment(Appointment selectedAppointment){

        ObservableList<Contacts> contactList = ContactsDao.getContactList();
        ObservableList<Customers> customerList = CustomersDao.getCustomerList();

        Appointment_ID.setText(String.valueOf(selectedAppointment.getAppointment_ID()));
        titleField.setText(String.valueOf(selectedAppointment.getTitle()));
        locationField.setText(String.valueOf(selectedAppointment.getLocation()));
        descriptionField.setText(String.valueOf(selectedAppointment.getDescription()));
        typeField.setText(String.valueOf(selectedAppointment.getType()));

        Contacts selectedContact = null;
        for(Contacts contact : contactList){
            if(contact.getContactID() == selectedAppointment.getContact_ID()){
                selectedContact = contact;
                break;
            }
        }

        Customers customer = null;
        for(Customers selectedCustomer : customerList){
            if(selectedCustomer.getCustomerId() == selectedAppointment.getCustomer_ID()){
                customer = selectedCustomer;
                break;
            }
        }

        customerCombobox.setValue(customer);
        contactCombobox.setValue(selectedContact);
        appointmentDate.setValue(selectedAppointment.getStart().toLocalDate());
        startHourCombo.setValue(LocalTime.from(selectedAppointment.getStart()));
        endHourCombo.setValue(LocalTime.from(selectedAppointment.getEnd()));
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        getAppointment(selectedAppointment);
        startHourCombo.setItems(Utils.getStartTimeList());
        endHourCombo.setItems(Utils.getEndTimeList());
        customerCombobox.setItems(CustomersDao.getAllCustomers());
        contactCombobox.setItems(ContactsDao.getAllContacts());
        userComboBox.setItems(UserDao.getAllUsers());

    }
}

