package Controller;

import Dao.AppointmentDAO;
import Dao.CustomersDao;
import Dao.ReportsDAO;
import Model.Appointment;
import Model.Contacts;
import Model.Customers;
import Model.Reports;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TableView<Reports> customerApptTable;
    @FXML private TableColumn<Reports, String> customerCol;
    @FXML private TableColumn<Reports, String> dateCol;
    @FXML private TableColumn<Reports, Integer>totalCol;

    @FXML private TableView<Reports> contactApptTable;

    @FXML private TableView<Reports> totalApptTable;

    private ObservableList<Reports> reports;


    //TODO Contact and Customer Dropdowns accessing everything from the DB. MAX appts count for 3rd.


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

    private void setApptTables(){
        reports = ReportsDAO.getTotalAppointments();
        customerApptTable.setItems(reports);
        customerCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>(""));
        //apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setApptTables();
    }
}
