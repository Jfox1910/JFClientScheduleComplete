package Controller;

import Dao.AppointmentDAO;
import Dao.CustomersDao;
import Model.Appointment;
import Model.Contacts;
import Model.Customers;
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

    @FXML private TableView<Customers> customerApptTable;
    @FXML private TableColumn<Customers, String> customerCol;
    @FXML private TableColumn<Customers, String> dateCol;
    @FXML private TableColumn<Customers, Integer>totalCol;

    @FXML private TableView<Contacts> contactApptTable;

    @FXML private TableView<Appointment> totalApptTable;


    private ObservableList<Appointment> appointments;
    private ObservableList<Customers> customers;




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
        customers = CustomersDao.getCustomerReport();
        customerApptTable.setItems(customers);
        customerCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        //apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setApptTables();
    }
}
