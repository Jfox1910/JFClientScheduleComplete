package Controller;

import Controller.MainScreenController;
import Model.Appointments;
import Model.Customers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddApptController implements Initializable {
    public static Customers customers;
    public static Appointments appointments;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField titleField;
    @FXML private TextField locationTextfield;
    @FXML private ComboBox nameCombobox;
    @FXML private ComboBox descriptionTextfield;
    @FXML private ComboBox typeCombobox;
    @FXML private ComboBox contactCombobox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox startHour;
    @FXML private ComboBox startMinute;
    @FXML private ComboBox endHour;
    @FXML private ComboBox endMinute;



    //Exit to the main screen
    public void onActionMainScreen(javafx.event.ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All unsaved information will be lost. Are you sure you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}
