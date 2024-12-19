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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController {

	@FXML
	private Pane menuPane;

	@FXML
	private ListView<String> mainMenu;

	@FXML
	private TextField drinkType;

	@FXML
	private TextField drinkQuantity;

	@FXML
	private TextField drinkPrice;

	@FXML
	private TextField drinkName;

	@FXML
	private TextField foodType;

	@FXML
	private TextField foodQuantity;

	@FXML
	private TextField foodPrice;

	@FXML
	private TextField foodSpice;

	@FXML
	private TextField foodName;

	@FXML
	void handleAddDrink(ActionEvent event) {
		String query = "INSERT INTO cms_drinks (name, price, quantity, type) VALUES (?, ?, ?,?)";
		String name = drinkName.getText();
		String type = drinkType.getText();
		Double price = Double.parseDouble(drinkPrice.getText());
		int quantity = Integer.parseInt(drinkQuantity.getText());
		try (Connection connection = Database.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);
			statement.setDouble(2, price);
			statement.setInt(3, quantity);
			statement.setString(4, type);
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				// Show success message in a dialog box
				showAlert(Alert.AlertType.INFORMATION, "Success", "Drink Added", "Successfully added: " + name);
				initialize();
			} else {
				showAlert(Alert.AlertType.INFORMATION, "Error", "Drink Not Added", "Error adding: " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void handleDeleteDrink(ActionEvent event) {
		String selectedItem = mainMenu.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String[] parts = selectedItem.split(" \\| ");
			if (parts.length > 0) {
				String nameToDelete = parts[0].trim(); // Extract the name of the item to delete
				try (Connection connection = Database.getConnection();
						PreparedStatement statement = connection
								.prepareStatement("DELETE FROM cms_drinks WHERE name = ?")) {
					statement.setString(1, nameToDelete);

					int rowsAffected = statement.executeUpdate();

					if (rowsAffected > 0) {
						showAlert(Alert.AlertType.INFORMATION, "Success", "Drink Deleted",
								"Successfully deleted: " + nameToDelete);
						// Update the displayed items in the ListView after deletion
						initialize();
					} else {
						showAlert(Alert.AlertType.INFORMATION, "Error", "Drink Not Deleted",
								"Error deleting: " + nameToDelete);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	void handleDeleteFood(ActionEvent event) {
		String selectedItem = mainMenu.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String[] parts = selectedItem.split(" \\| ");
			if (parts.length > 0) {
				String nameToDelete = parts[0].trim(); // Extract the name of the item to delete
				try (Connection connection = Database.getConnection();
						PreparedStatement statement = connection
								.prepareStatement("DELETE FROM cms_food_items WHERE name = ?")) {
					statement.setString(1, nameToDelete);

					int rowsAffected = statement.executeUpdate();

					if (rowsAffected > 0) {
						showAlert(Alert.AlertType.INFORMATION, "Success", "Food Item Deleted",
								"Successfully deleted: " + nameToDelete);
						// Update the displayed items in the ListView after deletion
						initialize();
					} else {
						showAlert(Alert.AlertType.INFORMATION, "Error", "Food Item Not Deleted",
								"Error deleting: " + nameToDelete);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@FXML
	void handleUpdateDrink(ActionEvent event) {
		String query = "UPDATE cms_drinks SET name = ?, price = ?, quantity = ?, type = ? WHERE name = ?";
		String name = drinkName.getText();
		String type = drinkType.getText();
		Double price = Double.parseDouble(drinkPrice.getText());
		int quantity = Integer.parseInt(drinkQuantity.getText());
		String selectedItem = mainMenu.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String[] parts = selectedItem.split(" \\| ");
			if (parts.length > 0) {
				String oldName = parts[0].trim(); // Extract the original name from the selected item
				try (Connection connection = Database.getConnection();
						PreparedStatement statement = connection.prepareStatement(query)) {
					statement.setString(1, name);
					statement.setDouble(2, price);
					statement.setInt(3, quantity);
					statement.setString(4, type);
					statement.setString(5, oldName); // Use the original name in the WHERE clause

					int rowsAffected = statement.executeUpdate();

					if (rowsAffected > 0) {
						showAlert(Alert.AlertType.INFORMATION, "Success", "Drink Updated",
								"Successfully updated: " + name);
						initialize();
					} else {
						showAlert(Alert.AlertType.INFORMATION, "Error", "Drink Not Updated", "Error updating: " + name);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@FXML
	void handleUpdateFood(ActionEvent event) {
		String query = "UPDATE cms_food_items SET name = ?, price = ?, quantity = ?, food_type = ?, spice_level = ? WHERE name = ?";
		String name = foodName.getText();
		String type = foodType.getText();
		String spice = foodSpice.getText();
		Double price = Double.parseDouble(foodPrice.getText());
		int quantity = Integer.parseInt(foodQuantity.getText());
		String selectedItem = mainMenu.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String[] parts = selectedItem.split(" \\| ");
			if (parts.length > 0) {
				String oldName = parts[0].trim(); // Extract the original name from the selected item
				try (Connection connection = Database.getConnection();
						PreparedStatement statement = connection.prepareStatement(query)) {
					statement.setString(1, name);
					statement.setDouble(2, price);
					statement.setInt(3, quantity);
					statement.setString(4, type);
					statement.setString(5, spice);
					statement.setString(6, oldName); // Use the original name in the WHERE clause

					int rowsAffected = statement.executeUpdate();

					if (rowsAffected > 0) {
						showAlert(Alert.AlertType.INFORMATION, "Success", "Food Item Updated",
								"Successfully updated: " + name);
						// Update the displayed items in the ListView after the update
						initialize();
					} else {
						showAlert(Alert.AlertType.INFORMATION, "Error", "Food Item Not Updated",
								"Error updating: " + name);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@FXML
	private void handleHomeView(ActionEvent event) throws SQLException, IOException {

		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent viewHomeScene = fxmlLoader.load((getClass().getResource("../view/Home.fxml").openStream()));
		Stage currentStage = (Stage) menuPane.getScene().getWindow();
		currentStage.setTitle("Home");
		currentStage.setScene(new Scene(viewHomeScene, 900, 900));
		currentStage.show();
	}

	@FXML
	private void handleAddFood(ActionEvent event) throws SQLException, IOException {
		String query = "INSERT INTO cms_food_items (name, price, quantity, food_type, spice_level) VALUES (?, ?, ?,?,?)";
		String name = foodName.getText();
		String type = foodType.getText();
		String spice = foodSpice.getText();
		Double price = Double.parseDouble(foodPrice.getText());
		int quantity = Integer.parseInt(foodQuantity.getText());
		try (Connection connection = Database.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);
			statement.setDouble(2, price);
			statement.setInt(3, quantity);
			statement.setString(4, type);
			statement.setString(5, spice);
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				// Show success message in a dialog box
				showAlert(Alert.AlertType.INFORMATION, "Success", "Food Item Added", "Successfully added: " + name);
				initialize();
			} else {
				showAlert(Alert.AlertType.INFORMATION, "Error", "Food Item Not Added", "Error adding: " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() throws SQLException {
		ObservableList<String> dataList = FXCollections.observableArrayList();
		ResultSet resultSet = Database.executeQuery("SELECT * FROM cms_food_items");
		dataList.add("----- Food -----");
		while (resultSet.next()) {
			dataList.add(resultSet.getString("name") + " | " + resultSet.getString("price") + " | "
					+ resultSet.getString("quantity") + " | " + resultSet.getString("food_type") + " | "
					+ resultSet.getString("spice_level"));
			// Add additional columns if needed
		}
		dataList.add("----- Drinks -----");
		resultSet = Database.executeQuery("SELECT * FROM cms_drinks");
		while (resultSet.next()) {
			dataList.add(resultSet.getString("name") + " | " + resultSet.getString("price") + " | "
					+ resultSet.getString("quantity") + " | " + resultSet.getString("type"));
		}
		mainMenu.setItems(dataList);

		mainMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Split the selected item to extract the ID and Name
				String[] parts = newValue.split(" \\| ");
				if (parts.length > 0) {
					String id = parts[0].trim(); // Extract the ID from the selected item

					try {
						ResultSet itemDetails = Database.executeQuery("SELECT * FROM cms_food_items WHERE name = ?",
								id);
						if (itemDetails.next()) {
							foodName.setText(itemDetails.getString("name"));
							foodPrice.setText(itemDetails.getString("price"));
							foodQuantity.setText(itemDetails.getString("quantity"));
							foodType.setText(itemDetails.getString("food_type"));
							foodSpice.setText(itemDetails.getString("spice_level"));
						} else {
							// If the ID doesn't match, check in drinks table
							itemDetails = Database.executeQuery("SELECT * FROM cms_drinks WHERE name = ?", id);
							if (itemDetails.next()) {
								drinkName.setText(itemDetails.getString("name"));
								drinkPrice.setText(itemDetails.getString("price"));
								drinkQuantity.setText(itemDetails.getString("quantity"));
								drinkType.setText(itemDetails.getString("type"));
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

}
