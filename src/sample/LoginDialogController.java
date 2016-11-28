package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by arslan on 11/27/16.
 */
public class LoginDialogController implements ParentController {

    MainController controller;
    FileController fileController;

    @FXML
    private TextField loginUsernameField, loginPasswordField;

    @Override
    public void setParentController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    public void userLogin(ActionEvent event)    {
        fileController = new FileController();
        BuzzData data = new BuzzData();


        try {
            boolean success = fileController.loadData(loginUsernameField.getText(), loginPasswordField.getText(), data);

            if (success)    {
                System.out.println(loginPasswordField.getText());
                controller.setPlayer(new Player(data));
                controller.setData(data);

                controller.getLoggedInController().setLoggedInLogoutText(controller.getData().getUsername());

                controller.setScene(ParentController.scene2ID);
            }
            else    {
                System.out.println("Not a valid account.");
                controller.setScene(ParentController.scene1ID);
            }

        } catch (IOException e) {
            System.out.println("IOException was thrown.");
        }
    }

    @FXML
    public void dialogCancel(ActionEvent event) {
        controller.setScene(ParentController.scene1ID);
    }
}
