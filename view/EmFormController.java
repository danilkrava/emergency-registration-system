package view;

import controller.MainController;
import dao.*;
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
import model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Created by Крава on 06.12.2015.
 */
public class EmFormController {

    private Stage primaryStage;
    private Stage dialogStage;
    MainController controller = new MainController();
    @FXML
    private TableView<Emergency> emergencyTableView;

    @FXML
    private TableView<Organisation> organisationTableView;

    @FXML
    private TableColumn<Organisation, String> orgName;

    @FXML
    private TableColumn<Emergency, String> emergencyDate;

    @FXML
    private TableColumn<Emergency, String> emergencyPlace;

    @FXML
    private Label emergencyId;

    @FXML
    private ComboBox<SeverityType> severityName;

    @FXML
    private ComboBox<AreaType> areaName;

    @FXML
    private ComboBox<Organisation> organisationName;

    @FXML
    private TextField organisationAdress;

    @FXML
    private TextField organisationRegion;

    @FXML
    private Label damagedPeopleCount;

    ////////////////////////////////


    @FXML
    private TextField organisationId;

    @FXML
    private TextField organisationNamePane2;

    @FXML
    private TextField organisationAdressPane2;

    @FXML
    private TextField regionName;

    //////////////////////////////////////////

    @FXML
    private DatePicker filterDateFrom;
    @FXML
    private DatePicker filterDateTo;
    @FXML
    private ComboBox<AreaType> filterArea;
    @FXML
    private ComboBox<SeverityType> filterSeverity;
    @FXML
    private ComboBox<Organisation> filterOrganisation;

    /////////////////////////////////////////

    @FXML
    private TextField filterName;
    @FXML
    private ComboBox<Region> filterRegion;

    private ObservableList<Emergency> emergencies = FXCollections.observableArrayList();
    private ObservableList<Organisation> organisations = FXCollections.observableArrayList();
    private ObservableList<Person> damagedPeople = FXCollections.observableArrayList();
    private ObservableList<SeverityType> severities = FXCollections.observableArrayList();
    private ObservableList<AreaType> areas = FXCollections.observableArrayList();
    private ObservableList<Region> regions = FXCollections.observableArrayList();
    public void initialize() {
        emergencyDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        emergencyPlace.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganisation().getName()));
        orgName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        try (Connection con = DaoFactory.getConnection()) {

            EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(con);
            OrganisationDao organisationDao = DaoFactory.getOrganisationDao(con);
            SeverityTypeDao severityDao = DaoFactory.getSeverityTypeDao(con);
            AreaTypeDao areaDao = DaoFactory.getAreaTypeDao(con);
            RegionDao regionDao = DaoFactory.getRegionDao(con);

            emergencies.addAll(emergencyDao.getAll());
            organisations.addAll(organisationDao.getAll());
            severities.addAll(severityDao.getAll());
            areas.addAll(areaDao.getAll());
            regions.addAll(regionDao.getAll());

            emergencyTableView.setItems(emergencies);
            organisationTableView.setItems(organisations);
            severityName.setItems(severities);
            areaName.setItems(areas);
            organisationName.setItems(organisations);

            filterArea.setItems(areas);
            filterOrganisation.setItems(organisations);
            filterRegion.setItems(regions);
            filterSeverity.setItems(severities);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        emergencyTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEmergencyDetails(newValue));
        organisationTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrganisationDetails(newValue));
    }

    private void showEmergencyDetails(Emergency info) {
        if (info != null) {
            try (Connection con = DaoFactory.getConnection()) {
                PersonDao personDao = DaoFactory.getPersonDao(con);
                damagedPeople.addAll(personDao.getByEmergency(info.getId()));
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            this.emergencyId.setText(String.valueOf(info.getId()));
            this.areaName.setValue(info.getAreaType());
            this.organisationName.setValue(info.getOrganisation());
            this.organisationAdress.setText(info.getOrganisation().getAddress());
            this.organisationRegion.setText(info.getOrganisation().getRegion().getName());
            this.severityName.setValue(info.getSeverityType());
            this.damagedPeopleCount.setText(String.valueOf(damagedPeople.size()));
        } else {
            //label.setText("");
        }
    }

    private void showOrganisationDetails(Organisation info) {
        if (info != null) {
            this.organisationId.setText(String.valueOf(info.getId()));
            this.organisationNamePane2.setText(info.getName());
            this.organisationAdressPane2.setText(String.valueOf(info.getAddress()));
            this.regionName.setText(info.getRegion().getName());

        } else {
            //label.setText("");
        }
    }

    @FXML
    private void updateOrganisationName() {
        this.organisationAdress.setText(organisationName.getValue().getAddress());
        this.organisationRegion.setText(organisationName.getValue().getRegion().getName());
    }

    @FXML
    private void showDamagedPeople() {
        System.out.println("ИЛюша членяра!");
    }

    @FXML
    private void addMeasure() {

    }

    @FXML
    private void addEm() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_emergency_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add emergency");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddEmergencyController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_time_type_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add time type");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddTimeTypeController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void addReport() {

    }

    @FXML
    private void addDamagedPerson() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_person_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add person");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddPersonController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void filtrEmergencies() {
        try (Connection con = DaoFactory.getConnection()) {
            EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(con);
            emergencies.clear();
            emergencies.addAll(emergencyDao.filter(
                    filterDateFrom.getValue() == null ? null : new Date(Date.from(filterDateFrom.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()),
                    filterDateTo.getValue() == null ? null : new Date(Date.from(filterDateTo.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()),
                    filterOrganisation.getValue() == null ? -1 : filterOrganisation.getValue().getId(),
                    filterArea.getValue() == null ? -1 : filterArea.getValue().getId(),
                    filterSeverity.getValue() == null ? -1 : filterSeverity.getValue().getId()));
            emergencyTableView.setItems(emergencies);
            // emergencies.addAll(emergencyDao.filter(new Date(Date.from(instant).getTime()),)));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void filtrOrganisations() {
        try (Connection con = DaoFactory.getConnection()) {
            OrganisationDao organisationDao = DaoFactory.getOrganisationDao(con);
            organisations.clear();
            organisations.addAll(organisationDao.filter(
                    filterName.getText() == "" ? "" : filterName.getText(),
                    filterRegion.getValue() == null ? -1 : filterRegion.getValue().getId()));
            organisationTableView.setItems(organisations);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
