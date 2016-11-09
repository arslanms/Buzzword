package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the scene you want to display: ");
        System.out.println("1. Login\n2. Loggedin\n3. Level Selection\n4. Gameplay");
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
                resource = "levelselection.fxml";
                break;
            case 4:
                resource = "gameplay.fxml";
                break;
            default:
                break;
        }

        Parent root = FXMLLoader.load(getClass().getResource(resource));
        primaryStage.setTitle("Buzz Word");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
