package Controller;

import Dao.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TableView typeApptTable;
    @FXML private TableColumn<Customers,String>  customerCol;
    @FXML private TableColumn<String,String>  dateCol;
    @FXML private TableColumn<String,String>  typeCol;
    @FXML private TableColumn<String,String> totalCol;

    @FXML private ComboBox<Contacts> contactCombobox;
    @FXML private TableView <Appointment> contactApptTable;
    @FXML private TableColumn<Appointment, Integer> Appointment_ID;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> contactTypeCol;
    @FXML private TableColumn<Appointment, String> descriptionCol;
    @FXML private TableColumn<Appointment, LocalDateTime> startCol;
    @FXML private TableColumn<Appointment, LocalDateTime> endCol;
    @FXML private TableColumn<Customers, Integer> Customer_ID;

    @FXML private TextField totalCustomers;
    @FXML private TextField totalAppointments;

    private static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments;
    private ObservableList<Reports> reports;

    Contacts selectedContact;


    /**
     * Exits back to the main screen. Contains a confirmation.
     * @param event
     * @throws IOException
     */
    public void onActionMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit back to the main screen?");
        alert.showAndWait().ifPresent((response -> {
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
        }));
    }

    /**
     * Selects the contact from the combobox
     */
    public void selectContact(){
       selectedContact = contactCombobox.getSelectionModel().getSelectedItem();
       generateContactSchedule();
    }


    /**
     * Generates the columns based on the Contact selected from the dropdown.
     * @param
     */
    public void generateContactSchedule(){

            contactApptTable.setItems(contactFilter());
            Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            contactTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
    }


    /**
     * Sets all of the table in the Appt. by type table.
     */
        private void setApptTables(){
        reports = ReportsDAO.getAppointmentsByType();
        typeApptTable.setItems(reports);
        customerCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
    }


    /**
     * Sets the Total Table up. This is the 3rd Report requirement
     * It gives a total count for the Appointments in the DB as well as the individual Customers.
     */
    private void setTotalTables(){
        List<Appointment> apptTotalList = AppointmentDAO.getAllAppointments();
        totalAppointments.setText(String.valueOf(apptTotalList.size()));

        List<Customers> customerTotalList = CustomersDao.getAllCustomers();
        totalCustomers.setText(String.valueOf(customerTotalList.size()));
    }


    /**
     * 2nd LAMBDA function that filters contact appointments upon combobox selection. Simplified the code quite a bit and brought it down several lines from the original method.
     * @return contactFilteredList
     */
    public ObservableList<Appointment> contactFilter() {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        return new FilteredList<>(allAppointments, i -> i.getContact_ID() == contactCombobox.getSelectionModel().getSelectedItem().getContact_ID());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       contactCombobox.getItems().setAll(ContactsDao.getAllContacts());

       setApptTables();
       setTotalTables();
    }
}
