package cerberus.models.list;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class QuantifiedListItem {

    @FXML
    private HBox box;
    
    @FXML
    private Text leading;

    @FXML
    private Text title;

    @FXML
    private Text subtitle;
    
    @FXML
    private JFXTextField textField;

    public QuantifiedListItem(String title, String subtitle) {
        this(title, subtitle, "0", "0");
    }

    public QuantifiedListItem(String title, String subtitle, String leading) {
        this(title, subtitle, leading, "");
    }

    public QuantifiedListItem(String title, String subtitle, String leading, String amount) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list/quantity.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setTitle(title);
        setSubtitle(subtitle);
        setLeading(leading);
        this.textField.setText(amount);
        this.textField.setValidators(new IntegerValidator() {{ setMessage("Int!"); }});
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    public void setLeading(String text) {
        this.leading.setText(text);
    }

    public String getLeading() {
        return this.leading.getText();
    }

    public void setLeading(double value) {
        this.leading.setText(String.valueOf(value));
    }

    public JFXTextField getTextField() {
        return textField;
    }

    public HBox asBox() {
        return this.box;
    }
}
