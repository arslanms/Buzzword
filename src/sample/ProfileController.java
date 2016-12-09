package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by arslan on 12/8/16.
 */
public class ProfileController implements ParentController {

    MainController controller;

    @FXML
    private Button profileLogout;
    @FXML private Label profileUsernameLabel, mode1Label, mode2Label, mode3Label, mode4Label;

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
    public void openEditProfile(ActionEvent event)  {

    }

    public Button getProfileLogout() {
        return profileLogout;
    }

    public void setProfileLogout(Button profileLogout) {
        this.profileLogout = profileLogout;
    }

    public Label getProfileUsernameLabel() {
        return profileUsernameLabel;
    }

    public void setProfileUsernameLabel(Label profileUsernameLabel) {
        this.profileUsernameLabel = profileUsernameLabel;
    }

    public Label getMode1Label() {
        return mode1Label;
    }

    public void setMode1Label(Label mode1Label) {
        this.mode1Label = mode1Label;
    }

    public Label getMode2Label() {
        return mode2Label;
    }

    public void setMode2Label(Label mode2Label) {
        this.mode2Label = mode2Label;
    }

    public Label getMode3Label() {
        return mode3Label;
    }

    public void setMode3Label(Label mode3Label) {
        this.mode3Label = mode3Label;
    }

    public Label getMode4Label() {
        return mode4Label;
    }

    public void setMode4Label(Label mode4Label) {
        this.mode4Label = mode4Label;
    }
}
