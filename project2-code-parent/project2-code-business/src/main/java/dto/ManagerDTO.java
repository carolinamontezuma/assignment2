package dto;

import java.io.Serializable;

import data.Manager;

public class ManagerDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String email;

	public ManagerDTO() {
		super();
	}

	public ManagerDTO(Manager m) {
		this(m.getID(), m.getUsername(), m.getEmail());
	}

	public ManagerDTO(int ID, String username, String email) {
		super();
		this.id = ID;
		this.username = username;
		this.email = email;
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

	@Override
	public String toString() {
		return " id = " + this.id + " user = " + this.username;
	}
}
