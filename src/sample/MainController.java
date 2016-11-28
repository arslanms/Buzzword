package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by arslan on 11/27/16.
 */
public class MainController extends StackPane {

    private HashMap<String, Node> scenes;
    private String gameMode;
    private Player player;
    private BuzzData data;

    //Controllers:
    private LoginController loginController;
    private LoggedInController loggedInController;
    private LevelSelectController levelSelectController;
    private GameplayController gameplayController;
    private LoginDialogController loginDialogController;
    private ProfileDialogController profileDialogController;

    /*
    TODO: Work on letting the player select a level node and open the gameplay scene.
    TODO: Work on making a Yes/No Dialog box for whenever the user tries to exit the application - does not need to be connected to MainController
    TODO: Work on generating the letters for the grid and creating the target score properly. (Hardest part)
     */

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

            if (parentController instanceof LoginController)    {
                loginController = (LoginController) parentController;
            }
            else if (parentController instanceof LoggedInController)    {
                loggedInController = (LoggedInController) parentController;
            }
            else if (parentController instanceof LevelSelectController) {
                levelSelectController = (LevelSelectController) parentController;
            }
            else if (parentController instanceof GameplayController)    {
                gameplayController = (GameplayController) parentController;
            }
            else if (parentController instanceof LoginDialogController) {
                loginDialogController = (LoginDialogController) parentController;
            }
            else {
                profileDialogController = (ProfileDialogController) parentController;
            }


            return true;
        } catch (IOException e) {
            System.out.println("Scene was not loaded properly.");
            //e.printStackTrace();
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public BuzzData getData() {
        return data;
    }

    public void setData(BuzzData data) {
        this.data = data;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public LoggedInController getLoggedInController() {
        return loggedInController;
    }

    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }

    public LevelSelectController getLevelSelectController() {
        return levelSelectController;
    }

    public void setLevelSelectController(LevelSelectController levelSelectController) {
        this.levelSelectController = levelSelectController;
    }

    public GameplayController getGameplayController() {
        return gameplayController;
    }

    public void setGameplayController(GameplayController gameplayController) {
        this.gameplayController = gameplayController;
    }

    public LoginDialogController getLoginDialogController() {
        return loginDialogController;
    }

    public void setLoginDialogController(LoginDialogController loginDialogController) {
        this.loginDialogController = loginDialogController;
    }

    public ProfileDialogController getProfileDialogController() {
        return profileDialogController;
    }

    public void setProfileDialogController(ProfileDialogController profileDialogController) {
        this.profileDialogController = profileDialogController;
    }
}
