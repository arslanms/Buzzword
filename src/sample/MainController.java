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
    private FileController fileController;

    //Controllers:
    private LoginController loginController;
    private LoggedInController loggedInController;
    private LevelSelectController levelSelectController;
    private GameplayController gameplayController;
    private LoginDialogController loginDialogController;
    private ProfileDialogController profileDialogController;
    private HelpController helpController;
    private ProfileController profileController;
    private EditProfileController editProfileController;

    public MainController() {
        super();
        scenes = new HashMap<>();
        fileController = new FileController();
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
            else if (parentController instanceof HelpController)    {
                helpController = (HelpController) parentController;
            }
            else if (parentController instanceof ProfileController) {
                profileController = (ProfileController) parentController;
            }
            else if (parentController instanceof EditProfileController) {
                editProfileController = (EditProfileController) parentController;
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

    public FileController getFileController() {
        return fileController;
    }

    public void setFileController(FileController fileController) {
        this.fileController = fileController;
    }

    public HelpController getHelpController() {
        return helpController;
    }

    public void setHelpController(HelpController helpController) {
        this.helpController = helpController;
    }

    public ProfileController getProfileController() {
        return profileController;
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    public EditProfileController getEditProfileController() {
        return editProfileController;
    }

    public void setEditProfileController(EditProfileController editProfileController) {
        this.editProfileController = editProfileController;
    }
}
