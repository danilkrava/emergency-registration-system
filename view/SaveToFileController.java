package view;

import dao.DamageTypeDao;
import dao.DaoFactory;
import dao.EmergencyDao;
import dao.PersonDao;
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
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField middlename;
    @FXML
    private ComboBox<Emergency> emergency;
    @FXML
    private CheckListView<DamageType> damagedParts;

    private Stage dialogStage;

    private ObservableList<Emergency> emergencies = FXCollections.observableArrayList();
    private ObservableList<DamageType> damageTypes = FXCollections.observableArrayList();
    //  private Person person;
    private boolean okClicked = false;

    public void initialize() {
        try (Connection con = DaoFactory.getConnection()) {
            EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(con);
            DamageTypeDao damageTypeDao = DaoFactory.getDamageTypeDao(con);

            emergencies.addAll(emergencyDao.getAll());
            emergency.setItems(emergencies);


            damageTypes.addAll(damageTypeDao.getAll());
            damagedParts.setItems(damageTypes);
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
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
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
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (dateField.getValue() == null || LocalDate.now().toEpochDay() - dateField.getValue().toEpochDay() < 0) {
            errorMessage += "Дата\n";
        }
        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "Ім'я\n";
        }
        if (surname.getText() == null || surname.getText().length() == 0) {
            errorMessage += "Прізвище\n";
        }
        if (middlename.getText() == null || middlename.getText().length() == 0) {
            errorMessage += "По-батькові\n";
        }
        if (emergency.getValue() == null) {
            errorMessage += "Надзвичайна ситуація\n";
        }
        if (damagedParts.getCheckModel().getCheckedItems().size() == 0) {
            errorMessage += "Уражені частини\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Message.showErrorMessage("Виправте невірно вказані поля: \n" + errorMessage);
            return false;
        }
    }
}
