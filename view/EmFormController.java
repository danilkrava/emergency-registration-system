package view;

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

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import org.controlsfx.control.CheckListView;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Created by Крава on 06.12.2015.
 */
public class EmFormController {

    private Stage primaryStage;
    private Stage dialogStage;


    @FXML
    private TabPane pane;

    @FXML
    private TableView<Emergency> emergencyTableView;

    @FXML
    private TableView<Organisation> organisationTableView;

    @FXML
    private TableView<TimeType> timeTypeTableView;

    @FXML
    private TableView<AreaType> areaTypeTableView;

    @FXML
    private TableView<SeverityType> severityTypeTableView;

    @FXML
    private TableView<Measure> measureTableView;

    ///////////////////////////////////////////////

    @FXML
    private TableColumn<Organisation, String> orgName;

    @FXML
    private TableColumn<Emergency, String> emergencyDate;

    @FXML
    private TableColumn<Emergency, String> emergencyPlace;

    @FXML
    private TableColumn<TimeType, String> timeTypeName;

    @FXML
    private TableColumn<AreaType, String> areaTypeName;

    @FXML
    private TableColumn<SeverityType, String> severityTypeName;

    @FXML
    private TableColumn<Measure, String> measureName;

    //////////////////////////////////////////////////

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
    private TextArea emergencyInformation;


    @FXML
    private Label damagedPeopleCount;

    @FXML
    private Label measuresCount;

    @FXML
    private Label reportsCount;

    @FXML
    private Label appliedMeasuresCount;
    ////////////////////////////////


    @FXML
    private Label organisationId;

    @FXML
    private TextField organisationNamePane2;

    @FXML
    private TextField organisationAdressPane2;

    @FXML
    private TextField regionName;

    //////////////////////////////////////////

    @FXML
    private Label timeId;

    @FXML
    private TextField timeName;

    @FXML
    private TextField timeElapsedTime;

    //////////////////////////////////////////

    @FXML
    private Label areaId;

    @FXML
    private TextField areasAreaName;

    @FXML
    private TextField areaSize;

    //////////////////////////////////////////

    @FXML
    private Label severityId;

    @FXML
    private TextField severitiesSeverityName;

    //////////////////////////////////////////

    @FXML
    private Label measureId;

    @FXML
    private ComboBox<SeverityType> measureSeverityType;

    @FXML
    private ComboBox<AreaType> measureAreaType;

    @FXML
    private ComboBox<TimeType> measureTimeType;

    @FXML
    private TextField measureInfo;

    ////////////////////////////////

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

    @FXML
    private GridPane emergencyGridPane;

    @FXML
    private GridPane organisationGridPane;

    @FXML
    private GridPane timeGridPane;

    @FXML
    private GridPane areaGridPane;

    @FXML
    private GridPane severityGridPane;

    @FXML
    private GridPane measuresGridPane;

    private ObservableList<Emergency> emergencies = FXCollections.observableArrayList();
    private ObservableList<Organisation> organisations = FXCollections.observableArrayList();
    private ObservableList<Person> damagedPeople = FXCollections.observableArrayList();
    private ObservableList<Measure> emergencyMeasures = FXCollections.observableArrayList();
    private ObservableList<SeverityType> severities = FXCollections.observableArrayList();
    private ObservableList<AreaType> areas = FXCollections.observableArrayList();
    private ObservableList<Region> regions = FXCollections.observableArrayList();
    private ObservableList<TimeType> timeTypes = FXCollections.observableArrayList();
    private ObservableList<Measure> measures = FXCollections.observableArrayList();
    private ObservableList<Report> reports = FXCollections.observableArrayList();
    private ObservableList<AppliedMeasure> appliedMeasures = FXCollections.observableArrayList();

