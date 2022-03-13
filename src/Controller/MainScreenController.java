package Controller;

import Dao.DaoAppointments;
import Dao.DaoCustomers;
import Model.Appointments;
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


    @FXML private TableView<Appointments> apptTableview;
    @FXML private TableColumn<Appointments, Integer> apptIDCol;
    @FXML private TableColumn<Appointments, String> apptTitleCol;
    @FXML private TableColumn<Appointments, String> apptDescriptionCol;
    @FXML private TableColumn<Appointments, String> apptLocationCol;
    @FXML private TableColumn<Appointments, String> apptContactCol;
    @FXML private TableColumn<Appointments, String> apptTypeCol;
    @FXML private TableColumn<Appointments, LocalDateTime> apptStartTimeCol;
    @FXML private TableColumn<Appointments, LocalDateTime> apptEndTimeCol;
    @FXML private TableColumn<Appointments, Integer> apptCustomerIDCol;
    @FXML private TableColumn<Appointments, Integer> apptUserIDCol;


    @FXML private TableView<Customers> customersTableView;
    @FXML private TableColumn<Customers, Integer> customerIdCol;
    @FXML private TableColumn<Customers, String> customerNameCol;
    @FXML private TableColumn<Customers, String> customerAddyCol;
    @FXML private TableColumn<Customers, String> customerZipCol;
    @FXML private TableColumn<Countries, String> customerPhoneCol;
    @FXML private TableColumn<Customers, String> customerCreatedDateCol;
    @FXML private TableColumn<Customers,String> customerCreatedCol;
    @FXML private TableColumn<Customers, Timestamp> customerUpdatedOnCol;
    @FXML private TableColumn<Customers, String> customerUpdatedByCol;
    @FXML private TableColumn<Customers, Integer> customerDivisionCol;


    private ObservableList<Customers> customers;
    private ObservableList<Appointments> appointments;




    //----APPOINTMENT TAB METHODS----

    //Add Appointments Method
    public void onActionAddAppt(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addApptScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Modify Appointments Method
    public void onActionModAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("modApptScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Cancel an appointment method
    public void onActionCancelAppt(ActionEvent event) throws IOException {

        //NEEDS TO BE IMPLEMENTED

        System.out.println("Test CANCEL Appointments Button.");

    }

    //Load Reports Method
    public void onActionLoadReports(ActionEvent event) throws IOException {

        //DECIDE ON WHAT TO REPORT

        System.out.println("Test LOAD Reports Button.");
    }


    //----CUSTOMER TAB METHODS----

    //Add a customer method (Contained within the customer tab)
    public void onActionAddCustomer(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addCustomerScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Modify existing customer method
    public void onActionModifyCustomer(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("modifyCustomerScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    //Selects the customer and deletes them and any appointments they have scheduled.
    public void onActionDeleteCustomer(ActionEvent event) throws IOException{


            if (customersTableView.getSelectionModel().getSelectedItem() != null){
                Customers thisCustomer = customersTableView.getSelectionModel().getSelectedItem();

                for (int i = 0; i < appointments.size(); i++) {
                    if (appointments.get(i).getApptIDCol() == thisCustomer.getCustomerIdCol()) {
                        DaoAppointments.deleteAppointment(appointments.get(i).getApptIDCol());
                    }
                }

                //Hold and alert the user before deleting the selected customer.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("ATTENTION!");
                alert.setHeaderText("The selected customer will be deleted from the database.");
                alert.setContentText("Are you sure you wish to continue?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    DaoCustomers.deleteCustomer(thisCustomer.getCustomerIdCol());
                    customersTableView.setItems(DaoCustomers.getAllCustomers());
                }

        }
        System.out.println("Test DELETE customer from database");

    }

    @Override
    public void initialize (URL location, ResourceBundle resources){

      /*  LocalTime start = LocalTime.of(6, 0);
        LocalTime end = LocalTime.NOON;
        while(start.isBefore(end.plusSeconds(1))){
            testBox.getItems().add(start);
            start = start.plusMinutes(15);
        }
        testBox.getSelectionModel().select(LocalTime.of(8,0));

        testBox.setPromptText("Whatever");

        @Override
        public String toString(){
        return (Integer.toString(objectName)); }
        */

        //Appointments
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

        //Customers
        customers = DaoCustomers.getAllCustomers();
        customersTableView.setItems(customers);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerIdCol"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerNameCol"));
        customerAddyCol.setCellValueFactory(new PropertyValueFactory<>("customerAddyCol"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZipCol"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneCol"));

        /**
         * EDIT TO SHOW ACTUAL DIVISION INSTEAD OF THE ID*/

        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivisionCol"));

    }
}
