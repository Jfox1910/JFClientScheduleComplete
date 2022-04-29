package Controller;

import Dao.*;
import Model.Appointment;
import Model.Customers;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.time.LocalDate.now;

public class MainScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

/*    @FXML private TableView<Appointment> apptTableview;
    @FXML private TableColumn<Appointment, Integer> apptIDCol;
    @FXML private TableColumn<Appointment, String> apptTitleCol;
    @FXML private TableColumn<Appointment, String> apptDescriptionCol;
    @FXML private TableColumn<Appointment, String> apptLocationCol;
    @FXML private TableColumn<Appointment, String> apptContactCol;
    @FXML private TableColumn<Appointment, String> apptTypeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> apptStartTimeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> apptEndTimeCol;
    @FXML private TableColumn<Appointment, Integer> apptCustomerIDCol;
    @FXML private TableColumn<Appointment, Integer> apptUserIDCol;*/

    @FXML private TableView<Appointment> apptTableview;
    @FXML private TableColumn<Integer, Integer> apptIDCol;
    @FXML private TableColumn<String, String> apptTitleCol;
    @FXML private TableColumn<String, String> apptDescriptionCol;
    @FXML private TableColumn<String, String> apptLocationCol;
    @FXML private TableColumn<String, String> apptContactCol;
    @FXML private TableColumn<String, String> apptTypeCol;
    @FXML private TableColumn<String, LocalDateTime> apptStartTimeCol;
    @FXML private TableColumn<String, LocalDateTime> apptEndTimeCol;
    @FXML private TableColumn<String, Integer> apptCustomerIDCol;
    @FXML private TableColumn<String, Integer> apptUserIDCol;

    @FXML private TableView<Customers> customersTableView;
    @FXML private TableColumn<Customers, Integer> customerIdCol;
    @FXML private TableColumn<Customers, String> customerNameCol;
    @FXML private TableColumn<Customers, String> customerAddyCol;
    @FXML private TableColumn<Customers, String> customerZipCol;
    @FXML private TableColumn<Customers, String> customerPhoneCol;
    @FXML private TableColumn<Customers, Integer> customerDivisionCol;

    @FXML RadioButton weekSelect;
    @FXML RadioButton monthSelect;

    @FXML RadioButton appViewAllRadio;
    @FXML ToggleGroup weekmonth;

    private static Customers modifyCustomers;
    private static Appointment modifyAppointment;
    private ObservableList<Customers> customers;
    private ObservableList<Appointment> appointments;


    /*
    TODO LIST COMPLETE PROJECT
    Must haves----------
    TIME OVERLAP
    ADD REPORTS
    JAVADOCS
     */


    /**
     * ADD appointment screen/controller load method
     * @param event
     * @throws IOException
     */
    public void onActionAddAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("View/addApptScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * MODIFY appointment screen/controller load method.
     * @param event
     * @throws IOException
     */
    public void onActionModAppt(ActionEvent event) throws IOException {

        modifyAppointment = apptTableview.getSelectionModel().getSelectedItem();

        if (modifyAppointment != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("View/modApptScreen.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("An appointment has not been selected. Please click on the correct appointment and try again.");
            alert.showAndWait();
        }


    }


    /**
     * CANCEL appointment method. Cancels the selected appointment. Provides a warning confirmation, delete confirmation and null selection alerts.
     * @param event
     * @throws IOException
     */
    public void onActionCancelAppt(ActionEvent event) throws IOException {

        if (apptTableview.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppointment = apptTableview.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("Appointment # " + selectedAppointment.getAppointment_ID() + " will be cancelled!");
            alert.setContentText("This action cannot be undone. Are you sure you wish to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("SUCCESS!");
                alert2.setContentText("Appointment # " + selectedAppointment.getAppointment_ID() + " has been cancelled.");
                alert2.showAndWait();
                AppointmentDAO.deleteAppointment(selectedAppointment.getAppointment_ID());
                apptTableview.setItems(AppointmentDAO.getAllAppointments());
            } else apptTableview.setItems(AppointmentDAO.getAllAppointments());
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("An appointment has not been selected. Please click on an appointment and try again.");
            alert.showAndWait();
        }

    }


    /**
     * Load Reports Method
     * @param event
     * @throws IOException
     */
    public void onActionLoadReports(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/reportsScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Add a customer screen/controller load method (Contained within the customer tab).
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
     * Modify customer screen/controller load method (Contained within the customer tab). Contains a null selection alert.
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
     * Selects the customer and deletes them. If an appointment is scheduled, alerts the user that the appointment has to be deleted before the customer will be removed.
     * Provides a confirmation upon successful deletion and reloads the customer tableview.
     * Provides a null selection alert.
     *
     * @param event
     * @throws IOException
     */
    public void onActionDeleteCustomer(ActionEvent event) throws IOException {

        if (customersTableView.getSelectionModel().getSelectedItem() != null) {

            Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText(selectedCustomer.getCustomerName() + " will be deleted from the database!");
            alert.setContentText("This action cannot be undone. Are you sure you wish to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                if (CustomersDao.customerDeleteValidation(selectedCustomer))
                {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("SUCCESS!");
                    alert2.setContentText(selectedCustomer.getCustomerName() + " has been deleted from the system.");
                    alert2.showAndWait();

                    CustomersDao.deleteCustomer(selectedCustomer.getCustomerId());
                    {
                        customersTableView.setItems(CustomersDao.getAllCustomers());
                }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR!");
                    a.setHeaderText("The selected customer has an appointment scheduled. Please cancel all appointments for the selected customer and try again.");
                    a.showAndWait();
                }
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText("A customer has not been selected. Please click on a customer name and try again.");
            alert.showAndWait();
        }
    }


    /**
     * Holds the selected customer for the modCustomerController
     * @return modifyCustomers
     */
    public static Customers getSelectedCustomer() {return modifyCustomers;}

    public static Appointment getSelectedAppointment() {return modifyAppointment;}


    /**
     * Week, month and all handlers.
     */
    public void monthView() {showByMonth();}
    public void weekView() {showByWeek();}
    public void viewAll() {showAll();}


    /**
     *Sorts all the listed appointments scheduled for the current week.
     */
    private void showByWeek() {
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        DayOfWeek lastDayOfWeek = DayOfWeek.FRIDAY;
        LocalDate firstDateOfWeek = now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate lastDateOfWeek = now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
        apptTableview.setItems(AppointmentDAO.getAppointmentDates(firstDateOfWeek, lastDateOfWeek));
    }


    /**
     * Sorts all the listed appointments scheduled for the current month.
     */
    private void showByMonth() {
        LocalDate day = now();
        LocalDate monthStartDay = day.withDayOfMonth(1);
        LocalDate monthEndDay = day.withDayOfMonth(day.lengthOfMonth());
        apptTableview.setItems(AppointmentDAO.getAppointmentDates(monthStartDay, monthEndDay));
    }


    /**
     * Shows all the scheduled appointments.
     */
    private void showAll() {
        appointments = AppointmentDAO.getAllAppointments();
        apptTableview.setItems(appointments);
    }


    /**
     * Checks for impending appointments and alerts the user during login to an appointment in the next 15 minutes.
     * Otherwise it alerts that no appointments are pending.
     */
    private void impendingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        ObservableList<Appointment> pendingAppt = AppointmentDAO.getImpendingAppt(now);
        if (pendingAppt.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome!");
            alert.setHeaderText("There are no pending appointments scheduled within the next 15 minutes.");
            alert.showAndWait();
        } else {
            for (Appointment appointment : pendingAppt) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Welcome!");
                alert.setHeaderText(" Attention. Appointment # " + appointment.getAppointment_ID() + " is scheduled at " + appointment.getStart() + " EST.");
                alert.showAndWait();
            }
        }
    }


    /**
     * Appointment table initialization. Loads the columns with the information from the DB appointment table.
     */
    private void setApptTables(){
        appointments = AppointmentDAO.getAllAppointments();
        apptTableview.setItems(appointments);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
    }


    /**
     * Customers table initialization. Loads the columns with the information from the DB customers table
     */
    private void setCustomerTable(){
        customers = CustomersDao.getAllCustomers();
        customersTableView.setItems(customers);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddyCol.setCellValueFactory(new PropertyValueFactory<>("customerAddy"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setApptTables();
        setCustomerTable();
        impendingAppointments();
        weekmonth.selectToggle(appViewAllRadio);

    }
}

