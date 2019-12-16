package cerberus.controllers;

import cerberus.Main;
import cerberus.models.dialog.PartyInfo;
import cerberus.models.list.PartyItem;
import cerberus.party.Party;
import cerberus.party.filter.PaidPercent;
import cerberus.party.filter.PartyType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PartiesController implements Initializable {

    public static PartiesController instance;

    public JFXListView<PartyItem> eventsList;
    public VBox partyDetail;
    public JFXComboBox<PaidPercent> filterPaid;
    public JFXComboBox<PartyType> filterPartyType;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        // filter options
        filterPaid.setCellFactory(new Callback<ListView<PaidPercent>, ListCell<PaidPercent>>() {
            @Override
            public ListCell<PaidPercent> call(ListView<PaidPercent> param) {
                return new JFXListCell<PaidPercent>() {
                    @Override
                    protected void updateItem(PaidPercent item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item == PaidPercent.All)
                                setText(item.toString());
                            else {
                                setText(item.toString().substring(1).concat("%"));
                            }
                        }
                    }
                };
            }
        });
        filterPaid.getItems().setAll(Arrays.asList(PaidPercent.values()));
        filterPaid.setValue(PaidPercent.All);
        filterPaid.setOnAction(event -> resetPartyList());


        filterPartyType.setCellFactory(new Callback<ListView<PartyType>, ListCell<PartyType>>() {
            @Override
            public ListCell<PartyType> call(ListView<PartyType> param) {
                return new JFXListCell<PartyType>() {
                    @Override
                    protected void updateItem(PartyType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(item.toString());
                        }
                    }
                };
            }
        });
        filterPartyType.getItems().setAll(Arrays.asList(PartyType.values()));
        filterPartyType.setValue(PartyType.All);
        filterPartyType.setOnAction(event -> resetPartyList());
        initPartyList();
    }

    public void initPartyList() {
        eventsList.setCellFactory(new Callback<ListView<PartyItem>, ListCell<PartyItem>>() {

            @Override
            public ListCell<PartyItem> call(ListView<PartyItem> param) {
                return new JFXListCell<PartyItem>() {

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
                PartyInfo info = new PartyInfo(eventsList.getSelectionModel().getSelectedItem().getParty());
                new JFXDialog(BaseController.instance.root, info.getRoot(), JFXDialog.DialogTransition.CENTER).show();
                info.set();
            }
        });

        resetPartyList();
    }

    public void resetPartyList(ArrayList<Party> parties) {

        // Erase
        eventsList.setItems(FXCollections.observableArrayList(
                parties
                        .stream()
                        .map(PartyItem::new)
                        .collect(Collectors.toList())));

    }

    public void resetPartyList() {

        ArrayList<Party> parties;

        PartyType type = filterPartyType.getValue();
        parties = Main.database.getAll(filterPaid.getValue(), type);

        resetPartyList(parties);

    }
}
