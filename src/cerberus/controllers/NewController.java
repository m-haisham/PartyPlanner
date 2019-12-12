package cerberus.controllers;

import cerberus.date.DateTimeHelper;
import cerberus.date.LocalDateDifference;
import cerberus.date.LocalTimeDifference;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class NewController implements Initializable {
    @FXML JFXTextField labelField;
    @FXML JFXComboBox<String> partyTypeBox;

    // date time
    public JFXDatePicker partyFromDate;
    public JFXTimePicker partyFromTime;

    public JFXDatePicker partyToDate;
    public JFXTimePicker partyToTime;

    public Text durationText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // fields
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("This field is required!");

        labelField.getValidators().add(validator);
        partyTypeBox.getValidators().add(validator);

        initpartyTypeComboBox();
        initPickers();
    }

    private void initPickers() {
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
        boolean proceed = true;

        // input validation
        proceed = labelField.validate();
        proceed = partyTypeBox.validate();

        // time validation
        LocalDateTime from = LocalDateTime.of(partyFromDate.getValue(), partyFromTime.getValue());
        LocalDateTime to = LocalDateTime.of(partyToDate.getValue(), partyToTime.getValue());

        if (from.isEqual(to) || from.isAfter(to)) {
            updateTimeDifference(event);
            durationText.setText(durationText.getText() + ", must be greater than 0.");
        }

        if (proceed) {
            System.out.println("Proceed");
        }
    }
}
