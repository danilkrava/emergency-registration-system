package view;

import dao.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Крава on 21.12.2015.
 */
public class DamagedPeopleController {
    Stage dialogStage;
    Emergency currentEmergency;

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableView<DamageType> damageTypeTableView;

    ///////////////////////////////////////////////

    @FXML
    private TableColumn<Person, String> personNameColumn;

    @FXML
    private TableColumn<Person, String> personSurnameColumn;

    @FXML
    private TableColumn<DamageType, String> damageTypeColumn;

    ///////////////////////////////////////////////

    @FXML
    private Label personId;

    @FXML
    private TextField personName;

    @FXML
    private TextField personSurname;

    @FXML
    private TextField personMiddleName;

    @FXML
    private DatePicker personBirthDay;

    ///////////////////////////////////////////////

    private ObservableList<Person> people = FXCollections.observableArrayList();
    private ObservableList<DamageType> damageTypes = FXCollections.observableArrayList();

    public void initialize() {

    }

    public void start() {
        personNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        personSurnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        damageTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        try (Connection con = DaoFactory.getConnection()) {
            PersonDao personDao = DaoFactory.getPersonDao(con);

            people.addAll(personDao.getByEmergency(currentEmergency.getId()));

            personTableView.setItems(people);
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            Message.showErrorMessage("Немає постраждалих");
        }
        personTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(Person info) {
        if (info != null) {
            try (Connection con = DaoFactory.getConnection()) {
                damageTypeTableView.refresh();
                DamageTypeDao damageTypeDao = DaoFactory.getDamageTypeDao(con);
                damageTypes = damageTypeDao.getByPerson(info.getId());
                damageTypeTableView.setItems(damageTypes);

            } catch (SQLException e) {
                Message.showErrorMessage(e.getMessage());
            }
            this.personId.setText(String.valueOf(info.getId()));
            this.personName.setText(info.getName());
            this.personSurname.setText(String.valueOf(info.getSurname()));
            this.personMiddleName.setText(info.getMiddleName());
            this.personBirthDay.setValue(info.getBirthDate().toLocalDate());
        } else {
            //label.setText("");
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEmergency(Emergency emergency) {
        this.currentEmergency = emergency;
    }

}
