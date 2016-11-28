package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by arslan on 11/27/16.
 */
public class LoggedInController implements ParentController, Initializable {

    private MainController controller;

    @FXML
    private ComboBox<String> modeComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addModes();
    }

    private void addModes()  {
        modeComboBox.getItems().clear();
        modeComboBox.getItems().addAll("Dictionary Words", "Science", "Places", "Famous People");
    }

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void openLevelSelection(ActionEvent event)   {
        controller.setGameMode(modeComboBox.getValue());
        controller.getLevelSelectController().setLevelSelectionModeTitleText(modeComboBox.getValue());
        controller.setScene(ParentController.scene3ID);
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

}
