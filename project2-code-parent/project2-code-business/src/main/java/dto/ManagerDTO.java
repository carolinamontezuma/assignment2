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
	private String password;

	public ManagerDTO() {
		super();
	}

	public ManagerDTO(Manager m) {
		this(m.getID(), m.getUsername(), m.getPassword());
	}

	public ManagerDTO(int ID, String username, String password) {
		super();
		this.id = ID;
		this.username = username;
		this.password = password;
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

	@Override
	public String toString() {
		return " id = " + this.id + " user = " + this.username;
	}
}
