package sample;

/**
 * Created by arslan on 11/27/16.
 */
public interface ParentController {

    String scene1ID = "login";
    String scene1resource = "login.fxml";
    String scene2ID = "loggedin";
    String scene2resource = "loggedin.fxml";
    String scene3ID = "levelselection";
    String scene3resource = "levelselection.fxml";
    String scene4ID = "gameplay";
    String scene4resource = "gameplay.fxml";
    String scene5ID = "logindialog";
    String scene5resource = "logindialog.fxml";
    String scene6ID = "profiledialog";
    String scene6resource = "profiledialog.fxml";

    public void setParentController(MainController controller);

}
