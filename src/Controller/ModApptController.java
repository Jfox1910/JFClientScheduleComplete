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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.util.ResourceBundle;


public class ModApptController implements Initializable {

    public static Customers customers;
    public static Appointment appointment;
    private final Utils utils = Utils.getInstance();

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
    @FXML private ComboBox<String> startHourCombo;
    @FXML private ComboBox<String> startMinCombo;
    @FXML private ComboBox<String> endHourCombo;
    @FXML private ComboBox<String> endMinCombo;
    @FXML private ComboBox<String> startAMPM;
    @FXML private ComboBox<String> endAMPM;

    private LocalDate date;
    private LocalDateTime start;
    private LocalDateTime end;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm MM-dd-yyyy");

    private final ObservableList<Integer> selectableHours = FXCollections.observableArrayList();
    private final ObservableList<String> selectableMinutes = FXCollections.observableArrayList();

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
        Timestamp start = startTimeStamp();
        Timestamp end = endTimeStamp();
        int customerID = Customers.getCustomerIDByName(customerCombobox.getValue().toString());
        int User_ID = userComboBox.getSelectionModel().getSelectedIndex();
        int contactID = Contacts.getContactIDByName(contactCombobox.getValue().toString());

       Appointment updateAppt = new Appointment(Appointment_ID,title,location,description,type,start,end,customerID,User_ID,contactID);

        if (titleField.getText().isEmpty() || locationField.getText().isEmpty() || descriptionField.getText().isEmpty() || typeField.getText().isEmpty() || contactCombobox.getItems().isEmpty() || userComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All fields and dropdown menus must be filled before saving.");
            alert.showAndWait();
        }else
        {
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

        appointmentDate.setValue(selectedAppointment.getStart().toLocalDateTime().toLocalDate());
        startHourCombo.setValue(String.format("%02d", selectedAppointment.getStart().toLocalDateTime().getHour()));
        startMinCombo.setValue(String.format("%02d", selectedAppointment.getStart().toLocalDateTime().getMinute()));
        endHourCombo.setValue(String.format("%02d", selectedAppointment.getStart().toLocalDateTime().getHour()));
        endMinCombo.setValue(String.format("%02d", selectedAppointment.getEnd().toLocalDateTime().getMinute()));
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

    /**
     * Prevents a previous time on the current day from being selected.
     * @param actionEvent
     */
    public void handleTimeChoice(ActionEvent actionEvent) {
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
    }


    /**
     * Sets the hour dropdownbox
     * @return
     */
    public ObservableList apptHour() {

        int[] hours = new int[]{8,9,10,11,12,13,14,15,16,17,18,19,20};
        for(Integer H : hours) {
            if(!(selectableHours.contains(H))) {
                selectableHours.add(H);
            }
        }return selectableHours;
    }


    /**
     * Sets the minute dropdownbox
     * @return selectableMinutes
     */
    public ObservableList apptMin() {

        String[] mins = new String[]{"00", "15", "30", "45", "55"};
        for(String M : mins) {
            if(!(selectableMinutes.contains(M))) {
                selectableMinutes.add(M);
            }
        }return selectableMinutes;
    }


    /**
     * Uses the end and get timestamp to parse out a datetime.
     * @param datePicker
     * @param hourPicker
     * @param minutePicker
     * @return
     * @throws ParseException
     */
    private Timestamp getTimestamp(DatePicker datePicker, ComboBox hourPicker, ComboBox minutePicker) throws ParseException {
        String selectedDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        String selectedHour = hourPicker.getValue().toString();
        String selectedMinute = minutePicker.getValue().toString();
        String selectedTime = selectedHour + ":" + selectedMinute ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time24HourFormat = dateFormat.format(dateFormat.parse(selectedTime));
        String timestamp = selectedDate + " " + time24HourFormat + ":00";

        return Timestamp.valueOf(timestamp);
    }


    /**
     * Creates a timestamp used for the start time.
     * @return getTimestamp
     * @throws ParseException
     */
    public Timestamp startTimeStamp() throws ParseException {return getTimestamp(appointmentDate, startHourCombo, startMinCombo);}


    /**
     * Creates a timestamp used for the end time
     * @return
     * @throws ParseException
     */
    public Timestamp endTimeStamp() throws ParseException {return getTimestamp(appointmentDate, endHourCombo, endMinCombo);}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startHourCombo.setItems(apptHour());
        startMinCombo.setItems(apptMin());
        endHourCombo.setItems(apptHour());
        endMinCombo.setItems(apptMin());

        getAppointment(selectedAppointment);
        customerCombobox.setItems(customerList());
        contactCombobox.setItems(contactList());
        userComboBox.setItems(userList());


    }
}

