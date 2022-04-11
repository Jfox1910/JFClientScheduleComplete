import utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main class. Launches the program and establishes the DB connection.
 */

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException{


        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/loginScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {e.printStackTrace();
        }}

    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr"));


        JDBC.startConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
