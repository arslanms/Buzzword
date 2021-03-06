package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by arslan on 12/8/16.
 */
public class HelpController implements ParentController {

    MainController controller;

    @FXML
    private Button helpLogout;
    @FXML private Text helpText;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void logout(ActionEvent event)   {
        controller.setScene(ParentController.scene1ID);
        controller.setPlayer(null);
        controller.setData(null);
    }

    @FXML
    public void openHome(ActionEvent event) {
        controller.setScene(scene2ID);
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

    @FXML
    public void keyPressEvent(KeyEvent event)   {
        if (event.isControlDown() && event.getCode() == KeyCode.L)  {
            controller.setScene(ParentController.scene1ID);
            controller.setPlayer(null);
            controller.setData(null);
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

    public Button getHelpLogout() {
        return helpLogout;
    }

    public void setHelpLogout(Button helpLogout) {
        this.helpLogout = helpLogout;
    }

    public Text getHelpText() {
        return helpText;
    }

    public void setHelpText(Text helpText) {
        this.helpText = helpText;
    }
}
