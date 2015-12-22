package view;

import dao.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by Крава on 22.12.2015.
 */
public class AppliedMeasuresController {
    Stage dialogStage;
    Measure currentMeasure;

    @FXML
    private TextField appliedMoney;

    @FXML
    private TextArea information;

    @FXML
    private DatePicker appliedDate;

    private boolean okClicked = false;

    public void initialize() {

    }

    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
            try (Connection con = DaoFactory.getConnection()) {
                AppliedMeasureDao appliedMeasureDao = DaoFactory.getAppliedMeasureDao(con);
                Instant instant = appliedDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                appliedMeasureDao.add(new AppliedMeasure(new Date(Date.from(instant).getTime()), Double.parseDouble(appliedMoney.getText()), currentMeasure, information.getText()));
                Message.showConfirmationnMessage("Рекомендація застосована");
            } catch (SQLException e) {
                Message.showErrorMessage(e.getMessage());
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

        if (appliedDate.getValue() == null || LocalDate.now().toEpochDay() - appliedDate.getValue().toEpochDay() < 0) {
            errorMessage += "Дата\n";
        }
        try {
            Double.parseDouble(appliedMoney.getText());
            if (appliedMoney.getText() == null || appliedMoney.getText().length() == 0 || Double.parseDouble(appliedMoney.getText()) < 0) {
                errorMessage += "Виділені кошти\n";
            }
        } catch (Exception ex) {
            errorMessage += "Виділені кошти\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Message.showErrorMessage("Виправте невірно вказані поля: \n" + errorMessage);
            return false;
        }
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMeasure(Measure measure) {
        this.currentMeasure = measure;
    }

}
