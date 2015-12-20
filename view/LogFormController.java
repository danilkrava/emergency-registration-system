package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import Main;

import java.io.IOException;

/**
 * Created by Крава on 06.12.2015.
 */
public class LogFormController {
    private Main application;
    private BorderPane rootLayout;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public void setApplication(Main application) {
        this.application = application;
    }

    @FXML
    private void logInButtonPressed() {
        try {
                //Loading root frame for all frames
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(application.getClass().getResource("view/root_frame.fxml"));
                rootLayout = loader.load();

                application.getPrimaryStage().setTitle("Emergency frame");
                application.getPrimaryStage().setScene(new Scene(rootLayout));
                application.getPrimaryStage().setResizable(false);
                application.getPrimaryStage().show();

                FXMLLoader loader1 = new FXMLLoader();
                loader1.setLocation(application.getClass().getResource("view/emergencyform/emergency_frame.fxml"));
                rootLayout.setCenter(loader1.load());

                EmFormController controller = loader.getController();
                controller.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void cancelLogInButtonPressed() {
        System.exit(0);
    }
}
