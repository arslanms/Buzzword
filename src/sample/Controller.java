package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Controller {

    @FXML
    public ComboBox modeComboBox;
    public TableView<TableElement> gameplayTable;
    public ObservableList<TableElement> tabledata;
    public TableColumn gameplayWordsColumn;
    public TableColumn gameplayPointsColumn;
    public Stage loginStage;

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


}
