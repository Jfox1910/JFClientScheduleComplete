package Controller;

import Dao.DBCustomers;
import Model.Countries;
import Model.Divisions;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    private final ObservableList<Countries> countries = FXCollections.observableArrayList();
    private final ObservableList<Divisions> divisions = FXCollections.observableArrayList();

    @FXML private TextField addCustomerID;
    @FXML private TextField addCustomerName;
    @FXML private TextField addCustomerAddy;
    @FXML private ComboBox addCustomerDivision;
    @FXML private ComboBox addCustomerCountry;
    @FXML private TextField addCustomerPhone;
    @FXML private TextField addCustomerZip;
    @FXML private Button addCustomerButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void onActionAddCustomer(){

        //Dao.DBCustomers.newCustomer();

        //.setVisibleRowCount(5);
        //.getSelectionModel().selectFirst();
        System.out.println("Testing Add Customer Button");

    }

    //Exit to the main screen
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

        addCustomerCountry.getItems().addAll(countries);

    }
}
