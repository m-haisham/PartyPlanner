package cerberus.models.dialog;

import cerberus.helper.date.DateTimeHelper;
import cerberus.models.list.SimpleListItem;
import cerberus.party.Contact;
import cerberus.party.Party;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class SpecificPartyInfo {

    private Tab tab;

    // tabs
    @FXML private Tab birthday;
    @FXML private Tab farewell;
    @FXML private Tab celebration;
    @FXML private Tab wedding;

    // BIRTHDAY
    @FXML private Text celebrantName;
    @FXML private Text celebrantMobile;
    @FXML private Text celebrantEmail;
    @FXML private Text celebrantBirthdate;
    @FXML private Text celebrantAge;

    // FAREWELL
    @FXML private JFXListView<VBox> farewellGroup;

    // CELEBRATION
    @FXML private Label celebrationText;

    // WEDDING
    @FXML private Text brideName;
    @FXML private Text brideMobile;
    @FXML private Text brideEmail;
    @FXML private Text brideBirthdate;

    @FXML private Text groomName;
    @FXML private Text groomMobile;
    @FXML private Text groomEmail;
    @FXML private Text groomBirthdate;

    public SpecificPartyInfo(Party party) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/specific.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (party.getClass() == Birthday.class) {
            assert party instanceof Birthday;
            tab = birthday;

            celebrantName.setText(((Birthday) party).getPerson().getName());
            celebrantMobile.setText(String.valueOf(((Birthday) party).getPerson().getMobile()));
            celebrantEmail.setText(((Birthday) party).getPerson().getEmail());
            celebrantBirthdate.setText(((Birthday) party).getPerson().getBirthDate().format(DateTimeHelper.dateFormatter));
            celebrantAge.setText(String.valueOf(((Birthday) party).getAgeOnPartyDate()));
        } else if (party.getClass() == Farewell.class) {
            assert party instanceof Farewell;
            tab = farewell;

            for (Contact contact : ((Farewell) party).getGroup())
                farewellGroup.getItems().add(new SimpleListItem(
                        contact.getName(),
                        contact.getEmail()
                ).asNode());
        } else if (party.getClass() == Celebration.class) {
            assert party instanceof Celebration;
            tab = celebration;

            celebrationText.setText(((Celebration) party).getMessage());
        } else if (party.getClass() == Wedding.class) {
            assert party instanceof Wedding;
            tab = wedding;

            brideName.setText(((Wedding) party).getBride().getName());
            brideMobile.setText(String.valueOf(((Wedding) party).getBride().getMobile()));
            brideEmail.setText(((Wedding) party).getBride().getEmail());
            brideBirthdate.setText(((Wedding) party).getBride().getBirthDate().format(DateTimeHelper.dateFormatter));

            groomName.setText(((Wedding) party).getGroom().getName());
            groomMobile.setText(String.valueOf(((Wedding) party).getGroom().getMobile()));
            groomEmail.setText(((Wedding) party).getGroom().getEmail());
            groomBirthdate.setText(((Wedding) party).getGroom().getBirthDate().format(DateTimeHelper.dateFormatter));
        }
    }

    public Tab getTab() {
        return tab;
    }

}
