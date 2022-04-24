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
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.awt.*;
import java.net.URL;
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


    protected LocalDate date;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected DateTimeFormatter timeFormat;

    private Appointment selectedAppointment = MainScreenController.getSelectedAppointment();

/*    public void handleUserCombobox(javafx.event.ActionEvent event){

        Object selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        String allUserNames = selectedUser.toString();

        allUserNames.equalsIgnoreCase("User_Name");
    }*/

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
        customerCombobox.setValue(selectedAppointment.getContactName());
        contactCombobox.setValue(selectedAppointment.getContactName());
       // appointmentDate.setValue(selectedAppointment.);

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

    @FXML
    void handleButton(ActionEvent event) {
        if (appointmentDate.getValue() == null || startHourCombo.getValue() == null || startMinCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Date and time not completly filled in!");
            alert.setContentText("Please use the calendar date picker to select a date AND use the Hour and Minute dropdown to set hours and minutes before clicking this button");
            alert.setGraphic(null);
            alert.showAndWait();
        } else {
            LocalDate date = appointmentDate.getValue();
            String hour = startHourCombo.getValue();
            String minute = startMinCombo.getValue();
            // obtain the LocalDateTime
            LocalDateTime ldt = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));
            // obtain the ZonedDateTime version of LocalDateTime
            ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
            // obtain the UTC ZonedDateTime of the ZonedDateTime version of LocalDateTime
            ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // make it look good in 24 hour format sortable by yyyy-MM-dd HH:mm:ss  (we are going to ignore fractions beyond seconds
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println("Locale.getDefault().toString:" + Locale.getDefault().toString()
                    + "\n\n"
                    + "ZoneOffset.systemDefault:" + ZoneOffset.systemDefault()
                    + "\n\n"
                    + "Local Date and Time:" + customFormatter.format(locZdt)
                    + "\n\n"
                    + "UTC Date and Time:" + customFormatter.format(utcZdt));

            alert.setTitle("Time and Date");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Locale.getDefault().toString:" + Locale.getDefault().toString()
                            + "\n\n"
                            + "ZoneOffset.systemDefault:" + ZoneOffset.systemDefault()
                            + "\n\n"
                            + "Local Date and Time:" + customFormatter.format(locZdt)
                            + "\n\n"
                            + "UTC Date and Time:" + customFormatter.format(utcZdt)
            );
            alert.showAndWait();
        }
    }

    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getAppointment(selectedAppointment);
        customerCombobox.setItems(customerList());
        contactCombobox.setItems(contactList());
        userComboBox.setItems(userList());

        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHourCombo.setItems(hours);
        startMinCombo.setItems(minutes);
        endHourCombo.setItems(hours);
        endMinCombo.setItems(minutes);

    }
}

