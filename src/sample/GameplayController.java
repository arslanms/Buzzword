package sample;

/**
 * Created by arslan on 11/27/16.
 */
public class GameplayController implements ParentController {

    private  MainController controller;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }
}
