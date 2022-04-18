package Controller;

import Dao.*;
import Model.Appointment;
import Model.Countries;
import Model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Appointment> apptTableview;
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> apptLocationCol;
    @FXML
    private TableColumn<Appointment, String> apptContactCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartTimeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndTimeCol;
    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIDCol;
    @FXML
    private TableColumn<Appointment, Integer> apptUserIDCol;

    @FXML
    private TableView<Customers> customersTableView;
    @FXML
    private TableColumn<Customers, Integer> customerIdCol;
    @FXML
    private TableColumn<Customers, String> customerNameCol;
    @FXML
    private TableColumn<Customers, String> customerAddyCol;
    @FXML
    private TableColumn<Customers, String> customerZipCol;
    @FXML
    private TableColumn<Countries, String> customerPhoneCol;
    @FXML
    private TableColumn<Customers, String> customerCreatedDateCol;
    @FXML
    private TableColumn<Customers, String> customerCreatedCol;
    @FXML
    private TableColumn<Customers, Timestamp> customerUpdatedOnCol;
    @FXML
    private TableColumn<Customers, String> customerUpdatedByCol;
    @FXML
    private TableColumn<Customers, Integer> customerDivisionCol;
    @FXML
    private RadioButton weekSelect;
    @FXML
    private RadioButton monthSelect;

    private int customerModID = 0;
    private int days = 0;
    private static Customers modifyCustomers;
    private ObservableList<Customers> customers;
    private ObservableList<Appointment> appointments;


    /*    @FXML private TextField CustomerID;
    @FXML private TextField CustomerName;
    @FXML private TextField CustomerAddress;
    @FXML private TextField CustomerZip;
    @FXML private TextField CustomerPhone;
    @FXML private ComboBox customerCountry;
    @FXML private ComboBox customerDivision;
    @FXML private RadioButton monthSelect;
    @FXML private RadioButton weekSelect;*/

