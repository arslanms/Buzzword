package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by arslan on 12/9/16.
 */
public class EditProfileController implements ParentController {

    MainController controller;
    FileController fileController;

    @FXML
    private PasswordField editProfileCurrentPwd;
    @FXML
    private PasswordField editProfileNewPwd;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void cancelEdit(ActionEvent event)   {
        controller.setScene(scene8ID);
    }

    @FXML
    public void updateUser(ActionEvent event)   {

        fileController = new FileController();
        BuzzData data = controller.getData();
        String password = data.getPassword();
        String currentPassword = editProfileCurrentPwd.getText();
        String newPassword = editProfileNewPwd.getText();

        if (currentPassword.equals("") || newPassword.equals("") || currentPassword == null || newPassword == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your current password does not match.");
            alert.setContentText("Please enter the correct password.");

            alert.showAndWait();
        }
        else {
            if (password.equals(currentPassword)) {

                try {

                    data.setPassword(newPassword);
                    controller.setData(data);

                    File file = new File(controller.getData().getUsername() + ".json");

                    fileController.saveData(controller.getData(), file);

                } catch (FileNotFoundException e) {
                    System.out.println("File was not found.");
                }

                controller.setScene(scene2ID);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Your current password does not match.");
                alert.setContentText("Please enter the correct password.");

                alert.showAndWait();
            }
        }

    }
}
