package view;

import dao.DamageTypeDao;
import dao.DaoFactory;
import dao.MeasureDao;
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
    public void initialize() {

    }

    public void start() {
        measureNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));

        try (Connection con = DaoFactory.getConnection()) {
            MeasureDao measureDao = DaoFactory.getMeasureDao(con);
            measures.addAll(measureDao.getByEmergency(currentEmergency));
            measureTableView.setItems(measures);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        measureTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMeasureDetails(newValue));
    }

    private void showMeasureDetails(Measure info) {
        if (info != null) {
            this.measureId.setText(String.valueOf(info.getId()));
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
