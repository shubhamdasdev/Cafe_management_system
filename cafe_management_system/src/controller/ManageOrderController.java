package controller;

import java.io.IOException;
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

public class ManageOrderController {

	@FXML
	private TextField orderID;

	@FXML
	private Pane manageOrderPane;

	@FXML
	private TextField quantity;

	@FXML
	private TextField tableNumber;

	@FXML
	private TextField orderDetails;

	@FXML
	private TextField status;

	@FXML
	private ListView<String> orderslList;

	@FXML
	public void initialize() throws SQLException {
		ObservableList<String> dataList = FXCollections.observableArrayList();
		ResultSet resultSet = Database.executeQuery("SELECT * FROM cms_orders");
		while (resultSet.next()) {
			dataList.add(resultSet.getString("order_id") + " | " + resultSet.getString("table_number") + " | "
					+ resultSet.getString("food_id") + " | " + resultSet.getString("drink_id") + " | "
					+ resultSet.getString("quantity") + " | " + resultSet.getString("status") + " | "
					+ resultSet.getString("order_details"));

		}
		orderslList.setItems(dataList);

		orderslList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Split the selected item to extract the ID and Name
				String[] parts = newValue.split(" \\| ");
				if (parts.length > 0) {
					String id = parts[0].trim(); // Extract the ID from the selected item
					try {
						ResultSet itemDetails = Database.executeQuery("SELECT * FROM cms_orders WHERE order_id = ?",
								id);
						if (itemDetails.next()) { // Move the cursor to the first row
							orderID.setText(itemDetails.getString("order_id"));
							quantity.setText(itemDetails.getString("quantity"));
							tableNumber.setText(itemDetails.getString("table_number"));
							orderDetails.setText(itemDetails.getString("order_details"));
							status.setText(itemDetails.getString("status"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@FXML
	void handleHomeView(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent viewHomeScene = fxmlLoader.load((getClass().getResource("../view/Home.fxml").openStream()));
		Stage currentStage = (Stage) manageOrderPane.getScene().getWindow();
		currentStage.setTitle("Home");
		currentStage.setScene(new Scene(viewHomeScene, 900, 900));
		currentStage.show();
	}

	@FXML
	void handleDeleteOrder(ActionEvent event) {
		String selectedOrderId = orderID.getText(); // Get the selected order ID from the TextField
		try {
			// Perform deletion based on the selected order ID
			int deleted = Database.executeUpdate("DELETE FROM cms_orders WHERE order_id = ?", selectedOrderId);

			if (deleted >= 0) {
				// Refresh the order list after deletion
				initialize(); // Call initialize method to reload the orders
				clearOrderFields(); // Clear the order fields
				showAlert("Success", "Order Deleted", "Order successfully deleted.");
			} else {
				showAlert("Error", "Deletion Failed", "Failed to delete the order.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showAlert("Error", "Database Error", "An error occurred while deleting the order.");
		}
	}

	// Utility method to clear order input fields
	private void clearOrderFields() {
		orderID.clear();
		quantity.clear();
		tableNumber.clear();
		orderDetails.clear();
		status.clear();
	}

	// Utility method to show an alert
	private void showAlert(String title, String headerText, String contentText) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	@FXML
	void handleUpdateOrder(ActionEvent event) {
		String orderId = orderID.getText();
		String updatedQuantity = quantity.getText();
		String updatedTableNumber = tableNumber.getText();
		String updatedOrderDetails = orderDetails.getText();
		String updatedStatus = status.getText();

		try {
			int updated = Database.executeUpdate(
					"UPDATE cms_orders SET quantity = ?, table_number = ?, order_details = ?, status = ? WHERE order_id = ?",
					updatedQuantity, updatedTableNumber, updatedOrderDetails, updatedStatus, orderId);

			if (updated >= 0) {
				initialize(); // Reload the orders after update
				showAlert("Success", "Order Updated", "Order successfully updated.");
			} else {
				showAlert("Error", "Update Failed", "Failed to update the order.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showAlert("Error", "Database Error", "An error occurred while updating the order.");
		}

	}

}
