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
    private static final int COUNTDOWNTIME = 6000;
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
    private int personalBest;
    private List<List> allOccurences;
    private List<List> allIndices;

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
    @FXML private TextField gameplayTextField;

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

//                generateLine(prevNode, circle);

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

    @FXML
    public void findInGridByTyping(KeyEvent event)  {
        String keyPressed = gameplayTextField.getText();
        String[][] matrix = controller.getLevelSelectController().getGrid().getGrid();


        if (keyPressed.length() == 1)   {

            char lastLetter = keyPressed.charAt(keyPressed.length() - 1);
            keyPressed = Character.toString(lastLetter);
            keyPressed = keyPressed.toUpperCase();
            visitedNodes = new boolean[16];

            allOccurences = new ArrayList<>();
            allIndices = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (matrix[i][j].equals(keyPressed))    {

                        int index = 4 * i + j;
                        List<Integer> intList = new ArrayList<>();
                        intList.add(index);
                        allIndices.add(intList);
                        visitedNodes[index] = true;

                        circles[index].setFill(Color.CORNFLOWERBLUE);
                        List<String> list = new ArrayList<>();
                        list.add(keyPressed);
                        allOccurences.add(list);
                    }
                }
            }

        }
        else if (event.getCode() == KeyCode.ENTER)  {
            String text = gameplayTextField.getText();
            if (text != null)   {
                gameplayTextField.clear();

                text = text.toUpperCase();

                Color c = Color.web("#505050");
                for (int i = 0; i < circles.length; i++)    {
                    circles[i].setFill(c);
                }

                Grid grid = controller.getLevelSelectController().getGrid();
                HashSet words = grid.getDuplicateCheck();
                int score = determineScore(text.length());

                if (words.contains(text) && !wordsGuessed.contains(text)) {
                    TableElement tableElement = new TableElement(text, score);
                    tabledata.add(tableElement);
                    System.out.println(true);
                    wordsGuessed.add(text);
                    totalScore += score;
                    totalPointsLabel.setText("Total Score: " + totalScore);
                }

            }
        }
        else if (keyPressed.length() > 1)   {

            char lastLetter = keyPressed.charAt(keyPressed.length() - 1);
            keyPressed = Character.toString(lastLetter);
            keyPressed = keyPressed.toUpperCase();

            List<Integer> markedForRemoval = new ArrayList<Integer>();

            for (int i = 0; i < allIndices.size(); i++)  {

                List<Integer> intList = allIndices.get(i);
                int currIndex = intList.get(intList.size() - 1);

                int neighbor = isNeighbor(currIndex, keyPressed, matrix);

                if (neighbor != -1 && !visitedNodes[neighbor])   {

                        List<String> strList = allOccurences.get(i);
                        strList.add(keyPressed);

                        intList.add(neighbor);

                        visitedNodes[neighbor] = true;

                }
                else    {
                    markedForRemoval.add(i);

                }
            }

            List[] allIndicesArr = allIndices.toArray(new List[allIndices.size()]);
            List[] allOccurencesArr = allOccurences.toArray(new List[allOccurences.size()]);

//            for (int i = 0; i < allIndicesArr.length; i++)  {
//                for (Object obj: allIndicesArr[i])   {
//                    int num = (Integer) obj;
//
//                    visitedNodes[num] = false;
//                }
//            }

            for (int i = 0; i < markedForRemoval.size(); i++)   {
                int removeIndex = markedForRemoval.get(i);

                List<Integer> list = allIndicesArr[removeIndex];

                for (int j = 0; j < list.size(); j++)   {
                    visitedNodes[list.get(j)] = false;
                }

                allIndicesArr[removeIndex] = null;
                allOccurencesArr[removeIndex] = null;
            }

            allIndices.clear();
            allOccurences.clear();

            for (int i = 0; i < allIndicesArr.length; i++)  {
                if (allIndicesArr[i] != null && allOccurencesArr[i] != null)    {
                    allIndices.add(allIndicesArr[i]);
                    allOccurences.add(allOccurencesArr[i]);
                }
            }

            Color c = Color.web("#505050");

            for (int i = 0; i < circles.length; i++)    {
                circles[i].setFill(c);
            }

            for (int i = 0; i < allIndices.size(); i++) {
                for (int j = 0; j < allIndices.get(i).size(); j++)  {
                    int index = (Integer) allIndices.get(i).get(j);
                    circles[index].setFill(Color.CORNFLOWERBLUE);
                }
            }

        }
        else if (keyPressed.length() == 0)  {
            for (int i = 0; i < circles.length; i++)    {
                Color c = Color.web("#505050");
                circles[i].setFill(c);
            }
        }

    }

    private int isNeighbor(int start, String neighbor, String[][] grid)   {
        int x = start / 4;
        int y = start % 4;

        if ((x - 1 >= 0) && (y - 1 >= 0) && grid[x-1][y-1].equals(neighbor) && !visitedNodes[4*(x-1) + (y-1)]) return 4*(x-1) + (y-1);
        else if ((x - 1 >= 0) && grid[x-1][y].equals(neighbor) && !visitedNodes[4*(x-1) + (y)]) return 4*(x-1) + (y);
        else if ((x - 1 >= 0) && (y + 1 <= 3) && grid[x-1][y+1].equals(neighbor) && !visitedNodes[4*(x-1) + (y+1)]) return 4*(x-1) + (y+1);

        else if ((y - 1 >= 0) && grid[x][y-1].equals(neighbor) && !visitedNodes[4*(x) + (y-1)]) return 4*(x) + (y-1);
        else if ((y + 1 <= 3) && grid[x][y+1].equals(neighbor) && !visitedNodes[4*(x) + (y+1)]) return 4*(x) + (y+1);

        else if ((x + 1 <= 3) && (y - 1 >= 0) && grid[x+1][y-1].equals(neighbor) && !visitedNodes[4*(x+1) + (y-1)]) return 4*(x+1) + (y-1);
        else if ((x + 1 <= 3) && grid[x+1][y].equals(neighbor) && !visitedNodes[4*(x+1) + (y)]) return 4*(x+1) + (y);
        else if ((x + 1 <= 3) && (y + 1 <= 3) && grid[x+1][y+1].equals(neighbor) && !visitedNodes[4*(x+1) + (y+1)]) return 4*(x+1) + (y+1);

        else return -1;
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

                                    String currentGameMode = controller.getGameMode();
                                    int currentLevel = player.getCurrentLevel();
                                    int[][] modes = data.getModes();
                                    int modeIndex = getModeIndex(currentGameMode);

                                    int highScore = modes[modeIndex][currentLevel - 1];
                                    personalBest = highScore;

                                    if (totalScore >= targetScore)  {
                                        System.out.println("You win!");
                                    }
                                    else    {
                                        System.out.println("You lose!");
                                    }


                                    if (totalScore > highScore) {
                                        try {
                                            System.out.println("You beat your highscore");
                                            modes[modeIndex][currentLevel - 1] = totalScore;

                                            if (totalScore >= targetScore && currentLevel < 8) {
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Level End");
        alert.setHeaderText("The timer has run out!");

        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(400, 400);

        HashSet<String> words = controller.getLevelSelectController().getGrid().getDuplicateCheck();
        String allWords = "All possible words: \n";

        for (String s: words)   {
            allWords += s + " ";
        }

        alert.setContentText("Your score: " + totalScore + "\tYour personal best: " + personalBest + "\n\n\n" + allWords);

        ButtonType replayButton = new ButtonType("Replay");
        ButtonType startButton = new ButtonType("Start Next");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);


        if (controller.getPlayer().getCurrentLevel() == 8 || totalScore < controller.getLevelSelectController().getGrid().getTargetScore())  {
            alert.getButtonTypes().setAll(replayButton, buttonTypeCancel);
        }
        else    {
            alert.getButtonTypes().setAll(replayButton, startButton, buttonTypeCancel);
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == replayButton){
            System.out.println("Replay clicked");

            controller.getLevelSelectController().getGrid().resetGrid();
            initLabels();

            controller.getLevelSelectController().getGrid().generateLetters();
            String[][] gridArray = controller.getLevelSelectController().getGrid().getGrid();

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    labels[4*i + j].setText(gridArray[i][j]);
                }
            }

            controller.getLevelSelectController().getGrid().setTargetScore(0);
            controller.getLevelSelectController().getGrid().displayWords();

            int targetScore = controller.getLevelSelectController().getGrid().getTargetScore() / (9 - controller.getPlayer().getCurrentLevel());
            controller.getLevelSelectController().getGrid().setTargetScore(targetScore);
            setTargetScoreLabelText("Target Score: " + targetScore);

            startTimer();
            timer.playFromStart();


        } else if (result.get() == startButton) {


            System.out.println("Start clicked");

            int currentLevel = controller.getPlayer().getCurrentLevel();
            currentLevel++;
            controller.getPlayer().setCurrentLevel(currentLevel);

            levelLabel.setText("Level " + currentLevel);

            controller.getLevelSelectController().getGrid().resetGrid();
            initLabels();

            controller.getLevelSelectController().getGrid().generateLetters();
            String[][] gridArray = controller.getLevelSelectController().getGrid().getGrid();

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    labels[4*i + j].setText(gridArray[i][j]);
                }
            }

            controller.getLevelSelectController().getGrid().setTargetScore(0);
            controller.getLevelSelectController().getGrid().displayWords();

            int targetScore = controller.getLevelSelectController().getGrid().getTargetScore() / (9 - controller.getPlayer().getCurrentLevel());
            controller.getLevelSelectController().getGrid().setTargetScore(targetScore);
            setTargetScoreLabelText("Target Score: " + targetScore);

            startTimer();
            timer.playFromStart();

        } else {
            System.out.println("Close clicked");
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
