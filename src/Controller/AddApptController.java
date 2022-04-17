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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddApptController implements Initializable {
    private final ObservableList<Integer> hourInput = FXCollections.observableArrayList();
    private final ObservableList<String> minuteInput = FXCollections.observableArrayList();

    private final ObservableList<String> times = FXCollections.observableArrayList();
    private final ObservableList<Integer> length = FXCollections.observableArrayList(IntStream.range(15, 91).boxed().collect(Collectors.toList()));

    public static Customers customers;
    public static Appointment appointment;
    private final Utils utils = Utils.getInstance();

    private Stage stage;
    private Scene scene;
    private Parent root;

    //@FXML private ComboBox userComboBox;
    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private ComboBox customerCombobox;
    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox contactCombobox;


    @FXML private ComboBox startTimeCombo;
    @FXML private ComboBox <Integer> startMinCombo;


    @FXML private ComboBox endTimeCombo;
    @FXML private ComboBox endMinCombo;
    @FXML private ToggleGroup am_pmStart;
    @FXML private ToggleGroup am_pmEnd;


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

            int appointmentID = 0;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String appointmentDate = this.appointmentDate.getValue().format(dtf);
            String title = titleField.getText();
            String location = locationField.getText();
            String description = descriptionField.getText();
            String type = typeField.getText();
            Timestamp startTime = startTime();
            Timestamp endTime =  endTime();
            int customerID = getIdFromComboBox(customerCombobox);
            int contactID = getIdFromComboBox(contactCombobox);

            Appointment appt = new Appointment(appointmentID, title, description, location, type, startTime, endTime, customerID, contactID);

            System.out.println(appointmentDate + title + location + description + type + startTime +  endTime + customerID + contactID);
        /**
         * Check that a name, address and phone has been entered and gives an alert if it isn't there.
         */
        if (title.isEmpty() || location.isEmpty() || description.isEmpty() || type.isEmpty() || contactCombobox.getItems().isEmpty()){
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

                    DaoAppointments.newAppointment(appt, startTime, endTime);

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

    /**
     * Creates an appointment beginning timestamp.
     * @return
     * @throws ParseException
     */
    public Timestamp startTime() throws ParseException {
        return getTimestamp(appointmentDate, startTimeCombo, startMinCombo, am_pmStart);
    }

    /**
     * Creates an appointment end timestamp.
     * @return
     * @throws ParseException
     */
    public Timestamp endTime() throws ParseException {
        return getTimestamp(appointmentDate, endTimeCombo, endMinCombo, am_pmEnd);
    }

    /**
     * Date/Time method
     * @param datePicker
     * @param hourPicker
     * @param minutePicker
     * @param am_pm
     * @return
     * @throws ParseException
     */
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
    }



    /**
     * Creates and returns an array of HOUR options.
     * @return hourInput
     */
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

    /**
     * Creates and returns an array of MINUTE options.
     * @return minuteInput
     */
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
    }


    /** AN observable list holding the customers from the database. Used to initialize the "Customer" combobox.
     *
     * @return customers
     */
    private ObservableList customerList(){
        ObservableList<String> customers = FXCollections.observableArrayList();
        for(Customers customer : DaoCustomers.getAllCustomers()){
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
        for (Contacts contacts : DaoContacts.getAllContacts()){
            allContactNames.add(String.valueOf(contacts.getContactID() + " : " + contacts.getContactName()));
        }return allContactNames;
    }

    /**
     * timeHandler method. Populates the time combo boxes with available times during business hours.
     */
    private void timeHandler(){
        try {
            // Creating a loop that will add times from 08:00 to 22:00 in 30 minute increments to list for creating Time Combo box.
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String timer = "08:00:00";
            while (!timer.equals("22:00:00")) {
                Date d = df.parse(timer);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal.add(Calendar.MINUTE, 30);
                times.add(df.format(cal.getTime()));
                timer = df.format(cal.getTime());
            }
            startTimeCombo.setItems(times);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialize
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeHandler();
        customerCombobox.setItems(customerList());
        contactCombobox.setItems(contactList());

        startTimeCombo.setItems(apptHour());
        startMinCombo.setItems(length);


        //endTimeCombo.setItems(apptHour());
        //endMinCombo.setItems(apptMin());


    }
}
