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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController {

	@FXML
	private AnchorPane homeAnchorPane;

	@FXML
	private Button manageUserButton;

	@FXML
	private Button menuButton;

	@FXML
	private Button placeOrderButton;

	@FXML
	private Text greetings;

	@FXML
	private TextField drinkID;

	@FXML
	private ListView<String> currentOrders;

	@FXML
	private TextField tableNumber;

	@FXML
	private TextField quantity;

	@FXML
	private TextField foodID;

	@FXML
	private TextField orderDetails;

	@FXML
	public void initialize() {
		try {
			ObservableList<String> orderList = FXCollections.observableArrayList();
			ResultSet resultSet = Database.executeQuery("SELECT * FROM cms_orders");
			orderList.add("ID	|	FOOD	|	DRINK	|	TABLE	|	QNTY	|	DETAILS	|	STATUS");
			while (resultSet.next()) {
				orderList.add(resultSet.getString("order_id") + "	|	" + resultSet.getString("food_id") + "	|	"
						+ resultSet.getString("drink_id") + "	|	" + resultSet.getString("table_number") + "	|	"
						+ resultSet.getString("quantity") + "	|	" + resultSet.getString("order_details")
						+ "	|	" + resultSet.getString("status"));
			}

			currentOrders.setItems(orderList);

			currentOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue != null) {
					String[] parts = newValue.split(" \\| ");
					if (parts.length > 0) {
						String username = parts[0].trim(); // Extract the username from the selected item
						try {
							ResultSet orders = Database.executeQuery("SELECT * FROM cms_orders", username);
							if (orders.next()) {
								foodID.setText(orders.getString("food_id"));
								drinkID.setText(orders.getString("drink_id"));
								tableNumber.setText(orders.getString("table_number"));
								quantity.setText(orders.getString("quantity"));
								orderDetails.setText(orders.getString("order_details"));
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
	void handlePlaceOrder(ActionEvent event) {
		String drinkIdText = drinkID.getText();
		Integer drinkId = null; // Set initially to null
		if (!drinkIdText.isEmpty()) {
			drinkId = Integer.parseInt(drinkIdText);
		}
		String foodIdText = foodID.getText();
		Integer foodId = null; // Set initially to null
		if (!foodIdText.isEmpty()) {
			foodId = Integer.parseInt(foodIdText);
		}

		int tableNum = Integer.parseInt(tableNumber.getText()); // Get table number from TextField
		int orderQty = Integer.parseInt(quantity.getText()); // Get order quantity from TextField
		String orderDetailsText = orderDetails.getText(); // Get order details from TextArea
		// Check if all necessary fields are filled

		try {
			// Establish database connection
			Connection connection = Database.getConnection();

			// Prepare SQL statement for order insertion
			String query = "INSERT INTO cms_orders (drink_id, food_id, table_number, quantity, order_details, status) VALUES (?, ?, ?, ?, ?, 'in-progress')";
			PreparedStatement statement = connection.prepareStatement(query);

			// Set parameters for the SQL statement
			if (drinkId != null) {
				statement.setInt(1, drinkId);
			} else {
				statement.setObject(1, null); // Set as NULL in the database if drinkId is null
			}
			if (foodId != null) {
				statement.setInt(2, foodId);
			} else {
				statement.setObject(2, null); // Set as NULL in the database if drinkId is null
			}
			statement.setInt(3, tableNum);
			statement.setInt(4, orderQty);
			statement.setString(5, orderDetailsText);

			// Execute the SQL statement
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Success", "Order Placed", "Order placed successfully!");
				clearOrderFields();
				initialize();
			} else {
				showAlert(Alert.AlertType.ERROR, "Error", "Order Failed", "Failed to place the order.");
			}
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, "Error", "Database Error",
					"An error occurred while processing the order.");
		}
	}

	// Utility method to clear order input fields
	private void clearOrderFields() {
		drinkID.clear();
		foodID.clear();
		tableNumber.clear();
		quantity.clear();
		orderDetails.clear();
	}

	// Utility method to show an alert (similar to what you had in other
	// controllers)
	private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	@FXML
	private void handleManageUserView(ActionEvent event) throws SQLException, IOException {

		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent viewHomeScene = fxmlLoader.load((getClass().getResource("../view/Manage User.fxml").openStream()));
		Stage currentStage = (Stage) homeAnchorPane.getScene().getWindow();
		currentStage.setTitle("Manage User");
		currentStage.setScene(new Scene(viewHomeScene, 900, 900));
		currentStage.show();
	}

	@FXML
	private void handleManageOrder(ActionEvent event) throws SQLException, IOException {

		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent viewHomeScene = fxmlLoader.load((getClass().getResource("../view/Order Details.fxml").openStream()));
		Stage currentStage = (Stage) homeAnchorPane.getScene().getWindow();
		currentStage.setTitle("Manage Orders");
		currentStage.setScene(new Scene(viewHomeScene, 900, 900));
		currentStage.show();
	}

	@FXML
	private void handleMenuView(ActionEvent event) throws SQLException, IOException {

		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent viewHomeScene = fxmlLoader.load((getClass().getResource("../view/Menu.fxml").openStream()));
		Stage currentStage = (Stage) homeAnchorPane.getScene().getWindow();
		currentStage.setTitle("Menu");
		currentStage.setScene(new Scene(viewHomeScene, 900, 900));
		currentStage.show();
	}

}
