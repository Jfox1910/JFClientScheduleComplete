package Controller;

import Dao.DaoLogin;
import Model.loginUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;
import java.time.ZoneId;

/**
 * Login screen controller class. Accepts the users name and password, connects to the DB and verifies if they exist. If they do then it opens the application.
 * If not an error message alerts the user to the problem. Displays in French if needed.
 * Logs the username, password and time to a textfile called LoginAttempts.txt stored in the SRC folder
 */

public class LoginScreenController implements Initializable {

    public Label mainLabel;
    public Label locationLabel;
    public TextField usernameTextField;
    public PasswordField usernamePasswordField;
    public Button loginButton;
    public Button cancelButton;
    public Label userLocale;

    private static final String reportsFile = "LoginAttempts.txt";
    boolean loginSuccess = false;
    private final String userLocation = ZoneId.systemDefault().getId();
    private ResourceBundle rb;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public String getUserName() {return usernameTextField.getText();}


//Login Method. Creates a list and populates it with all the users in the DB.
    public void onActionLogin(ActionEvent event) throws IOException {
        boolean verifyUser = DaoLogin.loggedInUser(usernameTextField.getText(), usernamePasswordField.getText());{

            String user = usernameTextField.getText();
            String userName = usernameTextField.getText();
            String password = usernamePasswordField.getText();
            if (verifyUser) {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/mainScreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                loginSuccess = true;
                reports(userName, password, "TRUE");

            }else {
                loginSuccess = false;
                usernameTextField.clear();
                usernamePasswordField.clear();

                reports(userName, password, "FALSE");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username and/or password is incorrect. Verify your information and try again.");
                alert.showAndWait();
                return;
            }
        }
    }

    public String getLoginUser() {
        return usernameTextField.getText();
    }




//Writes a log of login attempts to a textfile LoginAttempts.txt.
    private void reports (String userName, String password, String successfulLogin){
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(reportsFile, true));
            printWriter.append((new Date()) + " Login successful? " + successfulLogin + " Username? " + userName + " Password? " + password + "\n");
            printWriter.close();
        }catch (IOException e){}
    }

//Exit Method with a confirmation
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
    public void initialize(URL location, ResourceBundle resources) {
        userLocale.setText(userLocation);

/**
 * Initializes the language selection when the default language is French
 */
        try {
            rb = ResourceBundle.getBundle("utils/MessagesBundle",Locale.getDefault());
            System.out.println(rb.getString("username"));
            mainLabel.setText(rb.getString("mainlabel"));
            usernameTextField.setPromptText(rb.getString("username"));
            usernamePasswordField.setPromptText(rb.getString("password"));
            loginButton.setText(rb.getString("loginbutton"));
            cancelButton.setText(rb.getString("cancelbutton"));
            locationLabel.setText(rb.getString("Location"));
        }catch (MissingResourceException e){

        }
    }
}