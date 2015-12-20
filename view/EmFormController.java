package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Крава on 06.12.2015.
 */
public class EmFormController {

    private class Info{
        public StringProperty field1;
        public StringProperty field2;
        public Info(String field1, String field2){
            this.field1 = new SimpleStringProperty(field1);
            this.field2 = new SimpleStringProperty(field2);
        }
    }
    @FXML
    private TableView<Info> emergencies;

    @FXML
    private TableColumn<Info, String> col1;
    @FXML
    private TableColumn<Info, String> col2;

    @FXML
    private Label label;

    private ObservableList<Info> list = FXCollections.observableArrayList();

    public void initialize(){
        col1.setCellValueFactory(cellData -> cellData.getValue().field1);
        col2.setCellValueFactory(cellData -> cellData.getValue().field2);
        list.add(new Info("fuck","fuck"));
        list.add(new Info("fuck","fuck"));
        list.add(new Info("fuck","fuck"));
        list.add(new Info("fuck","fuck"));
        list.add(new Info("fuck","fuck"));
         emergencies.setItems(list);

        emergencies.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(Info info) {
        if (info != null) {

            label.setText(info.field1.getValue());

        } else {

            label.setText("");

        }
    }

}
