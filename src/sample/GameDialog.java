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
    GameDialogController gameDialogController;

    public GameDialog(int currentScore, MainController controller)  {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
