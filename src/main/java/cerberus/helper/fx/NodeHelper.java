package cerberus.helper.fx;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;

public class NodeHelper {

    /**
     * reset the node respective to its class
     * @param node to be reset
     */
    public static void reset(Node node) {

        if (node.getClass() == JFXTextField.class) ((TextField) node).setText("");
        else if (node.getClass() == JFXTextArea.class) ((TextArea) node).setText("");
        else if (node.getClass() == JFXListView.class) ((ListView) node).setItems(FXCollections.observableArrayList());
        else if (node.getClass() == JFXDatePicker.class) ((JFXDatePicker) node).setValue(LocalDate.now());
        else if (node.getClass() == JFXTimePicker.class) ((JFXTimePicker) node).setValue(LocalTime.now());
        else if (node.getClass() == JFXComboBox.class) ((ComboBox) node).setValue(null);
        else if (node.getClass() == Text.class) ((Text) node).setText("");
        else if (node.getClass() == JFXToggleButton.class) ((JFXToggleButton) node).setSelected(false);

    }

    /**
     * @param nodes runs {@link #reset(all_nodes_in_array)}}
     */
    public static void resetAll(Node[] nodes) {
        for (Node node : nodes) {
            reset(node);
        }
    }

}
