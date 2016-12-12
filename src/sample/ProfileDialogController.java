package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by arslan on 11/27/16.
 */
public class ProfileDialogController implements ParentController {

    MainController controller;
    FileController fileController;

    @FXML
    private TextField profileUsernameField, profilePasswordField;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void createProfile(ActionEvent event)    {
        String username = profileUsernameField.getText();
        String password = profilePasswordField.getText();

        File f = new File(username + ".json");

        if (username == null || password == null || username.equals("") || password.equals("") || f.exists())   {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The username or password field is not entered.");
            alert.setContentText("Please enter a username or password.");

            alert.showAndWait();
            controller.setScene(ParentController.scene1ID);
        }
        else    {
            File file = new File(username + ".json");
            fileController = new FileController();

            int[][] modes = new int[4][8];

            for (int i = 0; i < modes.length; i++)  {
                for (int j = 0; j < modes[i].length; j++)   {
                    modes[i][j] = -1;
                }
            }

            for (int i = 0; i < modes.length; i++)  {
                modes[i][0] = 0;
            }

            try {
                BuzzData data = new BuzzData(username, password, modes);
                fileController.saveData(data, file);
                controller.setScene(ParentController.scene1ID);
            }
            catch (FileNotFoundException e) {
                System.out.println("File was not found.");
            }
        }
    }

    @FXML
    public void dialogCancel(ActionEvent event) {
        controller.setScene(ParentController.scene1ID);
    }
}
