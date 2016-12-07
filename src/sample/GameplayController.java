package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
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
    private Set<String> selectedLetters;

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

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void logout(ActionEvent event)   {
        controller.setScene(ParentController.scene1ID);
        controller.setPlayer(null);
        controller.setData(null);
    }

    @FXML
    public void openHome(ActionEvent event)   {
        controller.setScene(ParentController.scene2ID);
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
        initLabels();
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
        selectedLetters = new LinkedHashSet<String>();
        StackPane clickedNode = (StackPane) event.getSource();
        Label letter = (Label) clickedNode.getChildren().get(1);
        selectedLetters.add(letter.getText());
        ((Node) event.getSource()).startFullDrag();

        Circle circle = (Circle) clickedNode.getChildren().get(0);
        circle.setFill(Color.CORNFLOWERBLUE);
    }

    @FXML
    public void nodeDragged(MouseEvent event)   {
        StackPane clickedNode = (StackPane) event.getSource();
        Label letter = (Label) clickedNode.getChildren().get(1);
        selectedLetters.add(letter.getText());

        Circle circle = (Circle) clickedNode.getChildren().get(0);
        circle.setFill(Color.CORNFLOWERBLUE);

    }

    @FXML
    public void nodeReleased(MouseEvent event)   {
        StringBuffer sb = new StringBuffer();
        for (String s : selectedLetters)    {
            sb.append(s);
        }
        System.out.println(sb.toString());

        for (int i = 0; i < circles.length; i++)    {
            Color c = Color.web("#505050");
            circles[i].setFill(c);
        }
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
        circles[11] = gameplayNode11;
        circles[12] = gameplayNode12;
        circles[13] = gameplayNode13;
        circles[14] = gameplayNode14;
        circles[15] = gameplayNode15;
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
