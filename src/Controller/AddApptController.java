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

public class AddApptController implements Initializable {

    //private final ObservableList<Integer> hourInput = FXCollections.observableArrayList();
   // private final ObservableList<String> minuteInput = FXCollections.observableArrayList();
    //private final ObservableList<String> times = FXCollections.observableArrayList();
    //private final ObservableList<Integer> length = FXCollections.observableArrayList(IntStream.range(15, 91).boxed().collect(Collectors.toList()));


    public static Customers customers;
    public static Appointment appointment;
    private final Utils utils = Utils.getInstance();

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
    @FXML private ComboBox<String> startAMPM;
    @FXML private ComboBox<String> endAMPM;


    protected LocalDate date;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");


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

    protected void setDate() {
        date = appointmentDate.getValue();
    }

    protected void setStartDateTime() {start = getLocalDateTime(startHourCombo, startMinCombo, startAMPM);}

    protected void setEndDateTime() {end = getLocalDateTime(endHourCombo, endMinCombo, endAMPM);}

    public LocalDateTime getStartDateTime() {
        return start;
    }

    public LocalDateTime getEndDateTime() {
        return end;
    }



    private LocalDateTime getLocalDateTime(ComboBox<String> hourCombo, ComboBox<String>minuteCombo,ComboBox<String> amPmCombo) {
        String timeString = hourCombo.getSelectionModel().getSelectedItem() + ":" + minuteCombo.getSelectionModel().getSelectedItem();
        LocalTime time = LocalTime.parse(timeString, timeFormat);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        if(amPmCombo.getSelectionModel().getSelectedItem().equals("PM")) {
            return dateTime.plusHours(12);
        }
        return dateTime;
    }

    private void timeComboBoxes() {
        ArrayList<String> hours = new ArrayList<>();
        ArrayList<String> minutes = new ArrayList<>();
        ArrayList<String> amPm = new ArrayList<>();
        minutes.add("00");
        for(int i = 5; i < 60; i+=5) {
            int hour = i/5;
            if (hour <= 10) {
                hours.add("0" + hour);
            } else {
                hours.add(Integer.toString(hour));
            }
            if (i == 15) {
                minutes.add("15");
            } else {
                minutes.add(Integer.toString(i));
            }
        }
        hours.add("12");
        amPm.add("AM");
        amPm.add("PM");
        startHourCombo.getItems().addAll(hours);
        startMinCombo.getItems().addAll(minutes);
        endHourCombo.getItems().addAll(hours);
        endMinCombo.getItems().addAll(minutes);
        startAMPM.getItems().addAll(amPm);
        endAMPM.getItems().addAll(amPm);

        /*startHourCombo.getItems().addAll(hours);
        startMinCombo.getItems().addAll(minutes);

        endHourCombo.getItems().addAll(hours);
        endMinCombo.getItems().addAll(minutes);
        */
    }


    protected int getLocalOffsetFromEst() {
        ZoneId systemZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");
        Instant now = Instant.now();
        ZoneOffset offsetForSystemZone = systemZone.getRules().getOffset(now);
        ZoneOffset estOffset = estZone.getRules().getOffset(now);
        return estOffset.compareTo(offsetForSystemZone);
    }

    //TODO TESTING

