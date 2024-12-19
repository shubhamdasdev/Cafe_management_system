package view;

import controller.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class loginView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ensure tables are created before starting the application
        Database.createTablesIfNotExist();

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setTitle("Cafe Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
