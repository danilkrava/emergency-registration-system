package view;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Created by Крава on 21.12.2015.
 */
public class AddPersonController {
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField middlename;

    private Stage dialogStage;
    //  private Person person;
    private boolean okClicked = false;

    public void initialize() {
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Instant instant = dateField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Person person = new Person(name.getText(), surname.getText(), middlename.getText(), new Date(Date.from(instant).getTime()));
            PersonDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getPersonDao(con);
                dao.add(person);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (dateField.getValue() == null) {
            errorMessage += "No valid data!\n";
        }
        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "No valid name\n";
        }
        if (surname.getText() == null || surname.getText().length() == 0) {
            errorMessage += "No valid surname!\n";
        }
        if (middlename.getText() == null || middlename.getText().length() == 0) {
            errorMessage += "No valid middle!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}
