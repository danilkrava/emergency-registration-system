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
public class MeasuresController {
    Stage dialogStage;
    Emergency currentEmergency;

    @FXML
    private TableView<Measure> measureTableView;

    ///////////////////////////////////////////////

    @FXML
    private TableColumn<Measure, String> measureNameColumn;

    ///////////////////////////////////////////////

    @FXML
    private Label measureId;

    @FXML
    private TextField name;

    @FXML
    private ComboBox<TimeType> timeTypeComboBox;

    @FXML
    private ComboBox<SeverityType> severityTypeComboBox;

    @FXML
    private ComboBox<AreaType> areaTypeComboBox;

    ///////////////////////////////////////////////

    ObservableList<Measure> measures = FXCollections.observableArrayList();
    ObservableList<TimeType> timeTypes = FXCollections.observableArrayList();
    ObservableList<SeverityType> severityTypes = FXCollections.observableArrayList();
    ObservableList<AreaType> areaTypes = FXCollections.observableArrayList();

    public void initialize() {

    }

    public void start() {
        measureNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));
        try (Connection con = DaoFactory.getConnection()) {
            MeasureDao measureDao = DaoFactory.getMeasureDao(con);
            TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(con);
            SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(con);
            AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(con);

            measures.addAll(measureDao.getByEmergency(currentEmergency));
            timeTypes.addAll(timeTypeDao.getAll());
            severityTypes.addAll(severityTypeDao.getAll());
            areaTypes.addAll(areaTypeDao.getAll());
            measureTableView.setItems(measures);

            timeTypeComboBox.setItems(timeTypes);
            severityTypeComboBox.setItems(severityTypes);
            areaTypeComboBox.setItems(areaTypes);

        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            Message.showInformationMessage("Немає рекомендацій");
        }
        measureTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMeasureDetails(newValue));
    }

    private void showMeasureDetails(Measure info) {
        if (info != null) {
            this.measureId.setText(String.valueOf(info.getId()));
            this.name.setText(info.getInfo());
            this.timeTypeComboBox.setValue(info.getTimeType());
            this.severityTypeComboBox.setValue(info.getSeverityType());
            this.areaTypeComboBox.setValue(info.getAreaType());
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEmergency(Emergency emergency) {
        this.currentEmergency = emergency;
    }
}
