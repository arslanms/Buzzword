package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by arslan on 11/27/16.
 */
public class GameplayController implements ParentController {

    private  MainController controller;
    private Label[] labels;
    private boolean isPaused;

    @FXML
    private Label gameplayNodeLabel1, gameplayNodeLabel2, gameplayNodeLabel3, gameplayNodeLabel4, gameplayNodeLabel5,
            gameplayNodeLabel6, gameplayNodeLabel7, gameplayNodeLabel8, gameplayNodeLabel9, gameplayNodeLabel10,
            gameplayNodeLabel11, gameplayNodeLabel12, gameplayNodeLabel13, gameplayNodeLabel14, gameplayNodeLabel15,
            gameplayNodeLabel16;
    @FXML private Label gameplayModeLabel;
    @FXML private Label levelLabel;


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

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)  {
            Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
            stage.close();
        }
        else    {
            alert.close();
        }
    }

    @FXML
    public void playOrPause(ActionEvent event)  {
        initLabels();
        if (!isPaused)  {
            for (int i = 0; i < labels.length; i++)    {
                labels[i].setVisible(false);
            }
            isPaused = true;
        }
        else    {
            for (int i = 0; i < labels.length; i++) {
                labels[i].setVisible(true);
            }
            isPaused = false;
        }
    }

    private void initLabels()   {
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
}
