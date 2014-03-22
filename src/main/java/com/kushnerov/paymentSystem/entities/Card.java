package  com.kushnerov.paymentSystem.entities;

import java.io.Serializable;


public class Card extends Identity implements Serializable {

	
	
	private static final long serialVersionUID = 533240278675441814L;
	private Client cardHolder;
	private long number;
	private String expirationDate;
	private Account account;
	private PaymentSystem paymentSystem;
	private String type;
	
	public Client getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(Client cardHolder) {
		this.cardHolder = cardHolder;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public PaymentSystem getPaymentSystem() {
		return paymentSystem;
	}
	public void setPaymentSystem(PaymentSystem paymentSystem) {
		this.paymentSystem = paymentSystem;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result
				+ ((cardHolder == null) ? 0 : cardHolder.hashCode());
		result = prime * result
				+ ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + (int) (number ^ (number >>> 32));
		result = prime * result
				+ ((paymentSystem == null) ? 0 : paymentSystem.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (cardHolder == null) {
			if (other.cardHolder != null)
				return false;
		} else if (!cardHolder.equals(other.cardHolder))
			return false;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (number != other.number)
			return false;
		if (paymentSystem == null) {
			if (other.paymentSystem != null)
				return false;
		} else if (!paymentSystem.equals(other.paymentSystem))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
