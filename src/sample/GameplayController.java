package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by arslan on 11/27/16.
 */
public class GameplayController implements ParentController {

    private  MainController controller;
    private Label[] labels;
    private Circle[] circles;
    private boolean isPaused;
    private static final int COUNTDOWNTIME = 60;
    private Timeline timer;
    private static int seconds;
    private List<String> selectedLetters;
    private boolean[] visitedNodes;
    private Circle prevNode;
    private ObservableList<TableElement> tabledata;
    private Set<String> wordsGuessed;
    private static int totalScore;
    private Player player;
    private BuzzData data;
    private Line[] nodeLines;
    private int nodeLineIndex;

    @FXML
    private Label gameplayNodeLabel1, gameplayNodeLabel2, gameplayNodeLabel3, gameplayNodeLabel4, gameplayNodeLabel5,
            gameplayNodeLabel6, gameplayNodeLabel7, gameplayNodeLabel8, gameplayNodeLabel9, gameplayNodeLabel10,
            gameplayNodeLabel11, gameplayNodeLabel12, gameplayNodeLabel13, gameplayNodeLabel14, gameplayNodeLabel15,
            gameplayNodeLabel16;
    @FXML
    private Circle gameplayNode1, gameplayNode2, gameplayNode3, gameplayNode4, gameplayNode5, gameplayNode6,
            gameplayNode7, gameplayNode8, gameplayNode9, gameplayNode10, gameplayNode11, gameplayNode12, gameplayNode13,
            gameplayNode14, gameplayNode15, gameplayNode16;
    @FXML private Label gameplayModeLabel;
    @FXML private Label levelLabel;
    @FXML private Label targetScoreLabel;
    @FXML private Button gameplayLogout;
    @FXML private Label gameplayTimerLabel;
    @FXML private Pane gameplayNodePane;
    @FXML private TableView<TableElement> gameplayTable;
    @FXML private TableColumn gameplayWordsColumn;
    @FXML private TableColumn gameplayPointsColumn;
    @FXML private Label totalPointsLabel;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void logout(ActionEvent event)   {
        controller.setScene(ParentController.scene1ID);
        controller.setPlayer(null);
        controller.setData(null);
        timer.stop();
    }

    @FXML
    public void openHome(ActionEvent event)   {
        controller.setScene(ParentController.scene2ID);
        timer.stop();
    }

