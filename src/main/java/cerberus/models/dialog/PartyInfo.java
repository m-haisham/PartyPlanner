package cerberus.models.dialog;

import cerberus.models.list.SimpleListCell;
import cerberus.models.list.SimpleListItem;
import cerberus.models.list.TrailingListCell;
import cerberus.models.list.TrailingListItem;
import cerberus.party.Party;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Instantiable ui component to show all of manga info
 */
public class PartyInfo {

    private Party party;

    @FXML private JFXTabPane tabPane;

    @FXML private BorderPane root;
    @FXML private Text label;

    // VENUE
    @FXML private Text venueAddress;
    @FXML private Text venueCost;

    // TIME
    @FXML private Text from;
    @FXML private Text to;
    @FXML private Text duration;

    // CONTACT
    @FXML private Text orgName;
    @FXML private Text orgMobile;
    @FXML private Text orgEmail;

    // OTHER
    @FXML private Text created;

    @FXML private JFXListView contactsList;
    @FXML private Text inviteeCount;

    @FXML private JFXListView decorationsList;
    @FXML private Text addonsCost;

    // COST
    @FXML private Text cVenueCost;
    @FXML private Text cAddonsCost;
    @FXML private Text cPaid;
    @FXML private Text cTotalCost;

    public PartyInfo(Party party) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/party.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.party = party;

        this.label.setText(party.getLabel());
        // VENUE
        this.venueAddress.setText(party.getVenue().getPath());
        this.venueCost.setText(String.valueOf(party.getVenue().getCost()));
        // TIME
        this.from.setText(Party.formatter.format(party.getOn().getFrom()));
        this.to.setText(Party.formatter.format(party.getOn().getTo()));
        this.duration.setText(party.getOn().difference().toString());
        // CONTACT
        this.orgName.setText(party.getContact().getName());
        this.orgMobile.setText(String.valueOf(party.getContact().getMobile()));
        this.orgEmail.setText(party.getContact().getEmail());
        // OTHER
        this.created.setText(party.getCreated().format(Party.formatter));
        // COST
        this.cVenueCost.setText(String.valueOf(party.getVenue().getCost()));
        this.cAddonsCost.setText(String.valueOf(party.getAddonsCost()));

        double total = party.getTotalCost();
        this.cPaid.setText(String.valueOf(party.getPaidPercentile() * total));
        this.cTotalCost.setText(String.valueOf(total));
    }

    public void set() {

        // Lists
        contactsList.setCellFactory(param -> new SimpleListCell());
        contactsList.setItems(FXCollections.observableArrayList(
                party.getContacts().stream().map(contact -> new SimpleListItem(contact.getName(), contact.getEmail())).collect(Collectors.toList())
        ));
        this.inviteeCount.setText(String.valueOf(contactsList.getItems().size()));

        decorationsList.setCellFactory(param -> new TrailingListCell());
        decorationsList.setItems(FXCollections.observableArrayList(
                party.getAddons().stream()
                        .map(addon -> {
                            return new TrailingListItem(
                                    String.valueOf(addon.getQuantity()),
                                    addon.getLabel(),
                                    "",
                                    String.valueOf(addon.getCost())
                            );
                        }).collect(Collectors.toList())
        ));
        addonsCost.setText(String.valueOf(party.getAddonsCost()));

        // get specific tab
        SpecificPartyInfo specificPartyInfo = new SpecificPartyInfo(party);
        tabPane.getTabs().add(2, specificPartyInfo.getTab());
    }

    public BorderPane getRoot() {
        return root;
    }
}
