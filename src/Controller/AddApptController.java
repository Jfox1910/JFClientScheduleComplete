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
    Appointment appt;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private ComboBox<Customers> customerCombobox;
    @FXML private ComboBox contactCombobox;
    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<LocalTime> startHourCombo;
    @FXML private ComboBox<LocalTime> endHourCombo;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm MM-dd-yyyy");

    //TESTING
    private int customerID;
    private LocalDateTime start;
    private LocalDateTime end;


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
     * Takes the inputs of date and selected hour combo and creates a LocalDateTime object.
     * Contains alerts for null fields, and 1 LAMBDA function confirming to an appointment being added, and a successful addition confirmation.
     * @param event
     * @throws IOException
     */
    public void onActionAdd(javafx.event.ActionEvent event) throws IOException, ParseException {

        int Appointment_ID = 0;
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        LocalDate date = appointmentDate.getValue();
        start = LocalDateTime.of(date, startHourCombo.getValue());
        end = LocalDateTime.of(date, endHourCombo.getValue());
        //LocalDateTime start = LocalDateTime.of(date, startHourCombo.getValue());
        //LocalDateTime end = LocalDateTime.of(date, endHourCombo.getValue());
        int customerID = getIdFromComboBox(customerCombobox);
        int User_ID = UserDao.getLoggedinUser().getUserId();
        int contactID = getIdFromComboBox(contactCombobox);
        Timestamp apptStart = Timestamp.valueOf(start);
        Timestamp apptEnd = Timestamp.valueOf(end);
        int checkContact = contactID +1;

        overlap();

        //boolean overlap = AppointmentDAO.checkForOverlap(apptStart, apptEnd, customerID, -1);

        Appointment appt = new Appointment(Appointment_ID, title, description, location, type, start, end, customerID, User_ID, contactID);

        if (titleField.getText().isEmpty() || locationField.getText().isEmpty() || descriptionField.getText().isEmpty() || typeField.getText().isEmpty() || contactCombobox.getItems().isEmpty() && checkContact <=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention!");
            alert.setContentText("All fields must be filled before saving.");
            alert.showAndWait();
        }else if (start.equals(end) || start.isAfter(end)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setContentText("Start time must be before the end time.");
            alert.showAndWait();
        }else if (overlap() == true) {
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
            alert.setContentText("By clicking OK, you will be scheduling an appointment for " + "\n"
                    + customerCombobox.getValue() + ". Are you sure you wish to continue?");
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {

                    AppointmentDAO.newAppointment(appt);

                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Success!");
                    alert2.setContentText("Appointment for " +customerCombobox.getValue() + " has been added.");
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
     * Overlap method that pulls verifies if there is an appointment overlap. It took a while to figure out how to not check against
     * the current appointment and the logic behind stepping through the times. Tried a lot of different approaches before it worked.
     * @return overlap true or false for the update method
     */
    private boolean overlap() {
        System.out.println("overlap checking");
        ObservableList<Appointment> thisCustomersAppts = AppointmentDAO.getAppointmentsByCustomer(customerID);
        boolean overlap = false;

        for (Appointment thisCustomersAppt : thisCustomersAppts) {

            Appointment thisAppt = thisCustomersAppt;
            Customers customerID = customerCombobox.getValue();
            int appointmentId = thisAppt.getAppointment_ID();
            LocalDateTime overlapStart = thisAppt.getStart();
            LocalDateTime overlapEnd = thisAppt.getEnd();

            if (start.isAfter(overlapStart.minusMinutes(0)) && start.isBefore(overlapEnd.plusMinutes(0))) {
                System.out.println("Overlap True 1 " + start + " " + overlapStart + " " + end + " " + overlapEnd);
                overlap = true;
                break;
            } else if (overlapEnd.isAfter(start.minusMinutes(0)) && overlapEnd.isBefore(end.plusMinutes(0))) {
                System.out.println("Overlap True 2 " + overlapEnd + " " + start + " " + overlapEnd + " " + end);

                overlap = true;
                break;
            } else if (overlapStart.isBefore(start.plusMinutes(0)) && overlapEnd.isAfter(end.minusMinutes(0))) {
                System.out.println("Overlap True 3");

                overlap = true;
                break;
            } else {
                System.out.println("Overlap false");

                overlap = false;
                continue;
            }
        }
        return overlap;
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
        LocalTime endHour = endHourCombo.getSelectionModel().getSelectedItem();
        LocalTime startHour = startHourCombo.getSelectionModel().getSelectedItem();

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

        startHourCombo.setItems(Utils.getStartTimeList());
        endHourCombo.setItems(Utils.getEndTimeList());
        customerCombobox.setItems(customerList());
        contactCombobox.setItems(contactList());
    }
}
