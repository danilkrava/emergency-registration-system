package view;

import dao.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 21.12.2015.
 */
public class DamagedPeopleController {
    Stage dialogStage;
    Emergency currentEmergency;

    @FXML
    private TableView<Person> personTableView;

    ///////////////////////////////////////////////

    @FXML
    private TableColumn<Person, String> personNameColumn;

    @FXML
    private TableColumn<Person, String> personSurnameColumn;

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

    public void initialize() {
        personNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        personSurnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));

        try (Connection con = DaoFactory.getConnection()) {
            PersonDao personDao = DaoFactory.getPersonDao(con);


            people.addAll(personDao.getByEmergency(currentEmergency.getId()));


            personTableView.setItems(people);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(dialogStage);
            alert.setContentText("Немає постраждалих");
            alert.showAndWait();
        }
        personTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    private void showPersonDetails(Person info) {
        if (info != null) {
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
