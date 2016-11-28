package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

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
        controller.setScene(ParentController.scene6ID);
    }

    @FXML
    public void openLoginDialog(ActionEvent event)  {
        controller.setScene(ParentController.scene5ID);
    }

    @FXML
    public void exitApplication(ActionEvent event)  {
        Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
        stage.close();
    }
}
