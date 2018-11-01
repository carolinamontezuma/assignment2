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

	public ManagerDTO() {
		super();
	}

	public ManagerDTO(Manager m) {
		this(m.getID(), m.getUsername());
	}

	public ManagerDTO(int ID, String username) {
		super();
		this.id = ID;
		this.username = username;
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

	@Override
	public String toString() {
		return " id = " + this.id + " user = " + this.username;
	}
}
