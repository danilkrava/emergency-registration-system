package view;

import dao.DaoFactory;
import dao.EmergencyDao;
import dao.OrganisationDao;
import dao.RegionDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Emergency;
import model.Organisation;
import model.Region;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 20.12.2015.
 */
public class AddOrganisationController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField adressField;
    @FXML
    private ComboBox<Region> regionField;
    private ObservableList<Region> list = FXCollections.observableArrayList();

    private Stage dialogStage;
    //  private Person person;
    private boolean okClicked = false;

    public void initialize() {
        try (Connection con = DaoFactory.getConnection()) {
            RegionDao dao = DaoFactory.getRegionDao(con);
            list.addAll(dao.getAll());
            regionField.setItems(list);
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Organisation organisation = new Organisation(nameField.getText(), adressField.getText(), regionField.getValue());
            OrganisationDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getOrganisationDao(con);
                dao.add(organisation);
            } catch (SQLException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            okClicked = true;
            dialogStage.close();
        }
    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (adressField.getText() == null || adressField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (regionField.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid street!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}
