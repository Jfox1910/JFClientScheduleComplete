package Controller;

import Dao.DaoLogin;
import Model.loginUser;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    public TextField usernameTextField;
    public PasswordField usernamePasswordField;
    public Button loginButton;
    public Button cancelButton;
    public Label userLocale;

    private Stage stage;
    private Scene scene;
    private Parent root;


    //Login Method
    public void onActionLogin(ActionEvent event) throws IOException {
        ObservableList<loginUser> verifyUser = DaoLogin.getAllUsers();
        String userName = usernameTextField.getText();
        String userPassword = usernamePasswordField.getText();

        for (int i = 0; i < verifyUser.size(); i++) {
            if (userName.equals(verifyUser.get(i).getUserName()) && userPassword.equals(verifyUser.get(i).getUserPassword())) {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainScreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Username or password is incorrect. Please check your spelling and try again.");
                return;
            }
        }


    }

    //Exit Method
    @FXML
    void onActionExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("This will Exit the Program. Are you sure you wish to continue?");
        System.out.println("Exiting Program.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}