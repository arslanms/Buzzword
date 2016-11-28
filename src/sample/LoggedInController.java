package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

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
        controller.setScene("levelselection");
    }

    @FXML
    public void logout(ActionEvent event)   {

    }

    @FXML
    public void exitApplication(ActionEvent event)  {

    }

}
