package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Log yourself please");
        initRootLayout();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            //Load log form
            loader.setLocation(Main.class.getResource("view/logform/log_form.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();

            //Connection controller and main app
            LogFormController controller = loader.getController();
            controller.setApplication(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
