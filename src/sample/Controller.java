package sample;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;


public class Controller {

    private int stageWidth = 825;
    private int stageHeight = 525;

    @FXML
    public ComboBox<String> modeComboBox;
    public TableView<TableElement> gameplayTable;
    public ObservableList<TableElement> tabledata;
    public TableColumn gameplayWordsColumn;
    public TableColumn gameplayPointsColumn;
    public Stage loginStage;
    public Stage profileStage;
    public Label levelSelectionModeTitle;
    public Label levelSelectNodeLabel1, levelSelectNodeLabel2, levelSelectNodeLabel3, levelSelectNodeLabel4,
            levelSelectNodeLabel5, levelSelectNodeLabel6, levelSelectNodeLabel7, levelSelectNodeLabel8;
    public ImageView levelSelectNodeLock1, levelSelectNodeLock2, levelSelectNodeLock3, levelSelectNodeLock4,
            levelSelectNodeLock5, levelSelectNodeLock6, levelSelectNodeLock7, levelSelectNodeLock8;
    public Label[] levelSelectLabels;
    public ImageView[] levelSelectLocks;
    public Circle levelSelectNodeCircle1;
    public Button exitButton;
    public TextField profileUsernameField, profilePasswordField, loginUsernameField, loginPasswordField;

    public void addModes()  {
        modeComboBox.getItems().clear();
        modeComboBox.getItems().addAll("Dictionary Words", "Science", "Places", "Famous People");
    }

    public void addTable()  {

        tabledata = FXCollections.observableArrayList();

        gameplayWordsColumn.setCellValueFactory(new PropertyValueFactory<TableElement, String>("word"));
        gameplayPointsColumn.setCellValueFactory(new PropertyValueFactory<TableElement, Integer>("points"));

        gameplayTable.setItems(tabledata);

        TableElement element1 = new TableElement("WAR", 10);
        TableElement element2 = new TableElement("RAW", 10);
        TableElement element3 = new TableElement("DRAW", 20);

        tabledata.addAll(element1, element2, element3);
    }

