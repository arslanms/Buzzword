package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by arslan on 11/27/16.
 */
public class MainController extends StackPane {

    private HashMap<String, Node> scenes;
    private String gameMode;

    public MainController() {
        super();
        scenes = new HashMap<>();
    }

    public void addScene(String name, Node scene)   {
        scenes.put(name, scene);
    }

    public Node getScene(String name)   {
        return scenes.get(name);
    }

    public boolean loadScene(String name, String resource)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            Parent scene = fxmlLoader.load();
            ParentController parentController = (ParentController) fxmlLoader.getController();
            parentController.setParentController(this);
            addScene(name, scene);

            return true;
        } catch (IOException e) {
            System.out.println("Scene was not loaded properly.");
            return false;
        }
    }

    public boolean setScene(String name)    {
        if (scenes.get(name) != null)   {

            if (!getChildren().isEmpty())    {
                getChildren().remove(0);
                getChildren().add(scenes.get(name));
            } else  {
                getChildren().add(scenes.get(name));
            }
            return true;
        } else  {
            System.out.println("Screen has not been loaded.");
            return false;
        }
    }

    public void setGameMode(String gameMode)    {
        this.gameMode = gameMode;
    }

    public String getGameMode() {
        return gameMode;
    }

}