/*    private static Divisions modifyCustomerDivision;
    private static Countries modifyCustomerCountry;

    public ObservableList<Countries> allCountries = DaoCountries.getAllCountries();
    public ObservableList<Divisions> usDivisionsList = DaoDivisions.getUsStates();
    public ObservableList<Divisions> canadianDivisionList = DaoDivisions.getCanadianTerritories();
    public ObservableList<Divisions> UKDivisionList =DaoDivisions.getUKTerritories();
    public ObservableList<Appointment> allAppointments = DaoAppointments.getAllAppointments();*/



    /*
    TODO LIST COMPLETE PROJECT
    Must haves----------
    LAMBDAS
    MONTH WEEK SELECT
    MOD APPTS
    ADD REPORTS
    JAVADOCS
    README
     */


    /**
     * ADD appointment method
     *
     * @param event
     * @throws IOException
     */
    public void onActionAddAppt(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/addApptScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * MODIFY appointment method
     *
     * @param event
     * @throws IOException
     */
    public void onActionModAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/modApptScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * CANCEL appointment method
     *
     * @param event
     * @throws IOException
     */
    public void onActionCancelAppt(ActionEvent event) throws IOException {

        if (apptTableview.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppointment = apptTableview.getSelectionModel().getSelectedItem();

            for (Appointment appointment : appointments) {
                if (appointment.getApptIDCol() == selectedAppointment.getApptIDCol()) {
                    DaoAppointments.deleteAppointment(appointment.getApptIDCol());
                }
            }

            /**
             * Hold and alert the user before deleting the selected appointment. Holds first and deletes on OK then reloads the tableview.
             */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("The selected appointment will be deleted from the database!");
            alert.setContentText("This action cannot be undone. Are you sure you wish to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DaoAppointments.deleteAppointment(selectedAppointment.getApptIDCol());
                apptTableview.setItems(DaoAppointments.getAllAppointments());

                /**
                 * Confirmation that the appointment has been deleted.
                 */
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("SUCCESS!");
                alert2.setContentText("The appointment has been deleted from the system.");
                alert2.showAndWait();
            }
        } else {
            /**
             * Hold and alert the user that an appointment must be selected.
             */
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("An appointment has not been selected. Please click on an appointment and try again.");
            alert.showAndWait();
        }

    }


    /**
     * Load Reports Method
     *
     * @param event
     * @throws IOException
     */
    public void onActionLoadReports(ActionEvent event) throws IOException {

        //DECIDE ON WHAT TO REPORT

        System.out.println("Test LOAD Reports Button.");
    }


    /**
     * Handle month/week selection
     *
     * @param actionEvent
     */
    public void onActionSelectRange(ActionEvent actionEvent) {
    /*    if (weekSelect.isSelected()) {
            apptTableview.setItems(DaoAppointments.getAllAppointments(days, 7));
        }
        if (monthSelect.isSelected()) {
            apptTableview.setItems(DaoAppointments.getAllAppointments(days, 30));
        }*/
        System.out.println("Testing month");
    }


    /**
     * Add a customer method (Contained within the customer tab).
     *
     * @param event
     * @throws IOException
     */
    public void onActionAddCustomer
    (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/addCustomerScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Modify customer method (Contained within the customer tab).
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionModifyCustomer(ActionEvent actionEvent) throws IOException {

        modifyCustomers = customersTableView.getSelectionModel().getSelectedItem();
        if (modifyCustomers != null) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/modifyCustomerScreen.fxml"));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("A customer has not been selected. Please click on a customer name and try again.");
            alert.showAndWait();
        }
    }


    /**
     * Selects the customer and deletes them and any appointment they have scheduled.
     *
     * @param event
     * @throws IOException
     */
    public void onActionDeleteCustomer(ActionEvent event) throws IOException {

        if (customersTableView.getSelectionModel().getSelectedItem() != null) {

            Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

            /*for (Appointment appointment : appointments) {
                if (appointment.getApptIDCol() == selectedCustomer.getCustomerId()) {
                    DaoAppointments.deleteAppointment(appointment.getApptIDCol());
                }
            }*/

            /**
             * Hold and alert the user before deleting the selected customer. Holds first and deletes on OK then reloads the tableview.
             * If an appointment is scheduled, alerts the user that the appointment has to be deleted before the customer will be removed.
             */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("The selected customer will be deleted from the database!");
            alert.setContentText("This action cannot be undone. Are you sure you wish to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                if (DaoCustomers.customerDeleteValidation(selectedCustomer))
                {
                    DaoCustomers.deleteCustomer(selectedCustomer.getCustomerId());
                    {
                        customersTableView.setItems(DaoCustomers.getAllCustomers());
                }

                    /**
                     * Confirmation that the customer has been deleted.
                     */
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("SUCCESS!");
                    alert2.setContentText("The customer has been deleted from the system.");
                    alert2.showAndWait();
                }
                else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR!");
                    a.setHeaderText("The selected customer has an appointment scheduled. Please cancel all appointments for the selected customer and try again.");
                    a.showAndWait();
                }
            }
        }else {
            /**
             * Hold and alert the user that a customer name must be selected.
             */
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("A customer has not been selected. Please click on a customer name and try again.");
            alert.showAndWait();
        }
    }


    public static Customers getSelectedCustomer() {
        return modifyCustomers;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Appointment table initialization. Loads the columns with the information from the DB appointment table
         */
        appointments = DaoAppointments.getAllAppointments();
        apptTableview.setItems(appointments);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptIDCol"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitleCol"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescriptionCol"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocationCol"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("apptContactCol"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptTypeCol"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptStartTimeCol"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptEndTimeCol"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerIDCol"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("apptUserIDCol"));

        /**
         * Customers table initialization. Loads the columns with the information from the DB customers table
         */
        customers = DaoCustomers.getAllCustomers();
        customersTableView.setItems(customers);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddyCol.setCellValueFactory(new PropertyValueFactory<>("customerAddy"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));


        if (weekSelect.isSelected()) {
            apptTableview.setItems(DaoAppointments.getAppointments(days, 7));
        }
        if (monthSelect.isSelected()) {
            apptTableview.setItems(DaoAppointments.getAppointments(days, 30));
        }

    }
}

