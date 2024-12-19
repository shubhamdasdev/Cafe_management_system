package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane loginAnchorPane;

    // Handle login
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws SQLException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText(); // Plain password, consider hashing

        try {
            ResultSet resultSet = Database.executeQuery("SELECT password FROM cms_users WHERE username = ?", username);
            if (resultSet.next() && password.equals(resultSet.getString("password"))) {
                System.out.println("Login successful!");
                loadHomeView(event);
            } else {
                System.out.println("Invalid credentials!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load the home view
    private void loadHomeView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent homeView = fxmlLoader.load(getClass().getResource("../view/home.fxml").openStream());
        Stage currentStage = (Stage) loginAnchorPane.getScene().getWindow();
        currentStage.setTitle("Home");
        currentStage.setScene(new Scene(homeView, 900, 600));
        currentStage.show();
    }
}
