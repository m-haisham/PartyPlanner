package cerberus.controllers;

import cerberus.Main;
import cerberus.helper.date.DateTimeHelper;
import cerberus.helper.date.LocalDateDifference;
import cerberus.helper.date.LocalTimeDifference;
import cerberus.helper.fx.NodeHelper;
import cerberus.helper.navigation.Navigator;
import cerberus.helper.proceed.Proceeder;
import cerberus.models.dialog.AlertDialog;
import cerberus.models.dialog.PartyInfo;
import cerberus.models.list.QuantifiedListItem;
import cerberus.models.list.SimpleListItem;
import cerberus.models.table.VenueItem;
import cerberus.party.*;
import cerberus.party.addons.Addon;
import cerberus.party.addons.AddonFactory;
import cerberus.party.addons.QuantifiedAddon;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewController implements Initializable {

    public static NewController instance;
    public JFXButton resetButton;

    private Navigator navigator;
    private Party party;

    public JFXTabPane stepTabs;
    public Tab basicTab;
    public Tab organiserContactTab;
    public Tab birthdayTab;
    public Tab weddingTab;
    public Tab celebrationTab;
    public Tab farewellTab;
    public Tab venueTab;
    public Tab contactsTab;
    public Tab decorationTab;
    public Tab completeTab;
    public Tab prepaymentTab;

    // BASIC
    @FXML JFXTextField labelField;
    @FXML JFXComboBox<String> partyTypeBox;

    public JFXDatePicker partyFromDate;
    public JFXTimePicker partyFromTime;

    public JFXDatePicker partyToDate;
    public JFXTimePicker partyToTime;

    public Text durationText;

    /* CONTACT INFO */
    public JFXTextField organizerNameField;
    public JFXTextField organiserMobileField;
    public JFXTextField organiserEmailField;

    /* BIRTHDAY */
    public JFXTextField birthdayCelebrantName;
    public JFXTextField birthdayCelebrantMobile;
    public JFXTextField birthdayCelebrantEmail;
    public JFXDatePicker birthdayCelebrantDate;

    /* WEDDING */
    public JFXTextField spouseName;
    public JFXTextField spouseMobile;
    public JFXTextField spouseEmail;
    public JFXDatePicker spouseDate;
    public JFXTextField groomName;
    public JFXTextField groomMobile;
    public JFXTextField groomEmail;
    public JFXDatePicker groomDate;

    /* CELEBRATION */
    public JFXTextArea celebrationMessage;

    /* FAREWELL */
    public JFXTextField farewellName;
    public JFXTextField farewellMobile;
    public JFXTextField farewellEmail;
    public JFXListView<VBox> farewellList;
    public Text farewellCount;
    public ArrayList<Contact> farewellGroup;

    /* VENUE */
    public JFXTreeTableView<VenueItem> venueTable;
    public Text venueWarning;
    public Venue selectedVenue;

    /* CONTACTS */
    public JFXTextField newContactName;
    public JFXTextField newContactMobile;
    public JFXTextField newContactEmail;
    public JFXListView<VBox> contactsList;
    public Text contactsCount;
    private ArrayList<Contact> contactsGroup;

    /* DECORATIONS */
    public JFXListView<HBox> decorationList;
    public List<QuantifiedListItem> decorations;
    public Text decorationTotal;

    /* PREPAYMENT */
    public Text prepaymentTotal;
    public Text prepaymentPercent;
    public JFXToggleButton prepaymentToggle;

    /* COMPLETE */
    public Text completeLabel;
    public Text completeType;
    public Text completeVenue;
    public Text completeDuration;
    public Text completeFrom;
    public Text completeName;
    public Text completeMobile;
    public Text completeEmail;
    public Text completeContactsCount;
    public Text completeVenueCost;
    public Text completeDecorationsCost;
    public Text completeTotalCost;

    public JFXButton finalizeBack;
    public JFXButton finalizeComplete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        navigator = new Navigator(stepTabs);
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("This field is required!");

        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("Enter a valid number");

        resetButton.setVisible(false);

        /* BASIC */
        labelField.getValidators().add(requiredFieldValidator);
        partyTypeBox.getValidators().add(requiredFieldValidator);

        initpartyTypeComboBox();
        initBasicPickers();

        /* ORGANISER CONTACT INFO */
        organiserEmailField.getValidators().add(requiredFieldValidator);
        organiserMobileField.getValidators().add(requiredFieldValidator);
        organiserMobileField.getValidators().add(numberValidator);
        organizerNameField.getValidators().add(requiredFieldValidator);

        /* BIRTHDAY */
        initBirthdayFields(requiredFieldValidator, numberValidator);

        /* WEDDING */
        initWeddingFields(requiredFieldValidator, numberValidator);

        /* CELEBRATION */
        celebrationMessage.getValidators().add(requiredFieldValidator);

        /* FAREWELL */
        initFarewellFields(requiredFieldValidator, numberValidator);

        /* VENUE */
        initVenueFields();

        /* CONTACTS */
        initContactFields(requiredFieldValidator, numberValidator);

        /* DECORATIONS */
        initDecorationFields(Birthday.class);
    }

    private void initDecorationFields(Class<? extends Party> type) {
        if (type == null)
            return;

        decorationList.setItems(FXCollections.observableArrayList());
        decorations = new ArrayList<>();

        ArrayList<Addon> allAddons = AddonFactory.availableDecorationsFor(type);

        decorations = allAddons
                .stream()
                .map(addon -> new QuantifiedListItem(addon.getLabel(), "", String.valueOf(addon.getCost())))
                .collect(Collectors.toList());


        decorations.forEach(item -> {
            item.getTextField().focusedProperty().addListener((o, oldValue, newValue) -> {
                if (oldValue != newValue) {
                    recalculateDecorationsCost();
                }
            });
        });

        decorationList.getItems().setAll(decorations
                .stream()
                .map(QuantifiedListItem::asBox)
                .collect(Collectors.toList()));

    }

    private void initContactFields(RequiredFieldValidator requiredFieldValidator, NumberValidator numberValidator) {
        newContactName.getValidators().add(requiredFieldValidator);
        newContactMobile.getValidators().add(requiredFieldValidator);
        newContactMobile.getValidators().add(numberValidator);
        newContactEmail.getValidators().add(requiredFieldValidator);
        contactsList.setExpanded(true);
        contactsGroup = new ArrayList<>();
    }

    private void initBirthdayFields(RequiredFieldValidator requiredFieldValidator, NumberValidator numberValidator) {
        birthdayCelebrantName.getValidators().add(requiredFieldValidator);
        birthdayCelebrantMobile.getValidators().add(requiredFieldValidator);
        birthdayCelebrantMobile.getValidators().add(numberValidator);
        birthdayCelebrantEmail.getValidators().add(requiredFieldValidator);
        birthdayCelebrantDate.getValidators().add(requiredFieldValidator);
    }

    private void initWeddingFields(RequiredFieldValidator requiredFieldValidator, NumberValidator numberValidator) {
        // spouse
        spouseName.getValidators().add(requiredFieldValidator);
        spouseMobile.getValidators().add(requiredFieldValidator);
        spouseMobile.getValidators().add(numberValidator);
        spouseEmail.getValidators().add(requiredFieldValidator);
        spouseDate.getValidators().add(requiredFieldValidator);

        // groom
        groomName.getValidators().add(requiredFieldValidator);
        groomMobile.getValidators().add(requiredFieldValidator);
        groomMobile.getValidators().add(numberValidator);
        groomEmail.getValidators().add(requiredFieldValidator);
        groomDate.getValidators().add(requiredFieldValidator);
    }

    private void initFarewellFields(RequiredFieldValidator requiredFieldValidator, NumberValidator numberValidator) {
        farewellName.getValidators().add(requiredFieldValidator);
        farewellMobile.getValidators().add(requiredFieldValidator);
        farewellMobile.getValidators().add(numberValidator);
        farewellEmail.getValidators().add(requiredFieldValidator);
        farewellList.setExpanded(true);
        farewellGroup = new ArrayList<>();
    }

    private void initBasicPickers() {
        partyFromDate.setValue(LocalDate.now());
        partyFromTime.setValue(LocalTime.now());

        partyToDate.setValue(LocalDate.now());
        partyToTime.setValue(LocalTime.now());

        partyFromDate.setOnAction(this::updateTimeDifference);
        partyFromTime.setOnAction(this::updateTimeDifference);
        partyToDate.setOnAction(this::updateTimeDifference);
        partyToTime.setOnAction(this::updateTimeDifference);

    }

    private void initVenueFields() {

        // Table
        JFXTreeTableColumn<VenueItem, String> address = new JFXTreeTableColumn<>("Address");
        address.setPrefWidth(220);
        address.setCellValueFactory(param -> param.getValue().getValue().getAddress());

        JFXTreeTableColumn<VenueItem, Double> cost = new JFXTreeTableColumn<>("Cost");
        cost.setPrefWidth(90);
        cost.setCellValueFactory(param -> param.getValue().getValue().getCost().asObject());

        ObservableList<VenueItem> venues = FXCollections.observableArrayList(Venue.mock()
                .stream()
                .map(VenueItem::new)
                .collect(Collectors.toList()));

        final TreeItem<VenueItem> root = new RecursiveTreeItem<>(venues, RecursiveTreeObject::getChildren);
        venueTable.getColumns().setAll(address, cost);
        venueTable.setRoot(root);
        venueTable.setShowRoot(false);

        // Text
        venueWarning.setText("");
    }

    private void updateTimeDifference(ActionEvent event) {
        durationText.setText(getTimeDifference());
    }

    private String getTimeDifference() {
        DateTimeDifference difference = new DateTimeDifference(
                LocalDateTime.of(partyFromDate.getValue(), partyFromTime.getValue()),
                LocalDateTime.of(partyToDate.getValue(), partyToTime.getValue())
        );
        return difference.toString();
    }

    private void initpartyTypeComboBox() {
        partyTypeBox.getItems().add(Birthday.class.getSimpleName());
        partyTypeBox.getItems().add(Celebration.class.getSimpleName());
        partyTypeBox.getItems().add(Farewell.class.getSimpleName());
        partyTypeBox.getItems().add(Wedding.class.getSimpleName());
        partyTypeBox.setOnAction(event -> {
            switch (partyTypeBox.getValue()){
                case "Birthday":
                    initDecorationFields(Birthday.class);
                    break;
                case "Wedding":
                    initDecorationFields(Wedding.class);
                    break;
                case "Celebration":
                    initDecorationFields(Celebration.class);
                    break;
                case "Farewell":
                    initDecorationFields(Farewell.class);
                    break;
                default:
                    break;
            }
        });
    }

    public void jumpPreviousTab(ActionEvent event) {
        Tab pop = navigator.pop();
    }

    public void basicValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        // input validation
        proceed.addAll(new boolean[] {
                labelField.validate(),
                partyTypeBox.validate()
        });

        // time validation
        LocalDateTime from = LocalDateTime.of(partyFromDate.getValue(), partyFromTime.getValue());
        LocalDateTime to = LocalDateTime.of(partyToDate.getValue(), partyToTime.getValue());

        if (from.isEqual(to) || from.isAfter(to)) {
            updateTimeDifference(event);
            durationText.setText(durationText.getText() + ", must be greater than 0.");
            new AlertDialog("Negative time ... :O").show();
            proceed.add(false);
        }

        if (proceed.shouldProceed()) {
            navigator.push(organiserContactTab);
            resetButton.setVisible(true);
        }
    }

    /* CONTACT INFO */
    public void organiserValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.addAll(new boolean[] {
                organizerNameField.validate(),
                organiserMobileField.validate(),
                organiserEmailField.validate()
        });

        if (proceed.shouldProceed()) {

            switch (partyTypeBox.getValue()){
                case "Birthday":
                    navigator.push(birthdayTab);
                    break;
                case "Wedding":
                    navigator.push(weddingTab);
                    break;
                case "Celebration":
                    navigator.push(celebrationTab);
                    break;
                case "Farewell":
                    navigator.push(farewellTab);
                    break;
                default:
                    break;
            }
        }
    }

    public void birthdayValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.addAll(new boolean[] {
                birthdayCelebrantName.validate(),
                birthdayCelebrantMobile.validate(),
                birthdayCelebrantEmail.validate(),
                birthdayCelebrantDate.validate()
        });

        if (proceed.shouldProceed()) {
            navigator.push(venueTab);
        }

    }

    public void weddingValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.addAll(new boolean[] {
                // bride
                spouseName.validate(),
                spouseMobile.validate(),
                spouseMobile.validate(),
                spouseEmail.validate(),
                spouseDate.validate(),

                // groom
                groomName.validate(),
                groomMobile.validate(),
                groomMobile.validate(),
                groomEmail.validate(),
                groomDate.validate()
        });

        if (proceed.shouldProceed()) {
            navigator.push(venueTab);
        }
    }

    public void celebrationValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.add(celebrationMessage.validate());

        if (proceed.shouldProceed()) {
            navigator.push(venueTab);
        }
    }

    public void addToFarewellGroup(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.addAll(new boolean[] {
                farewellName.validate(),
                farewellMobile.validate(),
                farewellEmail.validate()
        });

        if (proceed.shouldProceed()) {

            // add to list
            farewellGroup.add(new Contact(farewellName.getText(), Integer.parseInt(farewellMobile.getText()), farewellEmail.getText()));

            // add to list view
            SimpleListItem listItem = new SimpleListItem(farewellName.getText(), farewellEmail.getText());
            farewellList.getItems().add(listItem.asNode());

            // farewell count display
            farewellCount.setText(String.valueOf(farewellList.getItems().size()));

        }

    }

    public void farewellValidateAndProceed(ActionEvent event) {
        if (farewellList.getItems().size() > 0) {
            navigator.push(venueTab);
        } else {
            new AlertDialog("No one to send off makes a dull farewell").show();
        }
    }

    public void venueValidateAndProceed(ActionEvent event) {
        if (venueTable.getSelectionModel().getSelectedItem() == null) {
            venueWarning.setText("Please select a Venue!");
            new AlertDialog("Select a venue!").show();
            return;
        }

        selectedVenue = venueTable.getSelectionModel().getSelectedItem().getValue().getVenue();
        venueWarning.setText("");

        navigator.push(contactsTab);
    }

    public void addToContactsGroup(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.addAll(new boolean[] {
                newContactName.validate(),
                newContactMobile.validate(),
                newContactEmail.validate()
        });

        if (proceed.shouldProceed()) {

            contactsGroup.add(new Contact(newContactName.getText(), Integer.parseInt(newContactMobile.getText()), newContactEmail.getText()));

            // add to list view
            SimpleListItem listItem = new SimpleListItem(newContactName.getText(), newContactEmail.getText());
            contactsList.getItems().add(listItem.asNode());

            // count update
            contactsCount.setText(String.valueOf(contactsList.getItems().size()));
        }
    }

    public void contactsValidateAndProceed(ActionEvent event) {
        if (contactsList.getItems().size() > 0) {
            navigator.push(decorationTab);
        } else {
            new AlertDialog("No invitees makes a dull party").show();
        }
    }

    public void recalculateDecorationsCost() {
        double totalCost = 0;
        for (QuantifiedListItem decoration : decorations) {
            double cost = Double.parseDouble(decoration.getLeading());

            int quantity = 0;
            if (!decoration.getTextField().getText().equals("") && decoration.getTextField().validate())
                quantity = Integer.parseInt(decoration.getTextField().getText());

            System.out.println();
            totalCost += cost * quantity;
        }

        decorationTotal.setText(String.valueOf(totalCost));
    }

    public void decorationValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        for (QuantifiedListItem decoration : decorations) {
            proceed.add(decoration.getTextField().validate());
        }

        if (proceed.shouldProceed()) {

            // setup prepayment
            double totalCost = venueTable.getSelectionModel().getSelectedItem().getValue().getVenue().getCost()
                            + Double.parseDouble(decorationTotal.getText());

            prepaymentTotal.setText(String.valueOf(totalCost));
            prepaymentPercent.setText(String.valueOf(totalCost * Party.prepaymentPercent));

            navigator.push(prepaymentTab);
        }
    }

    public void prepaymentValidateAndProceed(ActionEvent event) {
        completeAndDisplay();
    }

    public void completeAndDisplay() {

        /* BASIC */
        completeLabel.setText(labelField.getText());
        completeType.setText(partyTypeBox.getValue());
        completeVenue.setText(venueTable.getSelectionModel().getSelectedItem().getValue().getVenue().getPath());
        completeDuration.setText(getTimeDifference());
        completeFrom.setText(
                DateTimeFormatter.ofPattern("hh:mma dd MMM yyyy").format(
                        LocalDateTime.of(partyFromDate.getValue(), partyFromTime.getValue())
                )
        );

        /* CONTACTS */
        completeName.setText(organizerNameField.getText());
        completeMobile.setText(String.valueOf(organiserMobileField.getText()));
        completeEmail.setText(organiserEmailField.getText());
        completeContactsCount.setText(String.valueOf(contactsList.getItems().size()));

        /* COST */
        completeVenueCost.setText(
                String.valueOf(venueTable.getSelectionModel().getSelectedItem().getValue().getVenue().getCost())
        );
        completeDecorationsCost.setText(decorationTotal.getText());
        completeTotalCost.setText(
                String.valueOf(venueTable.getSelectionModel().getSelectedItem().getValue().getVenue().getCost()
                + Double.parseDouble(decorationTotal.getText()))
        );

        // tab
        navigator.push(completeTab);

        // create party values
        this.party = createParty();
    }

    public Party createParty() {

        Party party = null;

        if (partyTypeBox.getValue().equals(Birthday.class.getSimpleName())) {
            party = new Birthday();
            party = (Birthday) party;

            ((Birthday) party).setPerson(new Person(
                    birthdayCelebrantName.getText(),
                    birthdayCelebrantDate.getValue(),
                    Integer.parseInt(birthdayCelebrantMobile.getText()),
                    birthdayCelebrantEmail.getText()
            ));

            ((Birthday) party).setAge(((Birthday) party).getAgeOnPartyDate());

        } else if (partyTypeBox.getValue().equals(Celebration.class.getSimpleName())) {
            party = new Celebration();
            party = (Celebration) party;

            ((Celebration) party).setMessage(celebrationMessage.getText());

        } else if (partyTypeBox.getValue().equals(Farewell.class.getSimpleName())) {
            party = new Farewell();
            party = (Farewell) party;

            ((Farewell) party).setGroup(farewellGroup);

        } else if (partyTypeBox.getValue().equals(Wedding.class.getSimpleName())) {
            party = new Wedding();
            party = (Wedding) party;

            ((Wedding) party).setGroom(new Person(
                    groomName.getText(),
                    groomDate.getValue(),
                    Integer.parseInt(groomMobile.getText()),
                    groomEmail.getText()
            ));

            ((Wedding) party).setBride(new Person(
                    spouseName.getText(),
                    spouseDate.getValue(),
                    Integer.parseInt(spouseMobile.getText()),
                    spouseEmail.getText()
            ));
        }

        if (party == null)
            return party;

        // generics
        party.setLabel(labelField.getText());
        party.setVenue(venueTable.getSelectionModel().getSelectedItem().getValue().getVenue());
        party.setOn(new Duration(
                LocalDateTime.of(partyFromDate.getValue(), partyFromTime.getValue()),
                LocalDateTime.of(partyToDate.getValue(), partyToTime.getValue())
        ));
        party.setPaidPercentile(prepaymentToggle.isSelected() ? Party.prepaymentPercent : 0);

        party.setContacts(contactsGroup);
        party.setContact(new Contact(
                organizerNameField.getText(),
                Integer.parseInt(organiserMobileField.getText()),
                organiserEmailField.getText()
        ));

        // decorations
        ArrayList<QuantifiedAddon> decos = new ArrayList<>();
        decorations.forEach(listItem -> {

            if (listItem.getTextField().getText().equals(""))
                return;

            int quantity = Integer.parseInt(listItem.getTextField().getText());
            if (quantity <= 0) return;

            decos.add(new QuantifiedAddon(
                    listItem.getTitle(),
                    Double.parseDouble(listItem.getLeading()),
                    quantity
            ));
        });

        party.setAddons(decos);

        return party;
    }

    public void finalizeAndInsert(ActionEvent event) {
        // database actions
        Main.database.insertParty(party);
        PartiesController.instance.resetPartyList();
        ChartsController.instance.setCharts();

        // buttons
        finalizeBack.setDisable(true);
        finalizeComplete.setDisable(true);
        finalizeComplete.setText("ADDED");
    }

    public void showFullDetails(ActionEvent event) {
        PartyInfo info = new PartyInfo(this.party);
        JFXDialog dialog = new JFXDialog(BaseController.instance.root, info.getRoot(), JFXDialog.DialogTransition.CENTER);
        dialog.show();
        info.set();
    }

    public void reset() {

        NodeHelper.resetAll(new Node[]{
                // BASIC
                labelField,
                partyTypeBox,
                partyFromDate,
                partyFromTime,
                partyToDate,
                partyToTime,

                /* CONTACT INFO */
                organizerNameField,
                organiserMobileField,
                organiserEmailField,

                /* BIRTHDAY */
                birthdayCelebrantName,
                birthdayCelebrantMobile,
                birthdayCelebrantEmail,
                birthdayCelebrantDate,

                /* WEDDING */
                spouseName,
                spouseMobile,
                spouseEmail,
                spouseDate,

                groomName,
                groomMobile,
                groomEmail,
                groomDate,

                /* CELEBRATION */
                celebrationMessage,

                /* FAREWELL */
                farewellName,
                farewellMobile,
                farewellEmail,
                farewellList,
                farewellCount,

                /* VENUE */

                /* CONTACTS */
                newContactName,
                newContactMobile,
                newContactEmail,
                contactsList,
                contactsCount,

                /* DECORATIONS */
                decorationTotal,

                /* PREPAYMENT */
                prepaymentTotal,
                prepaymentPercent,
                prepaymentToggle

                /* complete screen doesnt need to be resetted, as its rewritten on form completion */
        });

        // FAREWELL
        farewellGroup = new ArrayList<>();

        // VENUE
        selectedVenue = null;

        // CONTACTS
        contactsGroup = new ArrayList<>();

        // DECORATIONS
        decorations.forEach(decoration -> decoration.getTextField().setText(""));

        // COMPLETE
        finalizeBack.setDisable(false);
        finalizeComplete.setDisable(false);
        finalizeComplete.setText("Finalize");

        resetButton.setVisible(false);
        stepTabs.getSelectionModel().select(basicTab);
        navigator = new Navigator(stepTabs);
    }
}
