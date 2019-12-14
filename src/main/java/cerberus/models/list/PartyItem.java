package cerberus.models.list;

import cerberus.party.Party;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class PartyItem {

    @FXML private VBox box;

    @FXML private Text label;
    @FXML private Text type;
    @FXML private Text organizer;
    @FXML private Text time;

    private Party party;

    public PartyItem (Party party) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list/party.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.party = party;

        this.label.setText(party.getLabel());
        this.type.setText(party.getClass().getSimpleName());
        this.organizer.setText(party.getContact().getName());
        this.time.setText(party.getOn().getFrom().format(Party.formatter));
    }

    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
    }

    public Text getType() {
        return type;
    }

    public void setType(Text type) {
        this.type = type;
    }

    public Text getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Text organizer) {
        this.organizer = organizer;
    }

    public Text getTime() {
        return time;
    }

    public void setTime(Text time) {
        this.time = time;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public VBox asBox() {
        return this.box;
    }
}
