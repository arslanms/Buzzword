package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    }

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void openHome(ActionEvent event) {
        controller.setScene(ParentController.scene2ID);
    }

    @FXML
    public void logout(ActionEvent event)   {
        controller.setScene(ParentController.scene1ID);
    }

    @FXML
    public void exitApplication(ActionEvent event)  {
        Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
        stage.close();
    }

    public Label getLevelSelectionModeTitle() {
        return levelSelectionModeTitle;
    }

    public void setLevelSelectionModeTitle(Label levelSelectionModeTitle) {
        this.levelSelectionModeTitle = levelSelectionModeTitle;
    }

    public void setLevelSelectionModeTitleText(String text) {
        levelSelectionModeTitle.setText(text);
    }
}
