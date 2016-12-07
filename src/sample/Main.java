package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        MainController controller = new MainController();

        controller.loadScene(ParentController.scene1ID, ParentController.scene1resource);
        controller.loadScene(ParentController.scene2ID, ParentController.scene2resource);
        controller.loadScene(ParentController.scene3ID, ParentController.scene3resource);
        controller.loadScene(ParentController.scene4ID, ParentController.scene4resource);
        controller.loadScene(ParentController.scene5ID, ParentController.scene5resource);
        controller.loadScene(ParentController.scene6ID, ParentController.scene6resource);

        controller.setScene(ParentController.scene1ID);

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
