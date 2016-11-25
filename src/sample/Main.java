package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //TODO: Change the id's for all of the FXML components to their correct names
        //TODO: Place all the icons and images I use into one folder
        //TODO: Remove the console input and create a dialog input

        String resource = "loggedin.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();

        Controller controller = loader.getController();

        primaryStage.setTitle("Buzz Word");

        int width = 825;
        int height = 525;

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
