package cerberus.models.list;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class SelectableListItem {

    @FXML
    private HBox box;

    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private Text title;

    @FXML
    private Text subtitle;

    @FXML
    private Text trailing;

    public SelectableListItem(String title, String subtitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectable.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setTitle(title);
        setSubtitle(subtitle);
    }

    public boolean isChecked() {
        return checkBox.isSelected();
    }

    public void setChecked(boolean value) {
        checkBox.setSelected(value);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    public void setTrailing(String text) {
        this.trailing.setText(text);
    }

}
