package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by arslan on 11/27/16.
 */
public class LevelSelectController implements ParentController, Initializable {

    private MainController controller;
    private Label[] nodeLabels;
    private ImageView[] nodeLocks;
    private Grid grid;

    private String dictionaryWords = "dictionary-yawl.txt";
    private String scienceWords = "science.txt";
    private String placeWords = "places.txt";
    private String nameWords = "names.txt";
    private String gridResource;

    @FXML
    private Label levelSelectionModeTitle;
    public Label levelSelectNodeLabel1, levelSelectNodeLabel2, levelSelectNodeLabel3, levelSelectNodeLabel4,
            levelSelectNodeLabel5, levelSelectNodeLabel6, levelSelectNodeLabel7, levelSelectNodeLabel8;
    public ImageView levelSelectNodeLock1, levelSelectNodeLock2, levelSelectNodeLock3, levelSelectNodeLock4,
            levelSelectNodeLock5, levelSelectNodeLock6, levelSelectNodeLock7, levelSelectNodeLock8;
    @FXML private Button levelSelectionLogout;


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
        controller.setPlayer(null);
        controller.setData(null);
        controller.setScene(ParentController.scene1ID);
    }

    @FXML
    public void goToGameplay(MouseEvent event) {
        StackPane clickedLabel = (StackPane) event.getSource();
        ImageView lock = (ImageView) clickedLabel.getChildren().get(2);
        if (lock.isVisible())   {
            System.out.println("Level is locked.");
        }
        else {
            Label level = (Label) clickedLabel.getChildren().get(1);
            int clickedLevel = Integer.parseInt(level.getText());
            System.out.println("Level clicked: " + clickedLevel);

            controller.getPlayer().setCurrentLevel(clickedLevel);

            controller.getGameplayController().setGameplayModeLabelText(controller.getGameMode());
            controller.getGameplayController().setLevelLabelText("Level " + clickedLevel);

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

            controller.setScene(ParentController.scene4ID);
        }
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

    public Label getLevelSelectionModeTitle() {
        return levelSelectionModeTitle;
    }

    public void setLevelSelectionModeTitle(Label levelSelectionModeTitle) {
        this.levelSelectionModeTitle = levelSelectionModeTitle;
    }

    public void setLevelSelectionModeTitleText(String text) {
        levelSelectionModeTitle.setText(text);
    }

    public void initNodeArrays()    {
        nodeLabels = new Label[8];
        nodeLocks = new ImageView[8];

        nodeLabels[0] = levelSelectNodeLabel1;
        nodeLabels[1] = levelSelectNodeLabel2;
        nodeLabels[2] = levelSelectNodeLabel3;
        nodeLabels[3] = levelSelectNodeLabel4;
        nodeLabels[4] = levelSelectNodeLabel5;
        nodeLabels[5] = levelSelectNodeLabel6;
        nodeLabels[6] = levelSelectNodeLabel7;
        nodeLabels[7] = levelSelectNodeLabel8;

        nodeLocks[0] = levelSelectNodeLock1;
        nodeLocks[1] = levelSelectNodeLock2;
        nodeLocks[2] = levelSelectNodeLock3;
        nodeLocks[3] = levelSelectNodeLock4;
        nodeLocks[4] = levelSelectNodeLock5;
        nodeLocks[5] = levelSelectNodeLock6;
        nodeLocks[6] = levelSelectNodeLock7;
        nodeLocks[7] = levelSelectNodeLock8;
    }

    public Label[] getNodeLabels() {
        return nodeLabels;
    }

    public void setNodeLabels(Label[] nodeLabels) {
        this.nodeLabels = nodeLabels;
    }

    public ImageView[] getNodeLocks() {
        return nodeLocks;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setNodeLocks(ImageView[] nodeLocks) {
        this.nodeLocks = nodeLocks;
    }

    public void setLevelSelectionLogoutText(String text)    {
        levelSelectionLogout.setText(text);
    }
}
