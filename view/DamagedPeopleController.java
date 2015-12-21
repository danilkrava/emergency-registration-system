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
    private GridPane damagedParts;

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

    }

    public void start() {
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
            try (Connection con = DaoFactory.getConnection()) {
                DamageTypeDao damageTypeDao = DaoFactory.getDamageTypeDao(con);
                List<DamageType> damagedList = damageTypeDao.getByPerson(info.getId());
                for (int i = 0; i < damagedList.size(); i++) {
                    Label label = new Label(damagedList.get(i).getName());
                    damagedParts.add(label, 0, i);
                    damagedParts.setMargin(label, new Insets(0.0, 0.0, 0.0, 20.0));
                }

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
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
