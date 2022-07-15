package Controller;

import Dao.UserDao;
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
import java.sql.SQLException;
import java.util.*;
import java.time.ZoneId;


/**
 * Login screen controller class. Accepts the users name and password, connects to the DB and verifies if they exist. If they do then it opens the application.
 * If not an error message alerts the user to the problem. Displays in French if needed.
 * Logs the username, password and time to a textfile called login_activity.txt stored in the SRC folder
 */

public class LoginScreenController implements Initializable {

    public Label mainLabel;
    public Label locationLabel;
    public TextField usernameTextField;
    public PasswordField usernamePasswordField;
    public Button loginButton;
    public Button cancelButton;
    public Label userLocale;

    private static final String reportsFile = "login_activity.txt";
    boolean loginSuccess = false;
    private final String userLocation = ZoneId.systemDefault().getId();
    private ResourceBundle rb;

    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Login Method. Creates a list and populates it with all the users in the DB. Sets an alert in either French or English based on locale.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onActionLogin(ActionEvent event) throws IOException, SQLException {
        boolean verifyUser = UserDao.loggedInUser(usernameTextField.getText(), usernamePasswordField.getText());{

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
                if (Locale.getDefault().getDisplayLanguage().equals("français")){
                alert.setContentText(rb.getString("Login_Error!"));
                }
                else {
                    alert.setContentText("Login Error! Check Username and Password and try again.");
                }
                alert.showAndWait();
                return;
            }
        }
    }


    /**
     * Writes a log of login attempts to a textfile login_activity.txt.
     * @param userName
     * @param password
     * @param successfulLogin
     */
    private void reports (String userName, String password, String successfulLogin){
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(reportsFile, true));
            printWriter.append((new Date()) + " Login successful? " + successfulLogin + " Username? " + userName + " Password? " + password + "\n");
            printWriter.close();
        }catch (IOException e){}
    }

    /**
     * Exit with a confirmation
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you wish to exit?");
        alert.showAndWait().ifPresent((response -> {
            System.exit(0);
        }));
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