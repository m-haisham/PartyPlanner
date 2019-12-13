package cerberus.models.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class SimpleListItem {

    @FXML
    private VBox box;

    @FXML
    private Text title;

    @FXML
    private Text subtitle;

    public SimpleListItem(String title, String subtitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list/simple.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setTitle(title);
        setSubtitle(subtitle);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    public void setWidth(double width) {
        this.box.setPrefWidth(width);
    }

    public VBox asNode() {
        return this.box;
    }
}
