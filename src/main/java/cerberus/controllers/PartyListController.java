package cerberus.controllers;

import cerberus.Main;
import cerberus.models.list.PartyItem;
import cerberus.party.Party;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PartyListController implements Initializable {

    public static PartyListController instance;

    public JFXListView<PartyItem> eventsList;
    public VBox partyDetail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initPartyList();
    }

    public void update() {
        resetPartyList(Main.database.getAll());
    }

    public void initPartyList() {
        eventsList.setCellFactory(new Callback<ListView<PartyItem>, ListCell<PartyItem>>() {

            @Override
            public ListCell<PartyItem> call(ListView<PartyItem> param) {
                return new ListCell<PartyItem>() {

                    @Override
                    protected void updateItem(PartyItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(null);
                            setGraphic(item.asBox());
                        }
                    }
                };
            }
        });

        eventsList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println(eventsList.getSelectionModel().getSelectedItem().getLabel());
            }
        });

        resetPartyList(Main.database.getAll());
    }

    public void resetPartyList(ArrayList<Party> parties) {

        // Erase
        eventsList.setItems(FXCollections.observableArrayList());

        for (Party party : parties) {

            PartyItem item = new PartyItem(party);
            eventsList.getItems().add(item);
        }

    }
}
