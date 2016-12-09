package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    @FXML
    public void openProfile(ActionEvent event)  {
        controller.getProfileController().getProfileLogout().setText(controller.getData().getUsername());
        controller.getProfileController().getProfileUsernameLabel().setText("Username: " + controller.getData().getUsername());

        int[][] modes = controller.getData().getModes();

        String modes1str = "Dictionary Words\n";

        for (int i = 0; i < modes[0].length; i++)   {
            modes1str += "Level " + (i+1) +": " + ((modes[0][i] == -1) ? "N/A":modes[0][i]) + "\n";
        }

        controller.getProfileController().getMode1Label().setText(modes1str);

        String modes2str = "Science\n";

        for (int i = 0; i < modes[1].length; i++)   {
            modes2str += "Level " + (i+1) +": " + ((modes[1][i] == -1) ? "N/A":modes[1][i]) + "\n";
        }

        controller.getProfileController().getMode2Label().setText(modes2str);

        String modes3str = "Places\n";

        for (int i = 0; i < modes[2].length; i++)   {
            modes3str += "Level " + (i+1) +": " + ((modes[2][i] == -1) ? "N/A":modes[2][i]) + "\n";
        }

        controller.getProfileController().getMode3Label().setText(modes3str);

        String modes4str = "Names\n";

        for (int i = 0; i < modes[3].length; i++)   {
            modes4str += "Level " + (i+1) +": " + ((modes[3][i] == -1) ? "N/A":modes[3][i]) + "\n";
        }

        controller.getProfileController().getMode4Label().setText(modes4str);

        controller.setScene(scene8ID);
    }

    @FXML
    public void openHelp(ActionEvent event) {
        controller.getHelpController().getHelpLogout().setText(controller.getData().getUsername());
        controller.setScene(scene7ID);
    }

    @FXML
    public void keyPressEvents(KeyEvent event)  {
        if (event.isControlDown() && event.getCode() == KeyCode.L)  {
            controller.setScene(ParentController.scene1ID);
            controller.setPlayer(null);
            controller.setData(null);
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


    public void setLoggedInLogoutText(String text)  {
        loggedInLogout.setText(text);
    }

}
