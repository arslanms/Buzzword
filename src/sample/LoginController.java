package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by arslan on 11/27/16.
 */
public class LoginController implements ParentController {

    private MainController controller;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void openProfileDialog(ActionEvent event)    {

    }

    @FXML
    public void openLoginDialog(ActionEvent event)  {

    }

    @FXML
    public void exitApplication(ActionEvent event)  {

    }
}
