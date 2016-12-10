package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by arslan on 12/9/16.
 */
public class GameDialog extends Stage {

    MainController controller;
    int currentScore;
    int targetScore;
    GameDialogController gameDialogController;

    public GameDialog(int currentScore, int targetScore, MainController controller)  {
        this.controller = controller;
        this.currentScore = currentScore;


    }

    private void setupStage()   {
        try {

            String resource = "gamedialog.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            Parent scene = fxmlLoader.load();

            gameDialogController = fxmlLoader.getController();

            controller.setGameDialogController(gameDialogController);
            gameDialogController.setController(controller);
            gameDialogController.setGameDialog(this);

            Scene dialogScene = new Scene(scene, 300, 300);
            setScene(dialogScene);
            setWidth(300);
            setHeight(300);

            if (currentScore < targetScore)  {
                gameDialogController.getStartNextLevelButton().setVisible(false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainController getController() {
        return controller;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public GameDialogController getGameDialogController() {
        return gameDialogController;
    }

    public void setGameDialogController(GameDialogController gameDialogController) {
        this.gameDialogController = gameDialogController;
    }
}
