package view;

import controller.MainController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import model.Emergency;
import model.SeverityType;

/**
 * Created by Крава on 06.12.2015.
 */
public class EmFormController {

    MainController controller = new MainController();
    @FXML
    private TableView<Emergency> emergencies;

    @FXML
    private TableColumn<Emergency, String> col1;

    @FXML
    private TableColumn<Emergency, String> col2;

    @FXML
    private Label emergencyId;

    @FXML
    private Label severityName;

    @FXML
    private Label areaName;

    @FXML
    private Label areaSize;

    @FXML
    private Label organisationName;

    @FXML
    private Label organisationAdress;

    @FXML
    private Label organisationRegion;


    private ObservableList<Emergency> list = FXCollections.observableArrayList();

    public void initialize(){
        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        col2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganisation().getName()));
        for (Emergency em :
                controller.getEmergencies()) {
            list.add(em);
        }
        emergencies.setItems(list);

        emergencies.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(Emergency info) {
        if (info != null) {
            this.emergencyId.setText(String.valueOf(info.getId()));
            this.areaSize.setText(String.valueOf(info.getAreaType().getArea()));
            this.areaName.setText(String.valueOf(info.getAreaType().getName()));
            this.organisationName.setText(info.getOrganisation().getName());
            this.organisationAdress.setText(info.getOrganisation().getAddress());
            this.organisationRegion.setText(info.getOrganisation().getRegion().getName());
            this.severityName.setText(info.getSeverityType().getName());

        } else {
            //label.setText("");
        }
    }

}
