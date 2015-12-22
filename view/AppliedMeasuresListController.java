package view;

import dao.DaoFactory;
import dao.MeasureDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Emergency;
import model.Measure;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 21.12.2015.
 */
public class AppliedMeasuresListController {
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
    private TextField timeType;

    @FXML
    private TextField severityType;

    @FXML
    private TextField areaType;

    @FXML
    private Button applyButton;

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
            Message.showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            Message.showInformationMessage("Немає рекомендацій");
        }
        applyButton.setDisable(true);
        measureTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMeasureDetails(newValue));
    }

    private void showMeasureDetails(Measure info) {
        applyButton.setDisable(false);
        if (info != null) {
            this.measureId.setText(String.valueOf(info.getId()));
            this.name.setText(info.getInfo());
            this.timeType.setText(info.getTimeType().getName());
            this.severityType.setText(info.getSeverityType().getName());
            this.areaType.setText(info.getAreaType().getName());
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEmergency(Emergency emergency) {
        this.currentEmergency = emergency;
    }
}
