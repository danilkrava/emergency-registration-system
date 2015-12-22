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
            Message.showErrorMessage(e.getMessage());
            dialogStage.close();
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
                Message.showConfirmationnMessage("Організацію додано");
            } catch (SQLException e) {
                Message.showErrorMessage(e.getMessage());
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
            errorMessage += "Ім'я\n";
        }
        if (adressField.getText() == null || adressField.getText().length() == 0) {
            errorMessage += "Місто\n";
        }
        if (regionField.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Область\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Message.showErrorMessage("Виправте невірно вказані поля: \n" + errorMessage);
            return false;
        }
    }
}
