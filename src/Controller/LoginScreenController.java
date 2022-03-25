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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    public TextField usernameTextField;
    public PasswordField usernamePasswordField;
    public Button loginButton;
    public Button cancelButton;
    public Label userLocale;

    boolean loginSuccess = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Login Method. Creates a list and populates it with all the users in the DB.
    public void onActionLogin(ActionEvent event) throws IOException {
        //Pulls the DBs user table from the DaoLogin class and creates an OL.
        ObservableList<loginUser> verifyUser = DaoLogin.getAllUsers();

        String userName = usernameTextField.getText();
        String userPassword = usernamePasswordField.getText();

        //Verifys that the login creds are valid, then either opens the main application or denies entry and throws an error screen.
        for (int i = 0; i < verifyUser.size(); i++) {
            if (userName.equals(verifyUser.get(i).getUserName()) && userPassword.equals(verifyUser.get(i).getUserPassword())) {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainScreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                loginSuccess = true;

                break;
            }else {
                loginSuccess = false;
                usernameTextField.clear();
                usernamePasswordField.clear();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username and/or password is incorrect. You must be really embarrassed right? Maybe a little nervous? Did we call the password police? It's possible. Do you hear a helicopter in the distance? Quick! Verify your information and try again!");
                alert.showAndWait();
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

        Locale locale = Locale.getDefault();

    }
}