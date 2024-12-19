package model;

public class Transaction {

	private double credit;
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSender_ID() {
		return sender_ID;
	}
	public void setSender_ID(int sender_ID) {
		this.sender_ID = sender_ID;
	}
	public int getReceiver_ID() {
		return receiver_ID;
	}
	public void setReceiver_ID(int receiver_ID) {
		this.receiver_ID = receiver_ID;
	}
	public double getTotal_amt() {
		return total_amt;
	}
	public void setTotal_amt(double total_amt) {
		this.total_amt = total_amt;
	}
	public int getTransaction_ID() {
		return transaction_ID;
	}
	public void setTransaction_ID(int transaction_ID) {
		this.transaction_ID = transaction_ID;
	}
	private double debit;
	private  String status;
	private int sender_ID;
	private int receiver_ID;
	private double total_amt;
	private int transaction_ID;
	
}
