package cerberus.models.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class TrailingListItem {

    @FXML private HBox box;

    @FXML private Text leading;
    @FXML private Text title;
    @FXML private Text subtitle;
    @FXML private Text trailing;

    public TrailingListItem(String leading, String title, String subtitle, String trailing) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list/trailing.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.leading.setText(leading);
        this.title.setText(title);
        this.subtitle.setText(subtitle);
        this.trailing.setText(trailing);
    }

    public Text getLeading() {
        return leading;
    }

    public void setLeading(Text leading) {
        this.leading = leading;
    }

    public Text getTitle() {
        return title;
    }

    public void setTitle(Text title) {
        this.title = title;
    }

    public Text getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Text subtitle) {
        this.subtitle = subtitle;
    }

    public Text getTrailing() {
        return trailing;
    }

    public void setTrailing(Text trailing) {
        this.trailing = trailing;
    }

    public HBox asBox() {
        return box;
    }
}
