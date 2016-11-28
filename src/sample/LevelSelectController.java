package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by arslan on 11/27/16.
 */
public class LevelSelectController implements ParentController, Initializable {

    private MainController controller;

    @FXML
    private Label levelSelectionModeTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelSelectionModeTitle.setText(controller.getGameMode());
    }

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void openHome(ActionEvent event) {

    }

    @FXML
    public void exitApplication(ActionEvent event)  {

    }


}