    @FXML
    public void exitApplication(ActionEvent event)  {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to exit?");

        isPaused = false;
        playOrPause();

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)  {
            Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
            stage.close();
        }
        else    {
            alert.close();
            isPaused = true;
            playOrPause();
        }
    }

    @FXML
    public void playOrPause()  {

        if (!isPaused)  {
            for (int i = 0; i < labels.length; i++)    {
                labels[i].setVisible(false);
            }
            isPaused = true;
            timer.pause();
        }
        else    {
            for (int i = 0; i < labels.length; i++) {
                labels[i].setVisible(true);
            }
            isPaused = false;
            timer.play();
        }
    }

    @FXML
    public void nodeDragDetected (MouseEvent event)  {

        if(!isPaused) {

            selectedLetters = new ArrayList<String>();
            visitedNodes = new boolean[17];
            StackPane clickedNode = (StackPane) event.getSource();
            Circle circle = (Circle) clickedNode.getChildren().get(0);
            int index = -1;
            for (int i = 0; i < circles.length; i++) {
                if (circles[i] == circle) {
                    index = i;
                }
            }

            prevNode = circle;

            if (index != -1 && !visitedNodes[index]) {
                Label letter = (Label) clickedNode.getChildren().get(1);
                selectedLetters.add(letter.getText());
                ((Node) event.getSource()).startFullDrag();
                visitedNodes[index] = true;
                circle.setFill(Color.CORNFLOWERBLUE);

            }
        }

    }

    @FXML
    public void nodeDragged(MouseEvent event)   {

        if(!isPaused) {

            StackPane clickedNode = (StackPane) event.getSource();
            Circle circle = (Circle) clickedNode.getChildren().get(0);
            int index = -1;
            for (int i = 0; i < circles.length; i++) {
                if (circles[i] == circle) {
                    index = i;
                }
            }

            boolean inRange = nodeInRange(prevNode, index);

            if (index != -1 && !visitedNodes[index] && inRange) {
                Label letter = (Label) clickedNode.getChildren().get(1);
                selectedLetters.add(letter.getText());
                visitedNodes[index] = true;
                circle.setFill(Color.CORNFLOWERBLUE);

                generateLine(prevNode, circle);

                prevNode = circle;
            }
        }


    }

    @FXML
    public void nodeReleased(MouseEvent event)   {

        if(!isPaused) {

            StringBuffer sb = new StringBuffer();
            for (String s : selectedLetters) {
                sb.append(s);
            }

            String word = sb.toString();

            for (int i = 0; i < visitedNodes.length; i++) {
                visitedNodes[i] = false;
            }

            Color c = Color.web("#505050");
            for (int i = 0; i < circles.length; i++) {
                circles[i].setFill(c);
            }

            for (int i = 0; i < nodeLines.length; i++) {
                nodeLines[i] = new Line();
            }

            prevNode = null;

            Grid grid = controller.getLevelSelectController().getGrid();
            HashSet words = grid.getDuplicateCheck();
            int score = determineScore(word.length());

            if (words.contains(word) && !wordsGuessed.contains(word)) {
                TableElement tableElement = new TableElement(word, score);
                tabledata.add(tableElement);
                System.out.println(word);
                wordsGuessed.add(word);
                totalScore += score;
                totalPointsLabel.setText("Total Score: " + totalScore);
            }
        }
    }

    @FXML
    public void keyPressEvent(KeyEvent event)   {
        if (event.isControlDown() && event.getCode() == KeyCode.L)  {
            controller.setScene(ParentController.scene1ID);
            controller.setPlayer(null);
            controller.setData(null);
            timer.stop();
        }
        if (event.isControlDown() && event.getCode() == KeyCode.Q)  {
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
    }

    private boolean nodeInRange(Circle prevNode, int c2)  {
        if (c2 == -1)   {
            return false;
        }

        int prevNodeIndex = -1;
        for (int i = 0; i < circles.length; i++)    {
            if (circles[i] == prevNode)   {
                prevNodeIndex = i;
            }
        }

        if (prevNodeIndex == -1) return false;

        int[][] nodeMatrix = new int[4][4];

        for (int i = 0; i < nodeMatrix.length; i++)  {
            for (int j = 0; j < nodeMatrix[i].length; j++)  {
                nodeMatrix[i][j] = 255;
            }
        }

        int prevNodeIndex_X = prevNodeIndex / 4;
        int prevNodeIndex_Y = prevNodeIndex % 4;
        int c2_X = c2 / 4;
        int c2_Y = c2 % 4;

        nodeMatrix[prevNodeIndex_X][prevNodeIndex_Y] = prevNodeIndex;
        nodeMatrix[c2_X][c2_Y] = c2;

        if (prevNodeIndex_X - 1 >= 0 && prevNodeIndex_Y - 1 >= 0 && nodeMatrix[prevNodeIndex_X - 1][prevNodeIndex_Y - 1] == c2) return true;
        else if (prevNodeIndex_X - 1 >= 0 && prevNodeIndex_Y >= 0 && nodeMatrix[prevNodeIndex_X - 1][prevNodeIndex_Y] == c2) return true;
        else if (prevNodeIndex_X - 1 >= 0 && prevNodeIndex_Y + 1 < 4 && nodeMatrix[prevNodeIndex_X - 1][prevNodeIndex_Y + 1] == c2) return true;

        else if (prevNodeIndex_X >= 0 && prevNodeIndex_Y - 1 >= 0 && nodeMatrix[prevNodeIndex_X][prevNodeIndex_Y - 1] == c2) return true;
        else if (prevNodeIndex_X >= 0 && prevNodeIndex_Y + 1 < 4 && nodeMatrix[prevNodeIndex_X][prevNodeIndex_Y + 1] == c2) return true;

        else if (prevNodeIndex_X + 1 < 4 && prevNodeIndex_Y - 1 >= 0 && nodeMatrix[prevNodeIndex_X + 1][prevNodeIndex_Y - 1] == c2) return true;
        else if (prevNodeIndex_X + 1 < 4 && prevNodeIndex_Y >= 0 && nodeMatrix[prevNodeIndex_X + 1][prevNodeIndex_Y] == c2) return true;
        else if (prevNodeIndex_X + 1 < 4 && prevNodeIndex_Y + 1 < 4 && nodeMatrix[prevNodeIndex_X + 1][prevNodeIndex_Y + 1] == c2) return true;

        else return false;

    }

    private void generateLine(Circle prevNode, Circle currNode) {
        if (prevNode == null) return;

        Line line = new Line(prevNode.getCenterX(), prevNode.getCenterY(), currNode.getCenterX(), currNode.getCenterY());

        nodeLines[nodeLineIndex] = line;
        nodeLineIndex++;

    }

    public void initLabels()   {
        labels = new Label[16];

        labels[0] = gameplayNodeLabel1;
        labels[1] = gameplayNodeLabel2;
        labels[2] = gameplayNodeLabel3;
        labels[3] = gameplayNodeLabel4;
        labels[4] = gameplayNodeLabel5;
        labels[5] = gameplayNodeLabel6;
        labels[6] = gameplayNodeLabel7;
        labels[7] = gameplayNodeLabel8;
        labels[8] = gameplayNodeLabel9;
        labels[9] = gameplayNodeLabel10;
        labels[10] = gameplayNodeLabel11;
        labels[11] = gameplayNodeLabel12;
        labels[12] = gameplayNodeLabel13;
        labels[13] = gameplayNodeLabel14;
        labels[14] = gameplayNodeLabel15;
        labels[15] = gameplayNodeLabel16;

        circles = new Circle[16];

        circles[0] = gameplayNode1;
        circles[1] = gameplayNode2;
        circles[2] = gameplayNode3;
        circles[3] = gameplayNode4;
        circles[4] = gameplayNode5;
        circles[5] = gameplayNode6;
        circles[6] = gameplayNode7;
        circles[7] = gameplayNode8;
        circles[8] = gameplayNode9;
        circles[9] = gameplayNode10;
        circles[10] = gameplayNode11;
        circles[11] = gameplayNode12;
        circles[12] = gameplayNode13;
        circles[13] = gameplayNode14;
        circles[14] = gameplayNode15;
        circles[15] = gameplayNode16;


        tabledata = FXCollections.observableArrayList();

        gameplayWordsColumn.setCellValueFactory(new PropertyValueFactory<TableElement, String>("word"));
        gameplayPointsColumn.setCellValueFactory(new PropertyValueFactory<TableElement, Integer>("points"));

        gameplayTable.setItems(tabledata);

        wordsGuessed = new HashSet<String>();

        totalScore = 0;
        totalPointsLabel.setText("Total Score: " + totalScore);

        player = controller.getPlayer();
        data = controller.getData();

        nodeLines = new Line[42];

        for (int i = 0; i < nodeLines.length; i++)  {
            nodeLines[i] = new Line();
            nodeLines[i].setVisible(false);
        }

        gameplayNodePane.getChildren().addAll(nodeLines);

        nodeLineIndex = 0;

    }

    public void startTimer()    {
        seconds = COUNTDOWNTIME;
        gameplayTimerLabel.setText("Time left: " + seconds + "s");
        timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1.0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                seconds--;
                                gameplayTimerLabel.setText("Time left: " + seconds + "s");
                                if (seconds <= 0)   {
                                    timer.stop();

                                    int targetScore = controller.getLevelSelectController().getGrid().getTargetScore();

                                    if (totalScore >= targetScore)  {
                                        System.out.println("You win!");
                                    }
                                    else    {
                                        System.out.println("You lose!");
                                    }

                                    String currentGameMode = controller.getGameMode();
                                    int currentLevel = player.getCurrentLevel();
                                    int[][] modes = data.getModes();
                                    int modeIndex = getModeIndex(currentGameMode);

                                    int highScore = modes[modeIndex][currentLevel - 1];

                                    if (totalScore > highScore) {
                                        try {
                                            System.out.println("You beat your highscore");
                                            modes[modeIndex][currentLevel - 1] = totalScore;

                                            if (currentLevel < 8)   {
                                                modes[modeIndex][currentLevel] = 0;
                                            }

                                            data.setModes(modes);
                                            File file = new File(data.getUsername() + ".json");
                                            controller.getFileController().saveData(data, file);

                                            int[] currModeArray = modes[modeIndex];
                                            Label[] nodeLabels = controller.getLevelSelectController().getNodeLabels();
                                            ImageView[] nodeLocks = controller.getLevelSelectController().getNodeLocks();

                                            for (int i = 0; i < currModeArray.length; i++) {
                                                if (currModeArray[i] == -1) {
                                                    nodeLabels[i].setText("");
                                                    nodeLocks[i].setVisible(true);
                                                } else {
                                                    nodeLabels[i].setText(Integer.toString(i + 1));
                                                    nodeLocks[i].setVisible(false);
                                                }
                                            }

                                        } catch (FileNotFoundException e)   {
                                            System.out.println("File was not found");
                                        }
                                    }
                                    else    {
                                        System.out.println("You didn't beat your high score.");
                                    }

                                    Platform.runLater(() -> timerEnded());
                                }
                            }
                        })
        );
    }

    public void timerEnded()    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time Ended!");
        alert.setContentText("The gameplay time has ended.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)  {
            alert.close();
            controller.setScene(scene3ID);
        }

    }

    private int getModeIndex(String mode){
        if (mode.equals("Dictionary Words"))    {
            return 0;
        }
        else if (mode.equals("Science"))   {
            return 1;
        }
        else if (mode.equals("Places")) {
            return 2;
        }
        else if (mode.equals("Names"))  {
            return 3;
        }
        else    {
            return -1;
        }
    }

    private int determineScore(int length) {

        if (length < 3)   {
            return 0;
        }
        else if (length >= 3 && length <= 4)  {
            return 1;
        }
        else if (length == 5)  {
            return 2;
        }
        else if (length == 6)  {
            return 3;
        }
        else if (length == 7)  {
            return 4;
        }
        else if (length >= 8)  {
            return 5;
        }
        else    {
            return -1;
        }

    }

    public Label getGameplayModeLabel() {
        return gameplayModeLabel;
    }

    public void setGameplayModeLabel(Label gameplayModeLabel) {
        this.gameplayModeLabel = gameplayModeLabel;
    }

    public void setGameplayModeLabelText(String text)   {
        gameplayModeLabel.setText(text);
    }

    public void setLevelLabelText(String text)  {
        levelLabel.setText(text);
    }

    public Label[] getLabels() {
        return labels;
    }

    public void setLabels(Label[] labels) {
        this.labels = labels;
    }

    public void setTargetScoreLabelText(String text)    {
        targetScoreLabel.setText(text);
    }

    public void setGameplayLogoutText(String text)  {
        gameplayLogout.setText(text);
    }

    public Timeline getTimer() {
        return timer;
    }

    public void setTimer(Timeline timer) {
        this.timer = timer;
    }
}