    private static Map<String, String> zoneIdToOffsetMap() {
        Map<String, String> offSet = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();

        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId id = ZoneId.of(zoneId);
            // LocalDateTime -> ZonedDateTime
            ZonedDateTime zonedDateTime = localDateTime.atZone(id);
            // ZonedDateTime -> ZoneOffset
            ZoneOffset zoneOffset = zonedDateTime.getOffset();
            //replace Z to +00:00
            String offset = zoneOffset.getId().replaceAll("Z", "+00:00");

            offSet.put(id.toString(), offset);
        }
        return offSet;
    }


    public void onActionAdd(javafx.event.ActionEvent event) throws IOException{

        //Appointment appt;
            int Appointment_ID = 0;
            String title = titleField.getText();
            String location = locationField.getText();
            String description = descriptionField.getText();
            String type = typeField.getText();
           // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           // String appointmentDate = this.appointmentDate.getValue().format(dtf);
           // String startHour = startHourCombo.getValue().toString();
           // String startMinute=startMinCombo.getValue().toString();

            setDate();
            setStartDateTime();
            setEndDateTime();

            int customerID = getIdFromComboBox(customerCombobox);
            int User_ID = UserDao.getLoggedinUser().getUserId();
            int contactID = getIdFromComboBox(contactCombobox);

           //return new Appointment (titleField.getText(), locationField.getText(), descriptionField.getText(), typeField.getText(), start, end, customerID, User_ID, contactID);
           Appointment appt = new Appointment(Appointment_ID, title, description, location, type, start, end, customerID, User_ID, contactID);


        System.out.println(titleField.getText() + locationField.getText()+ descriptionField.getText()+ typeField.getText()+ start+ end+ customerID+ User_ID+ contactID);
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

                  // AppointmentDAO.newAppointment(appt/*, start, end*/);
                    //AppointmentDAO.newAppointment(appt, startDateTime, endDateTime);

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

    /**
     * Calls the getID to string method from Utils.
     * @param comboBox
     * @return
     */
    private int getIdFromComboBox(ComboBox comboBox) {
        return Utils.getIdFromComboString((String) comboBox.getSelectionModel().getSelectedItem());
    }


   /* public Timestamp startTime() throws ParseException {
        return getTimestamp(appointmentDate, startTimeCombo, startMinCombo, am_pmStart);
    }


    public Timestamp endTime() throws ParseException {
        return getTimestamp(appointmentDate, endTimeCombo, endMinCombo, am_pmEnd);
    }


    private Timestamp getTimestamp(DatePicker datePicker, ComboBox hourPicker, ComboBox minutePicker, ToggleGroup am_pm) throws ParseException {
        String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        String hour = hourPicker.getValue().toString();
        String min = minutePicker.getValue().toString();

        ToggleButton selectedToggleButton = (ToggleButton) am_pm.getSelectedToggle();
        String am_pmValue = selectedToggleButton.getText();

        String time = hour + ":" + min + " " + am_pmValue;
        //String time = hour + ":" + min;

        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        String time24HourFormat = date24Format.format(date12Format.parse(time));

        String concatTime = date + " " + time24HourFormat + ":00";

        return Timestamp.valueOf(concatTime);
    }*/



/*    *//**
     * Creates and returns an array of HOUR options.
     * @return hourInput
     *//*
    public ObservableList apptHour() {
        int[] hours = new int[]{12,11,10,9,8,7,6,5,4,3,2,1};
        for(Integer H : hours)
        {
            if(!(hourInput.contains(H)))
            {
                hourInput.add(H);
            }
        }
            return hourInput;
    }

    *//**
     * Creates and returns an array of MINUTE options.
     * @return minuteInput
     *//*
    public ObservableList apptMin() {
        String[] minutes = new String[]{"00","15","30","45","60"};
        for(String Minutes : minutes)
        {
            if(!(minuteInput.contains(Minutes)))
            {
                minuteInput.add(Minutes);
            }
        }
        return minuteInput;
    }*/


    /** AN observable list holding the customers from the database. Used to initialize the "Customer" combobox.
     *
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
     * timeHandler method. Populates the time combo boxes with available times during business hours using the local timezone.
     */



    /**
     * Prevents a previous date from being selected for an appointment.
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


    /**
     * initialize
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerCombobox.setItems(customerList());
        contactCombobox.setItems(contactList());

        timeComboBoxes();

       // startHourCombo.setItems(times);
      //  startMinCombo.setItems(length);


        //endTimeCombo.setItems(apptHour());
        //endMinCombo.setItems(apptMin());


    }
}
