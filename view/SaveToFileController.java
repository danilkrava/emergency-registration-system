package view;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DamageType;
import model.Emergency;
import model.Person;
import model.Report;
import org.controlsfx.control.CheckListView;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by Крава on 22.12.2015.
 */
public class SaveToFileController {

    private Emergency emergency;
    @FXML
    private TextField path;


    private Stage dialogStage;

    private ObservableList<Report> reports = FXCollections.observableArrayList();
    //  private Person person;
    private boolean okClicked = false;

    public void initialize() {

    }

    public void start() {
        try (Connection con = DaoFactory.getConnection()) {
            ReportDao reportDao = DaoFactory.getReportDao(con);

            reports.addAll(reportDao.getByEmergency(emergency));

        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void choosePath() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(dialogStage);
        path.setText(file.getPath());

    }


    @FXML
    private void handleOk() {
      /*  if (isInputValid()) {
            Instant instant = dateField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Person person = new Person(name.getText(), surname.getText(), middlename.getText(), new Date(Date.from(instant).getTime()));
            PersonDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getPersonDao(con);
                dao.add(person, emergency.getValue(), damagedParts.getCheckModel().getCheckedItems());
            } catch (SQLException e) {
                Message.showErrorMessage(e.getMessage());
            }
            okClicked = true;
            Message.showInformationMessage("Запис додано");
            dialogStage.close();
        }*/
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (path.getText() == null || path.getText().length() == 0) {
            errorMessage += "Путь\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Message.showErrorMessage("Виправте невірно вказані поля: \n" + errorMessage);
            return false;
        }
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
    }
}
