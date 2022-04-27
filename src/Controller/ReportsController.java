package Controller;

import Dao.AppointmentDAO;
import Dao.ContactsDao;
import Dao.CustomersDao;
import Dao.ReportsDAO;
import Model.Appointment;
import Model.Contacts;
import Model.Customers;
import Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TableView<Reports> customerApptTable;
    @FXML private TableColumn<Reports, String> customerCol;
    @FXML private TableColumn<Reports, String> dateCol;
    @FXML private TableColumn<Reports, String> typeCol;
    @FXML private TableColumn<Reports, Integer>totalCol;
    @FXML private ComboBox contactCombobox;

    public TableView contactApptTable;
    public TableColumn Appointment_ID;
    public TableColumn titleCol;
    public TableColumn contactTypeCol;
    public TableColumn descriptionCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn Customer_ID;

    @FXML private TableView<Reports> totalApptTable;
    @FXML private TableColumn<Reports, String> totalCustomersCol;
    @FXML private TableColumn<Reports, String> totalApptCol;

    private ObservableList<Reports> reports;


    //TODO Contact and Customer Dropdowns accessing everything from the DB. MAX appts count for 3rd.

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


    public void generateContactSchedule(Event event){
        Contacts selectedContact = (Contacts) contactCombobox.getSelectionModel().getSelectedItem();
        if (selectedContact != null){
            contactApptTable.setItems(ReportsDAO.getAllAppointmentsForContact(selectedContact.getContactID()));
            Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            contactTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            Customer_ID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
    }

    /**
     * Sets all of the table in the Appt. by type table.
     */
    private void setApptTables(){
        reports = ReportsDAO.getAppointmentsByType();
        customerApptTable.setItems(reports);
        customerCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Contacts> contacts = ContactsDao.getAllContacts();
        //contactCombobox.setItems(contactList());
        contactCombobox.setItems(contacts);

        setApptTables();
    }
}
