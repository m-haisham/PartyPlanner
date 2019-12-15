package cerberus.models.dialog;

import cerberus.controllers.BaseController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AlertDialog extends JFXDialog {

    @FXML private VBox box;
    @FXML private Label title;
    @FXML private Label message;

    public AlertDialog(String message) {
        this("Alert", message);
    }

    public AlertDialog(String title, String message) {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/alert.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.title.setText(title);
        this.message.setText(message);

        this.setDialogContainer(BaseController.instance.root);
        this.setContent(this.box);
        this.setTransitionType(DialogTransition.CENTER);
    }

}
