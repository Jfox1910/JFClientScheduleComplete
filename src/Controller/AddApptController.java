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


/**
 * Add appointment class.
 */
public class AddApptController implements Initializable {

    public static Customers customers;
    public static Appointment appointment;
    private final Utils utils = Utils.getInstance();
    Appointment appt;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private ComboBox customerCombobox;
    @FXML private ComboBox contactCombobox;

    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<String> startHourCombo;
    @FXML private ComboBox<String> startMinCombo;
    @FXML private ComboBox<String> endHourCombo;
    @FXML private ComboBox<String> endMinCombo;

    private LocalDate date;
    private LocalDateTime start;
    private LocalDateTime end;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm MM-dd-yyyy");

    private final ObservableList<Integer> selectableHours = FXCollections.observableArrayList();
    private final ObservableList<String> selectableMinutes = FXCollections.observableArrayList();


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


    /**
     * new Appointment handler. Collects the information from the fields and adds them to the DB.
     * @param event
     * @throws IOException
     */
    public void onActionAdd(javafx.event.ActionEvent event) throws IOException, ParseException {


        int Appointment_ID = 0;
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        Timestamp start = startTimeStamp();
        Timestamp end = endTimeStamp();
        int customerID = getIdFromComboBox(customerCombobox);
        int User_ID = UserDao.getLoggedinUser().getUserId();
        int contactID = getIdFromComboBox(contactCombobox);

        Appointment appt = new Appointment(Appointment_ID, title, description, location, type, start, end, customerID, User_ID, contactID);


        /**
         * Check that a name, address and phone has been entered and gives an alert if it isn't there.
         */
        if (titleField.getText().isEmpty() || locationField.getText().isEmpty() || descriptionField.getText().isEmpty() || typeField.getText().isEmpty() || contactCombobox.getItems().isEmpty()){
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

                    AppointmentDAO.newAppointment(appt);

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


    /**
     * Calls the getID to string method from Utils.
     * @param comboBox
     * @return
     */
    private int getIdFromComboBox(ComboBox comboBox) {
        return Utils.getIdFromComboString((String) comboBox.getSelectionModel().getSelectedItem());
    }


    /**
     * An observable list holding the customers from the database. Used to initialize the "Customer" combobox.
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


    public Timestamp startTimeStamp() throws ParseException {return getTimestamp(appointmentDate, startHourCombo, startMinCombo);}
    public Timestamp endTimeStamp() throws ParseException {return getTimestamp(appointmentDate, endHourCombo, endMinCombo);}


    private Timestamp getTimestamp(DatePicker datePicker, ComboBox hourPicker, ComboBox minutePicker) throws ParseException {
        String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        String hour = hourPicker.getValue().toString();
        String min = minutePicker.getValue().toString();
        String time = hour + ":" + min ;
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        String time24HourFormat = date24Format.format(date24Format.parse(time));
        String concatTimeStamp = date + " " + time24HourFormat + ":00";

        return Timestamp.valueOf(concatTimeStamp);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startHourCombo.setItems(apptHour());
        startMinCombo.setItems(apptMin());
        endHourCombo.setItems(apptHour());
        endMinCombo.setItems(apptMin());

        customerCombobox.setItems(customerList());
        contactCombobox.setItems(contactList());
    }
}
