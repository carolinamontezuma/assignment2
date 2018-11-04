package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data.User;
import data.Content;

public class UserDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String email;
	private String creditCard;
	private int loginCount;

	private List<Content> watchList;

	public UserDTO() {
		super();
	}

	public UserDTO(User u) {
		this(u.getID(), u.getUserame(), u.getEmail(), u.getCreditCard(), u.getLoginCount());
	}

	public UserDTO(int ID, String username, String email, String creditCard, int loginCount) {
		super();
		this.id = ID;
		this.username = username;
		this.email = email;
		this.creditCard = creditCard;
		this.watchList = new ArrayList<>();
		this.loginCount = loginCount;
	}
	
	public int getLoginCount()
	{
		return this.loginCount;
	}
	
	public boolean hasLoggedInSinceRegister()
	{
		return this.loginCount > 0;
	}

	public int getID() {
		return this.id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public List<Content> getWatchList() {
		return watchList;
	}

	public void setWatchList(List<Content> watchList) {
		this.watchList = watchList;
	}

	@Override
	public String toString() {
		return " id = " + this.id + " user = " + this.username + " email = " + this.email;
	}
}
