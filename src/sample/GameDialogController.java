package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by arslan on 12/9/16.
 */
public class GameDialogController {

    MainController controller;
    GameDialog gameDialog;

    @FXML private Label currentScoreLabel;
    @FXML private Label bestScoreLabel;
    @FXML private Label listOfPossibleWordsLabel;

    @FXML
    public void exitDialog(ActionEvent event)   {
        gameDialog.close();
        controller.setScene(ParentController.scene3ID);
    }

    @FXML
    public void replayLevel(ActionEvent event)  {

    }

    @FXML
    public void startNextLevel(ActionEvent event)   {

    }

    @FXML
    public void cancelDialog(ActionEvent event) {
        gameDialog.close();
        controller.setScene(ParentController.scene3ID);
    }

    public MainController getController() {
        return controller;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public GameDialog getGameDialog() {
        return gameDialog;
    }

    public void setGameDialog(GameDialog gameDialog) {
        this.gameDialog = gameDialog;
    }
}
