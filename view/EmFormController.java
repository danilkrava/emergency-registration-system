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

import model.SeverityType;

/**
 * Created by Крава on 06.12.2015.
 */
public class EmFormController {

    MainController controller = new MainController();
    @FXML
    private TableView<SeverityType> emergencies;

    @FXML
    private TableColumn<SeverityType, String> col1;

    @FXML
    private Label info;

    @FXML
    private Label id;

    @FXML
    private Label label;

    private ObservableList<SeverityType> list = FXCollections.observableArrayList();

    public void initialize(){
        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));
        for (SeverityType sev :
                controller.getSeverities()) {
            list.add(sev);
        }
        emergencies.setItems(list);

        emergencies.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(SeverityType info) {
        if (info != null) {

            this.info.setText(info.getInfo());
            this.id.setText(String.valueOf(info.getId()));

        } else {
            label.setText("");
        }
    }

}
