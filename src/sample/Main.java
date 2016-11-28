package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Scanner;

public class Main extends Application {

    public static final String scene1ID = "login";
    public static final String scene1resource = "login.fxml";
    public static final String scene2ID = "loggedin";
    public static final String scene2resource = "loggedin.fxml";
    public static final String scene3ID = "levelselection";
    public static final String scene3resource = "levelselection.fxml";
    public static final String scene4ID = "gameplay";
    public static final String scene4resource = "gameplay.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainController controller = new MainController();

        controller.loadScene(scene1ID, scene1resource);
        controller.loadScene(scene2ID, scene2resource);
        controller.loadScene(scene3ID, scene3resource);
        controller.loadScene(scene4ID, scene4resource);

        controller.setScene(scene2ID);

        Group root = new Group();
        root.getChildren().addAll(controller);

        primaryStage.setTitle("Buzz Word");

        int width = 750;
        int height = 500;

        Scene scene = new Scene(root, width, height);

        URL url = this.getClass().getResource("styles.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
