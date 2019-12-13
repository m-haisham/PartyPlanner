package cerberus.controllers;

import cerberus.helper.date.DateTimeHelper;
import cerberus.helper.date.LocalDateDifference;
import cerberus.helper.date.LocalTimeDifference;
import cerberus.helper.proceed.Proceeder;
import cerberus.models.list.QuantifiedListItem;
import cerberus.models.list.SelectableListItem;
import cerberus.models.list.SimpleListItem;
import cerberus.models.table.VenueItem;
import cerberus.party.Contact;
import cerberus.party.Duration;
import cerberus.party.Venue;
import cerberus.party.decorations.Decoration;
import cerberus.party.decorations.DecorationFactory;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("This field is required!");

        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("Enter a valid number");

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
        initDecorationFields();
    }

    private void initDecorationFields() {
        ArrayList<Decoration> allDecorations = DecorationFactory.availableDecorationsFor(new Farewell("", new Venue("", 0), new Duration(LocalDateTime.now(), LocalDateTime.now())));

        decorations = allDecorations
                .stream()
                .map(decoration -> new QuantifiedListItem(decoration.getLabel(), "", String.valueOf(decoration.getCost())))
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
        address.setPrefWidth(300);
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
        LocalDateDifference dateDifference = DateTimeHelper.dateDifference(partyFromDate.getValue(), partyToDate.getValue());
        LocalTimeDifference timeDifference = DateTimeHelper.timeDifference(partyFromTime.getValue(), partyToTime.getValue());

        int totalHours = dateDifference.inHours();
        totalHours += timeDifference.getHours();

        String text = totalHours + "Hrs";
        if (timeDifference.getMins() > 0)
            text += ", " + timeDifference.getMins() + "Mins";
        return text;
    }

    private void initpartyTypeComboBox() {
        partyTypeBox.getItems().add(Birthday.class.getSimpleName());
        partyTypeBox.getItems().add(Celebration.class.getSimpleName());
        partyTypeBox.getItems().add(Farewell.class.getSimpleName());
        partyTypeBox.getItems().add(Wedding.class.getSimpleName());
    }

    public void basicValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        // input validation
        proceed.add(labelField.validate());
        proceed.add(partyTypeBox.validate());

        // time validation
        LocalDateTime from = LocalDateTime.of(partyFromDate.getValue(), partyFromTime.getValue());
        LocalDateTime to = LocalDateTime.of(partyToDate.getValue(), partyToTime.getValue());

        if (from.isEqual(to) || from.isAfter(to)) {
            updateTimeDifference(event);
            durationText.setText(durationText.getText() + ", must be greater than 0.");
            proceed.add(false);
        }

        if (proceed.shouldProceed()) {
            stepTabs.getSelectionModel().selectNext();
        }
    }

    /* CONTACT INFO */
    public void organiserValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.add(organizerNameField.validate());
        proceed.add(organiserMobileField.validate());
        proceed.add(organiserEmailField.validate());

        if (proceed.shouldProceed()) {

            switch (partyTypeBox.getValue()){
                case "Birthday":
                    stepTabs.getSelectionModel().select(birthdayTab);
                    break;
                case "Wedding":
                    stepTabs.getSelectionModel().select(weddingTab);
                    break;
                case "Celebration":
                    stepTabs.getSelectionModel().select(celebrationTab);
                    break;
                case "Farewell":
                    stepTabs.getSelectionModel().select(farewellTab);
                    break;
                default:
                    break;
            }
        }
    }

    public void birthdayValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.add(birthdayCelebrantName.validate());
        proceed.add(birthdayCelebrantMobile.validate());
        proceed.add(birthdayCelebrantEmail.validate());
        proceed.add(birthdayCelebrantDate.validate());

        if (proceed.shouldProceed()) {
            stepTabs.getSelectionModel().select(venueTab);
        }

    }

    public void weddingValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        // spouse
        proceed.add(spouseName.validate());
        proceed.add(spouseMobile.validate());
        proceed.add(spouseMobile.validate());
        proceed.add(spouseEmail.validate());
        proceed.add(spouseDate.validate());

        // groom
        proceed.add(groomName.validate());
        proceed.add(groomMobile.validate());
        proceed.add(groomMobile.validate());
        proceed.add(groomEmail.validate());
        proceed.add(groomDate.validate());

        if (proceed.shouldProceed()) {
            stepTabs.getSelectionModel().select(venueTab);
        }
    }

    public void celebrationValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.add(celebrationMessage.validate());

        if (proceed.shouldProceed()) {
            stepTabs.getSelectionModel().select(venueTab);
        }
    }

    public void addToFarewellGroup(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.add(farewellName.validate());
        proceed.add(farewellMobile.validate());
        proceed.add(farewellEmail.validate());

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
            stepTabs.getSelectionModel().select(venueTab);
        } else {
            farewellCount.setText("MUST BE GREATER THAN 0");
        }
    }

    public void venueValidateAndProceed(ActionEvent event) {
        if (venueTable.getSelectionModel().getSelectedItem() == null) {
            venueWarning.setText("Please select a Venue!");
            return;
        }

        selectedVenue = venueTable.getSelectionModel().getSelectedItem().getValue().getVenue();
        venueWarning.setText("");

        stepTabs.getSelectionModel().select(contactsTab);
    }

    public void addToContactsGroup(ActionEvent event) {
        Proceeder proceed = new Proceeder();

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
            stepTabs.getSelectionModel().select(decorationTab);
        } else {
            contactsCount.setText("MUST BE GREATER THAN 0");
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
            completeAndDisplay();
        }
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
                String.valueOf(venueTable.getSelectionModel().getSelectedItem().getValue().getVenue().getCost())
                + Double.parseDouble(decorationTotal.getText())
        );

        stepTabs.getSelectionModel().select(completeTab);

    }
}
