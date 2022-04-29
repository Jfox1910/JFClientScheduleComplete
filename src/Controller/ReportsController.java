package Controller;

import Dao.AppointmentDAO;
import Dao.ContactsDao;
import Dao.CustomersDao;
import Dao.ReportsDAO;
import Model.Appointment;
import Model.Contacts;
import Model.Customers;
import Model.Reports;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import utils.JDBC;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TableView customerApptTable;
    @FXML private TableColumn<String,String>  customerCol;
    @FXML private TableColumn<String,String>  dateCol;
    @FXML private TableColumn<String,String>  typeCol;
    @FXML private TableColumn<String,String> totalCol;
    @FXML private ComboBox <Contacts>contactCombobox;

    public TableView contactApptTable;
    public TableColumn Appointment_ID;
    public TableColumn titleCol;
    public TableColumn contactTypeCol;
    public TableColumn descriptionCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn Customer_ID;

    @FXML private TableView totalApptTable;
    @FXML private TableColumn totalCustomersCol;
    @FXML private TableColumn totalApptCol;

    private ObservableList<Reports> reports;
    private ObservableList<Reports> customerTotal;
    Contacts selectedContact;


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


    /**
     * Generates the columns based on the Contact selected from the dropdown.
     * @param
     */
    public void generateContactSchedule(){

        Contacts selectedContact = contactCombobox.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contactApptTable.setItems(contactFilter());
            Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            contactTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

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


    private void setTotalTables(){
        customerTotal = ReportsDAO.getTotalCustomers();
        totalApptTable.setItems(customerTotal);
        totalCustomersCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Total"));
        System.out.println( customerTotal.toString());

        /*totalApptTable.setItems(ReportsDAO.getTotalCustomers());
        totalCustomersCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
        totalApptTable.refresh();*/
    }


    /**
     * An observable list holding contacts from the database. Used to initialize the "Contact" combobox.
     * @return allContactNames
     */
    private ObservableList<String> contactList(){
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        for (Contacts contacts : ContactsDao.getAllContacts()){
            allContactNames.add(contacts.getContactID() + " : " + contacts.getContactName());
        }return allContactNames;
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
     * 2nd LAMBDA function that filters contact appointments upon combobox selection. Simplified the code and brought it down 1 line from the original method.
     * @return contactFilteredList
     */
    public ObservableList<Appointment> contactFilter() {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        FilteredList<Appointment> contactFilteredList = new FilteredList<>(allAppointments, i -> i.getContact_ID() == contactCombobox.getSelectionModel().getSelectedItem().getContact_ID());

        return contactFilteredList;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactCombobox.setItems(ContactsDao.getAllContacts());

        //contactList();
        //contactCombobox.setItems(contactList());

      // setTotalTables();
       setApptTables();
    }
}