    public void initialize() {
        emergencyDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        emergencyPlace.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganisation().getName()));
        orgName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        timeTypeName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        areaTypeName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        severityTypeName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        measureName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));

        try (Connection con = DaoFactory.getConnection()) {

            EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(con);
            OrganisationDao organisationDao = DaoFactory.getOrganisationDao(con);
            SeverityTypeDao severityDao = DaoFactory.getSeverityTypeDao(con);
            AreaTypeDao areaDao = DaoFactory.getAreaTypeDao(con);
            RegionDao regionDao = DaoFactory.getRegionDao(con);
            TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(con);
            MeasureDao measureDao = DaoFactory.getMeasureDao(con);


            emergencies.addAll(emergencyDao.getAll());
            organisations.addAll(organisationDao.getAll());
            severities.addAll(severityDao.getAll());
            areas.addAll(areaDao.getAll());
            regions.addAll(regionDao.getAll());
            timeTypes.addAll(timeTypeDao.getAll());
            measures.addAll(measureDao.getAll());

            emergencyTableView.setItems(emergencies);
            organisationTableView.setItems(organisations);
            areaTypeTableView.setItems(areas);
            severityTypeTableView.setItems(severities);
            timeTypeTableView.setItems(timeTypes);
            measureTableView.setItems(measures);

            severityName.setItems(severities);
            areaName.setItems(areas);
            organisationName.setItems(organisations);
            measureAreaType.setItems(areas);
            measureSeverityType.setItems(severities);
            measureTimeType.setItems(timeTypes);

            filterArea.setItems(areas);
            filterOrganisation.setItems(organisations);
            filterRegion.setItems(regions);
            filterSeverity.setItems(severities);

            setUnVisibleGridPanes();
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        }
        emergencyTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEmergencyDetails(newValue));
        organisationTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrganisationDetails(newValue));
        timeTypeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTimeTypeDetails(newValue));
        areaTypeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAreaTypeDetails(newValue));
        severityTypeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSeverityTypeDetails(newValue));
        measureTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMeasureDetails(newValue));
    }

    private void setUnVisibleGridPanes() {
        emergencyGridPane.setVisible(false);
        timeGridPane.setVisible(false);
        areaGridPane.setVisible(false);
        severityGridPane.setVisible(false);
        measuresGridPane.setVisible(false);
        organisationGridPane.setVisible(false);
    }
    private void showEmergencyDetails(Emergency info) {
        emergencyGridPane.setVisible(true);
        if (info != null) {
            try (Connection con = DaoFactory.getConnection()) {
                PersonDao personDao = DaoFactory.getPersonDao(con);
                MeasureDao measureDao = DaoFactory.getMeasureDao(con);
                ReportDao reportDao = DaoFactory.getReportDao(con);
                AppliedMeasureDao appliedMeasureDao = DaoFactory.getAppliedMeasureDao(con);

                appliedMeasures.clear();
                reports.clear();
                emergencyMeasures.clear();
                damagedPeople.clear();

                appliedMeasures.addAll(appliedMeasureDao.getByEmergency(info));
                reports.addAll(reportDao.getByEmergency(info));
                emergencyMeasures.addAll(measureDao.getByEmergency(info));
                damagedPeople.addAll(personDao.getByEmergency(info.getId()));
            } catch (SQLException e) {
                Message.showErrorMessage(e.getMessage());
            }

            this.emergencyId.setText(String.valueOf(info.getId()));
            this.areaName.setValue(info.getAreaType());
            this.organisationName.setValue(info.getOrganisation());
            this.organisationAdress.setText(info.getOrganisation().getAddress());
            this.organisationRegion.setText(info.getOrganisation().getRegion().getName());
            this.severityName.setValue(info.getSeverityType());
            this.damagedPeopleCount.setText(String.valueOf(damagedPeople.size()));
            this.measuresCount.setText(String.valueOf(emergencyMeasures.size()));
            this.reportsCount.setText(String.valueOf(reports.size()));
            this.appliedMeasuresCount.setText(String.valueOf(appliedMeasures.size()));
            this.emergencyInformation.setText(info.getInfo());

        } else {
            //label.setText("");
        }
    }

    private void showOrganisationDetails(Organisation info) {
        organisationGridPane.setVisible(true);
        if (info != null) {
            this.organisationId.setText(String.valueOf(info.getId()));
            this.organisationNamePane2.setText(info.getName());
            this.organisationAdressPane2.setText(String.valueOf(info.getAddress()));
            this.regionName.setText(info.getRegion().getName());

        } else {
            //label.setText("");
        }
    }

    private void showTimeTypeDetails(TimeType info) {
        timeGridPane.setVisible(true);
        if (info != null) {
            this.timeId.setText(String.valueOf(info.getId()));
            this.timeName.setText(info.getName());
            this.timeElapsedTime.setText(String.valueOf(info.getTimeElapsed()));

        } else {
            //label.setText("");
        }
    }

    private void showAreaTypeDetails(AreaType info) {
        areaGridPane.setVisible(true);
        if (info != null) {
            this.areaId.setText(String.valueOf(info.getId()));
            this.areasAreaName.setText(info.getName());
            this.areaSize.setText(String.valueOf(info.getArea()));
        } else {
            //label.setText("");
        }
    }

    private void showSeverityTypeDetails(SeverityType info) {
        severityGridPane.setVisible(true);
        if (info != null) {
            this.severityId.setText(String.valueOf(info.getId()));
            this.severitiesSeverityName.setText(info.getName());
        } else {
            //label.setText("");
        }
    }

    private void showMeasureDetails(Measure info) {
        measuresGridPane.setVisible(true);
        if (info != null) {
            this.measureId.setText(String.valueOf(info.getId()));
            this.measureTimeType.setValue(info.getTimeType());
            this.measureSeverityType.setValue(info.getSeverityType());
            this.measureAreaType.setValue(info.getAreaType());
            this.measureInfo.setText(info.getInfo());
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
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("damaged_people.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Постраждалі");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.

            DamagedPeopleController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEmergency(emergencyTableView.getSelectionModel().getSelectedItem());
            controller.start();
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showMeasures() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("measures.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Рекомендації");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            MeasuresController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setEmergency(emergencyTableView.getSelectionModel().getSelectedItem());
            controller.start();
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addMeasure() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_measure_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Додавання рекомендації");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddMeasureController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("add_report_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Додавання звіту");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddReportController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
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
            Message.showErrorMessage(e.getMessage());
        }
    }

    @FXML
    private void refresh() {
        organisations.clear();
        emergencies.clear();
        severities.clear();
        measures.clear();
        areas.clear();
        timeTypes.clear();
        regions.clear();
        initialize();
    }

    @FXML
    private void save() {
        try (Connection con = DaoFactory.getConnection()) {
            if (pane.getSelectionModel().getSelectedItem().getText().equals("Надзвичайні ситуації")) {
                EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(con);
                Emergency emergency = emergencyTableView.getSelectionModel().getSelectedItem();

                emergency.setAreaType(areaName.getValue());
                Organisation organisation = organisationName.getValue();
                organisation.setAddress(organisationAdress.getText());
                Region region = organisation.getRegion();
                region.setName(organisationRegion.getText());
                organisation.setRegion(region);
                emergency.setOrganisation(organisation);
                emergency.setSeverityType(severityName.getValue());
                emergency.setInfo(emergencyInformation.getText());

                emergencyDao.update(emergency);
                emergencyTableView.refresh();
            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Організації")) {
                OrganisationDao organisationDao = DaoFactory.getOrganisationDao(con);
                Organisation organisation = organisationTableView.getSelectionModel().getSelectedItem();
                organisation.setName(organisationNamePane2.getText());
                organisation.setAddress(organisationAdressPane2.getText());

                Region region = organisation.getRegion();
                region.setName(regionName.getText());
                organisation.setRegion(region);

                organisationDao.update(organisation);
                organisationTableView.refresh();
            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Типи за часом")) {
                TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(con);
                TimeType timeType = timeTypeTableView.getSelectionModel().getSelectedItem();

                timeType.setName(timeName.getText());
                timeType.setTimeElapsed(Integer.parseInt(timeElapsedTime.getText()));

                timeTypeDao.update(timeType);
                timeTypeTableView.refresh();

            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Типи за площею")) {
                AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(con);
                AreaType areaType = areaTypeTableView.getSelectionModel().getSelectedItem();

                areaType.setName(areasAreaName.getText());
                areaType.setArea(Double.parseDouble(areaSize.getText()));

                areaTypeDao.update(areaType);
                areaTypeTableView.refresh();

            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Типи за важкістю")) {
                SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(con);
                SeverityType severityType = severityTypeTableView.getSelectionModel().getSelectedItem();

                severityType.setName(severitiesSeverityName.getText());

                severityTypeDao.update(severityType);
                severityTypeTableView.refresh();
            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Рекомендації")) {
                MeasureDao measureDao = DaoFactory.getMeasureDao(con);
                Measure measure = measureTableView.getSelectionModel().getSelectedItem();

                measure.setInfo(measureInfo.getText());

                SeverityType severityType = measureSeverityType.getValue();
                AreaType areaType = measureAreaType.getValue();
                TimeType timeType = measureTimeType.getValue();

                measure.setSeverityType(severityType);
                measure.setAreaType(areaType);
                measure.setTimeType(timeType);

                measureDao.update(measure);
                severityTypeTableView.refresh();
            }
            Message.showInformationMessage("Запис успішно оновлено");
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            Message.showInformationMessage("Виберіть запис для оновлення");
        }


    }

    @FXML
    private void remove() {
        try (Connection con = DaoFactory.getConnection()) {
            if (pane.getSelectionModel().getSelectedItem().getText().equals("Надзвичайні ситуації")) {
                EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(con);
                Emergency emergency = emergencyTableView.getSelectionModel().getSelectedItem();

                emergencyDao.delete(emergency);
                emergencyTableView.refresh();
            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Організації")) {
                OrganisationDao organisationDao = DaoFactory.getOrganisationDao(con);
                Organisation organisation = organisationTableView.getSelectionModel().getSelectedItem();

                organisationDao.delete(organisation);
                organisationTableView.refresh();
            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Типи за часом")) {
                TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(con);
                TimeType timeType = timeTypeTableView.getSelectionModel().getSelectedItem();

                timeTypeDao.delete(timeType);
                timeTypeTableView.refresh();

            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Типи за площею")) {
                AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(con);
                AreaType areaType = areaTypeTableView.getSelectionModel().getSelectedItem();

                areaTypeDao.delete(areaType);
                areaTypeTableView.refresh();

            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Типи за важкістю")) {
                SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(con);
                SeverityType severityType = severityTypeTableView.getSelectionModel().getSelectedItem();

                severityTypeDao.delete(severityType);
                severityTypeTableView.refresh();
            } else if (pane.getSelectionModel().getSelectedItem().getText().equals("Рекомендації")) {
                MeasureDao measureDao = DaoFactory.getMeasureDao(con);
                Measure measure = measureTableView.getSelectionModel().getSelectedItem();

                measureDao.delete(measure);
                severityTypeTableView.refresh();
            }
            Message.showInformationMessage("Запис успішно видалено");
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            Message.showInformationMessage("Виберіть запис для видалення");
        }
    }

    @FXML
    private void showReports() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("show_report_frame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Звіти");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.

            ShowReportController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEmergency(emergencyTableView.getSelectionModel().getSelectedItem());
            controller.start();
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAppliedMeasures() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("applied_measures_list.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Застосовані рекомендації");
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.

            AppliedMeasuresListController controller = loader.getController();
            controller.setEmergency(emergencyTableView.getSelectionModel().getSelectedItem());
            controller.start();
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toFile() {
        try (Connection con = DaoFactory.getConnection()) {
            FileChooser fileChooser = new FileChooser();
            //FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DOC Files (*.doc)");
            //fileChooser.getExtensionFilters().add(extensionFilter);
            File file = fileChooser.showSaveDialog(primaryStage);

            ReportDao reportDao = DaoFactory.getReportDao(con);
            ObservableList<Report> reportsToSave = FXCollections.observableArrayList();
            reportsToSave.addAll(reportDao.getByEmergency(emergencyTableView.getSelectionModel().getSelectedItem()));

            DocCreator.createDocFile(file.getPath(), reportsToSave);
            Message.showConfirmationnMessage("Звіти збережено");
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        } catch (IOException e) {
            Message.showErrorMessage(e.getMessage());
        }
    }

    @FXML
    private void removeFilters() {
        filterDateFrom.setValue(null);
        filterDateTo.setValue(null);
        filterName.setText("");
        filterRegion.setValue(null);
        filterArea.setValue(null);
        filterOrganisation.setValue(null);
        filterSeverity.setValue(null);
        filtrEmergencies();
        filtrOrganisations();
    }
}
