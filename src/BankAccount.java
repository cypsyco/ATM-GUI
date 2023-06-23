
public class BankAccount {
	
	//variables for BankAccount
	private String bankNumber;
	private int pinCode;
	private double balance;
	private User bankUser;
	
	//constructor for BankAccount
	public BankAccount(String bankNumber, int pinCode, double balance, User bankUser) {
		this.bankNumber = bankNumber;
		this.pinCode = pinCode;
		this.balance = balance;
		this.bankUser = bankUser;
	}
	
	//getter and setter methods for BankAccount
	public String getBankNumber() {
		return bankNumber;
	}
	
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	public int getPinCode() {
		return pinCode;
	}
	
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public User getBankUser() {
		return bankUser;
	}
	
	public void setBankUser(User bankUser) {
		this.bankUser = bankUser;
	}
}