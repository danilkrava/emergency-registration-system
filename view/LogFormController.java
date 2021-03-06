package view;

import dao.DaoFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 06.12.2015.
 */
public class LogFormController {
    private Main application;
    private AnchorPane rootLayout;
    private Stage primaryStage;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public void setApplication(Main application) {
        this.application = application;
    }

    @FXML
    private void logInButtonPressed() {
        DaoFactory.setLoginInfo(username.getText(), password.getText());
        try (Connection con = DaoFactory.getConnection()) {
            try {
                //Loading root frame for all frames
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(application.getClass().getResource("emergency_frame.fxml"));
                rootLayout = loader.load();

                application.getPrimaryStage().setTitle("Emergency frame");
                application.getPrimaryStage().setScene(new Scene(rootLayout));
                application.getPrimaryStage().setResizable(false);
                application.getPrimaryStage().show();

                EmFormController controller = loader.getController();
                controller.setPrimaryStage(primaryStage);
                Message.showConfirmationnMessage("Ви успішно увійшли у систему як " + username.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void cancelLogInButtonPressed() {
        System.exit(0);
    }
}
