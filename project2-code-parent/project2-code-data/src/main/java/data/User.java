package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Player
 *
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Temporal(TemporalType.DATE)
	private Date registerDate;
	private String username;
	private String password;
	private String email;
	private String creditCard;
	private int loginCount;

	@ManyToMany
	@JoinTable(name="user_content", joinColumns=
	{@JoinColumn(name="user_id")}, inverseJoinColumns=
	{@JoinColumn(name="content_id")})
	private List<Content> watchList;

	public User() {
		super();
	}

	public User(String username, String password, String email, String creditCard,Date date) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.creditCard = creditCard;
		this.watchList = new ArrayList<>();
		this.loginCount = 0;
		this.registerDate=date;
	}
	
	public void updateLoginCount()
	{
		this.loginCount += 1;
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

	public String getUserame() {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setDate(Date date) {
		this.registerDate=date;
	}
	public Date getDate() {
		return registerDate;
	}

	@Override
	public String toString() {
		return " id = " + this.id + " user = " + this.username + " email = " + this.email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
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
		User other = (User) obj;
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