    @FXML
    public void openLoginDialog() throws IOException {

        loginStage = new Stage();

        String resource = "logindialog.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();

        loginStage.setTitle("Create Profile");

        int width = 300;
        int height = 300;

        Scene scene = new Scene(root, width, height);

        URL url = this.getClass().getResource("styles.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);

        loginStage.setScene(scene);
        loginStage.showAndWait();
    }

    @FXML
    public void openProfileDialog() throws IOException {

        profileStage = new Stage();

        String resource = "profiledialog.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();

        profileStage.setTitle("Create Profile");

        int width = 300;
        int height = 300;

        Scene scene = new Scene(root, width, height);

        URL url = this.getClass().getResource("styles.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);

        profileStage.setScene(scene);
        profileStage.showAndWait();
    }

    @FXML
    public void openLevelSelection(ActionEvent event) throws IOException   {

        if (modeComboBox.getSelectionModel().getSelectedItem() == null) {
            System.out.println("No mode selected.");
        } else {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("levelselection.fxml"));
            Parent parent = fxmlLoader.load();
            changeScene(parent, event, fxmlLoader);
            setLevelSelection(fxmlLoader.getController());

        }

    }

    @FXML
    public void createProfile(ActionEvent event) {

        String username = profileUsernameField.getText();
        String password = profilePasswordField.getText();

        if (username == null || password == null)   {
            System.out.println("no shit");
        }
        else {
            File file = new File(username + ".json");
            FileController fileController = new FileController();

            int[][] modes = new int[4][8];

            for (int i = 0; i < modes.length; i++)  {
                for (int j = 0; j < modes[i].length; j++)   {
                    modes[i][j] = -1;
                }
            }

            try {
                BuzzData data = new BuzzData(username, password, modes);
                fileController.saveData(data, file);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    public void closeWindow(ActionEvent event)   {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void openHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loggedin.fxml"));
        Parent parent = fxmlLoader.load();
        changeScene(parent, event, fxmlLoader);
        Controller controller = fxmlLoader.getController();
        modeComboBox = controller.modeComboBox;
        modeComboBox.getItems();
    }

    @FXML
    public void userLogin(ActionEvent event) throws IOException {
        FileController fileController = new FileController();
        BuzzData data = new BuzzData();
        boolean success = fileController.loadData(loginUsernameField.getText(), loginPasswordField.getText(), data);
        if (success)    {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loggedin.fxml"));
            Parent parent = fxmlLoader.load();
            changeScene(parent, event, fxmlLoader);
        }
        else {
            System.out.println("Cannot login");
        }
    }

    private void setLevelSelection(Controller controller)    {

        levelSelectionModeTitle = controller.levelSelectionModeTitle;

        String modeTitle = modeComboBox.getValue();
        levelSelectionModeTitle.setText(modeTitle);

        levelSelectNodeLabel1 = controller.levelSelectNodeLabel1;
        levelSelectNodeLabel2 = controller.levelSelectNodeLabel2;
        levelSelectNodeLabel3 = controller.levelSelectNodeLabel3;
        levelSelectNodeLabel4 = controller.levelSelectNodeLabel4;
        levelSelectNodeLabel5 = controller.levelSelectNodeLabel5;
        levelSelectNodeLabel6 = controller.levelSelectNodeLabel6;
        levelSelectNodeLabel7 = controller.levelSelectNodeLabel7;
        levelSelectNodeLabel8 = controller.levelSelectNodeLabel8;

        levelSelectLabels = new Label[8];

        levelSelectLabels[0] = levelSelectNodeLabel1;
        levelSelectLabels[1] = levelSelectNodeLabel2;
        levelSelectLabels[2] = levelSelectNodeLabel3;
        levelSelectLabels[3] = levelSelectNodeLabel4;
        levelSelectLabels[4] = levelSelectNodeLabel5;
        levelSelectLabels[5] = levelSelectNodeLabel6;
        levelSelectLabels[6] = levelSelectNodeLabel7;
        levelSelectLabels[7] = levelSelectNodeLabel8;

        levelSelectNodeLock1 = controller.levelSelectNodeLock1;
        levelSelectNodeLock2 = controller.levelSelectNodeLock2;
        levelSelectNodeLock3 = controller.levelSelectNodeLock3;
        levelSelectNodeLock4 = controller.levelSelectNodeLock4;
        levelSelectNodeLock5 = controller.levelSelectNodeLock5;
        levelSelectNodeLock6 = controller.levelSelectNodeLock6;
        levelSelectNodeLock7 = controller.levelSelectNodeLock7;
        levelSelectNodeLock8 = controller.levelSelectNodeLock8;

        levelSelectLocks = new ImageView[8];

        levelSelectLocks[0] = levelSelectNodeLock1;
        levelSelectLocks[1] = levelSelectNodeLock2;
        levelSelectLocks[2] = levelSelectNodeLock3;
        levelSelectLocks[3] = levelSelectNodeLock4;
        levelSelectLocks[4] = levelSelectNodeLock5;
        levelSelectLocks[5] = levelSelectNodeLock6;
        levelSelectLocks[6] = levelSelectNodeLock7;
        levelSelectLocks[7] = levelSelectNodeLock8;

        for (int i = 0; i < 1; i++) {
            levelSelectLabels[i].setText(Integer.toString(i+1));
            levelSelectLocks[i].setVisible(false);
        }

        for (int i = 1; i < 8; i++) {
            levelSelectLabels[i].setText("");
            levelSelectLocks[i].setVisible(true);
        }




    }

    private void changeScene(Parent parent, ActionEvent event, FXMLLoader fxmlLoader)  {
        Scene scene = new Scene(parent, stageWidth, stageHeight);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setTitle("BuzzWord");

        URL url = this.getClass().getResource("styles.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);

        appStage.setScene(scene);
        appStage.show();
    }

    private String encryptPassword(String password) {
        StringBuffer passwordBuffer = new StringBuffer(password);

        for (int i = 0; i < password.length(); i++) {
            int passwordChar = password.charAt(i);
            passwordChar *= 9;

            passwordBuffer.setCharAt(i, (char)passwordChar);
        }

        return passwordBuffer.toString();
    }

}
