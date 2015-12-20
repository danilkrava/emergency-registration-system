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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import model.Emergency;
import model.SeverityType;

import java.io.IOException;

/**
 * Created by Крава on 06.12.2015.
 */
public class EmFormController {

    private Stage primaryStage;
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

    @FXML
    private void addEm() {

    }

    @FXML
    private void addOrganisation() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_organisation_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add organisation");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddOrganisationController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void addSeverityType() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_severity_type_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add severity type");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddSeverityTypeController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addTimeType() {

    }

    @FXML
    private void addAreaType() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_area_type_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add area type");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddAreaTypeController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addMeasure() {

    }

    @FXML
    private void addReport() {

    }

    @FXML
    private void addDamagedPerson() {

    }

}
