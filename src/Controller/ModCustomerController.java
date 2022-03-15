package Controller;

import Dao.DaoCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModCustomerController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void onActionMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All unsaved information will be lost. Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Customers = DaoCustomers.getAllCustomers();
        //customersTableView.setItems(customers);
        //customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        //customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        //customerAddyCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        //customerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        //customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

        /**
         * EDIT TO SHOW ACTUAL DIVISION INSTEAD OF THE ID*/

        //customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        //customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
    }
}
