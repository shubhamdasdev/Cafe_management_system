package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ManageUserController {

	@FXML
	private Pane manageUserPane;

	@FXML
	private TextField email;

	@FXML
	private Button home;

	@FXML
	private TextField userType;

	@FXML
	private TextField age;

	@FXML
	private TextField address;

	@FXML
	private TextField shift;

	@FXML
	private TextField dressCode;

	@FXML
	private TextField contact;

	@FXML
	private TextField ssn;

	@FXML
	private TextField userName;

	@FXML
	private TextField gender;

	@FXML
	private TextField salary;

	@FXML
	private TextField password;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private ListView<String> userDetails;

	@FXML
	public void initialize() {
		try {
			ObservableList<String> userList = FXCollections.observableArrayList();
			ResultSet resultSet = Database.executeQuery("SELECT * FROM cms_users");
			while (resultSet.next()) {
				userList.add(resultSet.getString("username") + " | " + resultSet.getString("first_name") + " | "
						+ resultSet.getString("email") + " | " + resultSet.getString("age") + " | "
						+ resultSet.getString("address") + " | " + resultSet.getString("shift") + " | "
						+ resultSet.getString("dress_code") + " | " + resultSet.getString("mobile_number") + " | "
						+ resultSet.getString("ssn") + " | " + resultSet.getString("gender") + " | "
						+ resultSet.getString("salary"));
			}

			userDetails.setItems(userList);

			userDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue != null) {
					String[] parts = newValue.split(" \\| ");
					if (parts.length > 0) {
						String username = parts[0].trim(); // Extract the username from the selected item
						try {
							ResultSet userDetails = Database.executeQuery("SELECT * FROM cms_users WHERE username = ?",
									username);
							if (userDetails.next()) {
								userName.setText(userDetails.getString("username"));
								firstName.setText(userDetails.getString("first_name"));
								lastName.setText(userDetails.getString("last_name"));
								email.setText(userDetails.getString("email"));
								userType.setText(userDetails.getString("type"));
								age.setText(userDetails.getString("age"));
								address.setText(userDetails.getString("address"));
								shift.setText(userDetails.getString("shift"));
								dressCode.setText(userDetails.getString("dress_code"));
								contact.setText(userDetails.getString("mobile_number"));
								ssn.setText(userDetails.getString("ssn"));
								gender.setText(userDetails.getString("gender"));
								salary.setText(userDetails.getString("salary"));
								password.setText(userDetails.getString("password"));
								// Set other user details as needed
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleAddUser(ActionEvent event) {
		String query = "INSERT INTO cms_users (email, type, age, address, shift, dress_code, mobile_number, ssn, username, gender, salary, first_name, last_name, password) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		try (Connection connection = Database.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, email.getText());
			statement.setString(2, userType.getText());
			statement.setInt(3, Integer.parseInt(age.getText()));
			statement.setString(4, address.getText());
			statement.setString(5, shift.getText());
			statement.setString(6, dressCode.getText());
			statement.setString(7, contact.getText());
			statement.setString(8, ssn.getText());
			statement.setString(9, userName.getText());
			statement.setString(10, gender.getText());
			statement.setDouble(11, Double.parseDouble(salary.getText()));
			statement.setString(12, firstName.getText());
			statement.setString(13, lastName.getText());
			statement.setString(14, password.getText());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Success", "User Added",
						"Successfully added user: " + userName.getText());
				initialize(); // Refresh the user list after deletion
			} else {
				showAlert(Alert.AlertType.ERROR, "Error", "User Not Added",
						"Failed to add user: " + userName.getText());
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while adding the user.");
		}
	}

	// Utility method to show an alert
	private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	@FXML
	void handleDeleteUser(ActionEvent event) {
		String selectedUser = userDetails.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			String[] parts = selectedUser.split(" \\| ");
			if (parts.length > 0) {
				String username = parts[0].trim(); // Extract the username from the selected item
				try (Connection connection = Database.getConnection();
						PreparedStatement statement = connection
								.prepareStatement("DELETE FROM cms_users WHERE username = ?")) {

					statement.setString(1, username);
					int rowsAffected = statement.executeUpdate();

					if (rowsAffected > 0) {
						showAlert(Alert.AlertType.INFORMATION, "Success", "User Deleted",
								"Successfully deleted user: " + username);
						// Clear fields or update the user list after deletion as needed
						initialize(); // Refresh the user list after deletion
					} else {
						showAlert(Alert.AlertType.ERROR, "Error", "User Not Deleted",
								"Failed to delete user: " + username);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					showAlert(Alert.AlertType.ERROR, "Error", "Database Error",
							"An error occurred while deleting the user.");
				}
			}
		} else {
			showAlert(Alert.AlertType.ERROR, "Error", "No User Selected", "Please select a user to delete.");
		}

	}

	@FXML
	void handleHomeView(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent viewHomeScene = fxmlLoader.load((getClass().getResource("../view/Home.fxml").openStream()));
		Stage currentStage = (Stage) manageUserPane.getScene().getWindow();
		currentStage.setTitle("Home");
		currentStage.setScene(new Scene(viewHomeScene, 900, 900));
		currentStage.show();
	}

	@FXML
	void handleUpdateUser(ActionEvent event) {
		String selectedUser = userDetails.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			String[] parts = selectedUser.split(" \\| ");
			if (parts.length > 0) {
				String username = parts[0].trim(); // Extract the username from the selected item
				try (Connection connection = Database.getConnection();
						PreparedStatement statement = connection.prepareStatement(
								"UPDATE cms_users SET email = ?, type = ?, age = ?, address = ?, shift = ?, dress_code = ?, mobile_number = ?, ssn = ?, gender = ?, salary = ?, username = ?, first_name = ?, last_name = ?, password = ? WHERE username = ?")) {

					statement.setString(1, email.getText());
					statement.setString(2, userType.getText());
					statement.setInt(3, Integer.parseInt(age.getText()));
					statement.setString(4, address.getText());
					statement.setString(5, shift.getText());
					statement.setString(6, dressCode.getText());
					statement.setString(7, contact.getText());
					statement.setString(8, ssn.getText());
					statement.setString(9, gender.getText());
					statement.setDouble(10, Double.parseDouble(salary.getText()));
					statement.setString(11, userName.getText());
					statement.setString(12, firstName.getText());
					statement.setString(13, lastName.getText());
					statement.setString(14, password.getText());
					statement.setString(15, username);
					int rowsAffected = statement.executeUpdate();
					if (rowsAffected > 0) {
						showAlert(Alert.AlertType.INFORMATION, "Success", "User Updated",
								"Successfully updated user: " + username);
						initialize(); // Refresh the user list after update
					} else {
						showAlert(Alert.AlertType.ERROR, "Error", "User Not Updated",
								"Failed to update user: " + username);
					}
				} catch (SQLException | NumberFormatException e) {
					e.printStackTrace();
					showAlert(Alert.AlertType.ERROR, "Error", "Database Error",
							"An error occurred while updating the user.");
				}
			}
		} else {
			showAlert(Alert.AlertType.ERROR, "Error", "No User Selected", "Please select a user to update.");
		}

	}

}
