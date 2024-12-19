package model;

public class Vendor {
	private String username;
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public int getContact() {
		return contact;
	}
	public void setContact(int contact) {
		this.contact = contact;
	}
	public String getVendor_type() {
		return Vendor_type;
	}
	public void setVendor_type(String vendor_type) {
		Vendor_type = vendor_type;
	}
	public double getPending_amt() {
		return pending_amt;
	}
	public void setPending_amt(double pending_amt) {
		this.pending_amt = pending_amt;
	}
	public double getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(double total_cost) {
		this.total_cost = total_cost;
	}
	public int getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
	}
	private String email_id;
    private int contact;
    private String Vendor_type;
    private double pending_amt;
    private double total_cost;
    private int user_ID;
    
    
}
