package view;

import dao.DamageTypeDao;
import dao.DaoFactory;
import dao.PersonDao;
import dao.ReportDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DamageType;
import model.Emergency;
import model.Person;
import model.Report;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 22.12.2015.
 */
public class ShowReportController {
    Stage dialogStage;
    Emergency currentEmergency;

    @FXML
    private TableView<Report> reportTableView;

    @FXML
    private TableColumn<Report, String> reportStringTableColumn;

    @FXML
    private Label id;

    @FXML
    private TextField date;

    @FXML
    private TextField emergency;

    @FXML
    private TextField radiation;

    @FXML
    private TextArea information;

    ///////////////////////////////////////////////

    private ObservableList<Report> reports = FXCollections.observableArrayList();

    public void initialize() {

    }

    public void start() {
        reportStringTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));

        try (Connection con = DaoFactory.getConnection()) {
            ReportDao reportDao = DaoFactory.getReportDao(con);

            reports.addAll(reportDao.getByEmergency(currentEmergency));

            reportTableView.setItems(reports);
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            Message.showErrorMessage("Немає постраждалих");
        }
        reportTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showReportDetails(newValue));
    }

    private void showReportDetails(Report info) {
        if (info != null) {
            this.id.setText(String.valueOf(info.getId()));
            this.information.setText(info.getInfo());
            this.date.setText(String.valueOf(info.getDate()));
            this.radiation.setText(String.valueOf(info.getRadiation()));
            this.emergency.setText(info.getEmergency().toString());
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
