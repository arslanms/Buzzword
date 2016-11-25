package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.net.URL;


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
    public Label levelSelectionModeTitle;

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

        loginStage.setTitle("Login");

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
    public void openLevelSelection(ActionEvent event) throws IOException   {

        if (modeComboBox.getSelectionModel().getSelectedItem() == null) {
            System.out.println("No mode selected.");
        } else {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("levelselection.fxml"));
            Parent parent = fxmlLoader.load();
            changeScene(parent, event);
            setLevelSelection(fxmlLoader.getController());

        }

    }

    private void setLevelSelection(Controller controller)    {

        levelSelectionModeTitle = controller.levelSelectionModeTitle;

        String modeTitle = modeComboBox.getValue();
        levelSelectionModeTitle.setText(modeTitle);
    }

    @FXML
    public void homeButtonClicked() {
        levelSelectionModeTitle.setText("bruh");
    }

    private void changeScene(Parent parent, ActionEvent event)  {
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

}
