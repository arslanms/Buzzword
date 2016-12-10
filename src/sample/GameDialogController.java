package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by arslan on 12/9/16.
 */
public class GameDialogController {

    MainController controller;
    GameDialog gameDialog;
    Grid grid;
    String gridResource;
    private String dictionaryWords = "dictionary-yawl.txt";
    private String scienceWords = "science.txt";
    private String placeWords = "places.txt";
    private String nameWords = "names.txt";

    @FXML private Label currentScoreLabel;
    @FXML private Label bestScoreLabel;
    @FXML private Label listOfPossibleWordsLabel;
    @FXML private Button startNextLevelButton;

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

        int currentLevel = controller.getPlayer().getCurrentLevel();
        currentLevel++;
        controller.getPlayer().setCurrentLevel(currentLevel);

        controller.getGameplayController().setGameplayModeLabelText(controller.getGameMode());
        controller.getGameplayController().setLevelLabelText("Level " + currentLevel);

        if (controller.getGameMode().equals("Dictionary Words"))    {
            gridResource = dictionaryWords;
        }
        else if (controller.getGameMode().equals("Science"))    {
            gridResource = scienceWords;
        }
        else if (controller.getGameMode().equals("Places")) {
            gridResource = placeWords;
        }
        else {
            gridResource = nameWords;
        }

        grid = new Grid(gridResource);
        grid.getDictionary().readDictionary();

        controller.getGameplayController().initLabels();

        String[][] gridArray = grid.getGrid();
        Label[] labels = controller.getGameplayController().getLabels();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                labels[4*i + j].setText(gridArray[i][j]);
            }
        }

        grid.displayWords();

        int targetScore = grid.getTargetScore() / (9 - controller.getPlayer().getCurrentLevel());
        grid.setTargetScore(targetScore);

        controller.getGameplayController().setTargetScoreLabelText("Target Score: " + targetScore);

        controller.getGameplayController().setGameplayLogoutText(controller.getData().getUsername());

        controller.getGameplayController().startTimer();
        controller.getGameplayController().getTimer().playFromStart();

        gameDialog.close();

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

    public Button getStartNextLevelButton() {
        return startNextLevelButton;
    }

    public void setStartNextLevelButton(Button startNextLevelButton) {
        this.startNextLevelButton = startNextLevelButton;
    }
}
