package view;

import dao.AppliedMeasureDao;
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
import model.AppliedMeasure;
import model.Emergency;
import model.Measure;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 21.12.2015.
 */
public class AppliedMeasuresListController {
    Stage primaryStage;
    Emergency currentEmergency;

    @FXML
    private TableView<AppliedMeasure> measureTableView;

    ///////////////////////////////////////////////

    @FXML
    private TableColumn<AppliedMeasure, String> measureNameColumn;

    ///////////////////////////////////////////////

    @FXML
    private Label measureId;

    @FXML
    private TextField name;

    @FXML
    private TextField moneyField;

    @FXML
    private TextField date;

    @FXML
    private TextArea info;



    ///////////////////////////////////////////////

    ObservableList<AppliedMeasure> measures = FXCollections.observableArrayList();

    public void initialize() {

    }

    public void start() {
        measureNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));
        try (Connection con = DaoFactory.getConnection()) {
            AppliedMeasureDao measureDao = DaoFactory.getAppliedMeasureDao(con);


            measures.addAll(measureDao.getByEmergency(currentEmergency));

            measureTableView.setItems(measures);
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            Message.showInformationMessage("Немає виконаних рекомендацій");
        }

        measureTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMeasureDetails(newValue));
    }

    private void showMeasureDetails(AppliedMeasure info) {
        if (info != null) {
            this.measureId.setText(String.valueOf(info.getId()));
            this.name.setText(info.getInfo());
            this.moneyField.setText(Double.toString(info.getMoney()));
            this.date.setText(info.getDate().toString());
            this.info.setText(info.getInfo());
        }
    }



    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEmergency(Emergency emergency) {
        this.currentEmergency = emergency;
    }
}
