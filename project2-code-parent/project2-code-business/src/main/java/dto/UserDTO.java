package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import data.User;
import data.Content;

public class UserDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String password;
	private String email;
	private String creditCard;
	private int loginCount;
	@Temporal(TemporalType.DATE)
	private Date registerDate;
	private List<Content> watchList;

	public UserDTO() {
		super();
	}

	public UserDTO(User u) {
		this(u.getID(), u.getUserame(), u.getPassword(), u.getEmail(), u.getCreditCard(), u.getLoginCount(),u.getDate());
	}

	public UserDTO(int ID, String username, String password, String email, String creditCard, int loginCount,Date registerDate) {
		super();
		this.id = ID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.creditCard = creditCard;
		this.watchList = new ArrayList<>();
		this.loginCount = loginCount;
		this.registerDate=registerDate;
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public void setDate(Date date) {
		this.registerDate=date;
	}
	public Date getDate() {
		return registerDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + loginCount;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((watchList == null) ? 0 : watchList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (creditCard == null) {
			if (other.creditCard != null)
				return false;
		} else if (!creditCard.equals(other.creditCard))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (loginCount != other.loginCount)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (watchList == null) {
			if (other.watchList != null)
				return false;
		} else if (!watchList.equals(other.watchList))
			return false;
		return true;
	}
	
	
}
