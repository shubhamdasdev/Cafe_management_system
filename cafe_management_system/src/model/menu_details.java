package model;

public class menu_details {

	private String food_name;
	public String getFood_name() {
		return food_name;
	}
	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}
	public double getPrice() {
		return food_price;
	}
	public void setPrice(double price) {
		this.food_price = price;
	}
	public String getSpice_level() {
		return spice_level;
	}
	public void setSpice_level(String spice_level) {
		this.spice_level = spice_level;
	}
	public int getQuatity() {
		return Food_quatity;
	}
	public void setQuatity(int quatity) {
		this.Food_quatity = quatity;
	}
	public String getFood_type() {
		return food_type;
	}
	public void setFood_type(String food_type) {
		this.food_type = food_type;
	}
	public int getFood_ID() {
		return food_ID;
	}
	public void setFood_ID(int food_ID) {
		this.food_ID = food_ID;
	}
	private double food_price;
	private String spice_level;
	private int Food_quatity;
	private String food_type;
	private int food_ID;
	private String drink_name;
	public String getDrink_name() {
		return drink_name;
	}
	public void setDrink_name(String drink_name) {
		this.drink_name = drink_name;
	}
	public int getDrink_quantity() {
		return Drink_quantity;
	}
	public void setDrink_quantity(int drink_quantity) {
		Drink_quantity = drink_quantity;
	}
	public double getDrink_price() {
		return drink_price;
	}
	public void setDrink_price(double drink_price) {
		this.drink_price = drink_price;
	}
	public String getDrink_type() {
		return drink_type;
	}
	public void setDrink_type(String drink_type) {
		this.drink_type = drink_type;
	}
	public int getDrink_ID() {
		return drink_ID;
	}
	public void setDrink_ID(int drink_ID) {
		this.drink_ID = drink_ID;
	}
	private int Drink_quantity;
	private double drink_price;
	private String drink_type;
	private int drink_ID;
	
}
