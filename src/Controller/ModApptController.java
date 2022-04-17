package Controller;

import Dao.DaoUser;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ModApptController implements Initializable {


/*    public void handleUserCombobox(javafx.event.ActionEvent event){

        Object selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        String allUserNames = selectedUser.toString();

        allUserNames.equalsIgnoreCase("User_Name");
    }*/

    /**
     * An observable list holding the users from the database. Used to initialize the "User" combobox.
     * @return allUserNames
     */
    private ObservableList<String> userList(){
        ObservableList<String> allUserNames = FXCollections.observableArrayList();
        for (User allUsers : DaoUser.getAllUsers()){
            String loginName;
            loginName = allUsers.getUserName();
            allUserNames.add(loginName);
        }return allUserNames;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

