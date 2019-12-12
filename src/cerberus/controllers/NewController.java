package cerberus.controllers;

import cerberus.helper.date.DateTimeHelper;
import cerberus.helper.date.LocalDateDifference;
import cerberus.helper.date.LocalTimeDifference;
import cerberus.helper.proceed.Proceeder;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;
import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class NewController implements Initializable {

    public JFXTabPane stepTabs;
    public Tab basicTab;
    public Tab organiserContactTab;
    public Tab birthdayTab;
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

    }

    private void initBirthdayFields(RequiredFieldValidator requiredFieldValidator, NumberValidator numberValidator) {
        birthdayCelebrantName.getValidators().add(requiredFieldValidator);
        birthdayCelebrantMobile.getValidators().add(requiredFieldValidator);
        birthdayCelebrantMobile.getValidators().add(numberValidator);
        birthdayCelebrantEmail.getValidators().add(requiredFieldValidator);
        birthdayCelebrantDate.getValidators().add(requiredFieldValidator);
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

    private void updateTimeDifference(ActionEvent event) {
        LocalDateDifference dateDifference = DateTimeHelper.dateDifference(partyFromDate.getValue(), partyToDate.getValue());
        LocalTimeDifference timeDifference = DateTimeHelper.timeDifference(partyFromTime.getValue(), partyToTime.getValue());

        int totalHours = dateDifference.inHours();
        totalHours += timeDifference.getHours();

        String text = totalHours + "Hrs";
        if (timeDifference.getMins() > 0)
            text += ", " + timeDifference.getMins() + "Mins";
        durationText.setText(text);
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
                default:
                    break;
            }
            // different tabs dependting on party type
            System.out.println("TAB 3");
        }
    }

    public void birthdayValidateAndProceed(ActionEvent event) {
        Proceeder proceed = new Proceeder();

        proceed.add(birthdayCelebrantName.validate());
        proceed.add(birthdayCelebrantMobile.validate());
        proceed.add(birthdayCelebrantEmail.validate());
        proceed.add(birthdayCelebrantDate.validate());

        if (proceed.shouldProceed()) {
            // contact adding stage
        }

    }
}
