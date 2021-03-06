package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by arslan on 11/27/16.
 */
public class LoginController implements ParentController {

    private MainController controller;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void openProfileDialog(ActionEvent event)    {
        controller.setScene(ParentController.scene6ID);
    }

    @FXML
    public void openLoginDialog(ActionEvent event)  {
        controller.setScene(ParentController.scene5ID);
    }

    @FXML
    public void keyPressEvents(KeyEvent event)   {
        if (event.isControlDown() && event.isShiftDown() && event.getCode() == KeyCode.P)   {
            controller.setScene(scene6ID);
        }
        if (event.isControlDown() && event.getCode() == KeyCode.L)  {
            controller.setScene(scene5ID);
        }
        if (event.isControlDown() && event.getCode() == KeyCode.Q)  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK)  {
                Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
                stage.close();
            }
            else    {
                alert.close();
            }
        }
    }

    @FXML
    public void exitApplication(ActionEvent event)  {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)  {
            Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
            stage.close();
        }
        else    {
            alert.close();
        }
    }
}
