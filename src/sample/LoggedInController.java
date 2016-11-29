package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by arslan on 11/27/16.
 */
public class LoggedInController implements ParentController, Initializable {

    private MainController controller;

    @FXML
    private ComboBox<String> modeComboBox;
    @FXML private Button loggedInLogout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addModes();
    }

    private void addModes()  {
        modeComboBox.getItems().clear();
        modeComboBox.getItems().addAll("Dictionary Words", "Science", "Places", "Names");
    }

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void openLevelSelection(ActionEvent event)   {

        if (modeComboBox.getValue() != null) {

            controller.setGameMode(modeComboBox.getValue());
            controller.getLevelSelectController().setLevelSelectionModeTitleText(modeComboBox.getValue());

            controller.getLevelSelectController().initNodeArrays();

            Label[] nodeLabels = controller.getLevelSelectController().getNodeLabels();
            ImageView[] nodeLocks = controller.getLevelSelectController().getNodeLocks();

            int gameModeIndex = modeComboBox.getSelectionModel().getSelectedIndex();

            int[] modeArray = controller.getData().getModes()[gameModeIndex];

            for (int i = 0; i < modeArray.length; i++) {
                if (modeArray[i] == -1) {
                    nodeLabels[i].setText("");
                    nodeLocks[i].setVisible(true);
                } else {
                    nodeLabels[i].setText(Integer.toString(i + 1));
                    nodeLocks[i].setVisible(false);
                }
            }

            controller.getLevelSelectController().setLevelSelectionLogoutText((controller.getData().getUsername()));
            controller.setScene(ParentController.scene3ID);
        }
        else {
            System.out.println("Mode was not selected.");
        }
    }

    @FXML
    public void logout(ActionEvent event)   {
        controller.setScene(ParentController.scene1ID);
        controller.setPlayer(null);
        controller.setData(null);
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

    public void setLoggedInLogoutText(String text)  {
        loggedInLogout.setText(text);
    }

}
