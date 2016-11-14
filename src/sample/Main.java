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

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the scene you want to display: ");
        System.out.println("1. Login\n2. Loggedin\n3. Level Selection (Dictionary Words)\n4. Level Selection (Famous People)\n" +
                "5. Level Selection (Places)\n6. Level Selection (Science)\n7. Gameplay");
        int choice = input.nextInt();
        String resource = "";
        switch (choice) {
            case 1:
                resource = "login.fxml";
                break;
            case 2:
                resource = "loggedin.fxml";
                break;
            case 3:
                resource = "levelselection-dictionarywords.fxml";
                break;
            case 4:
                resource = "levelselection-famouspeople.fxml";
                break;
            case 5:
                resource = "levelselection-places.fxml";
                break;
            case 6:
                resource = "levelselection-science.fxml";
                break;
            case 7:
                resource = "gameplay.fxml";
                break;
            default:
                break;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();

        Controller controller = loader.getController();

        primaryStage.setTitle("Buzz Word");
        Scene scene = new Scene(root, 825, 525);

        if (resource.equals("loggedin.fxml"))
            controller.addModes();

        if (resource.equals("gameplay.fxml"))
            controller.addTable();

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
