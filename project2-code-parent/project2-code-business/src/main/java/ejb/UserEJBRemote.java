package ejb;

import javax.ejb.Remote;

import dto.UserDTO;

@Remote
public interface UserEJBRemote {
	public void populate();
	
	public void addAccount(String username, String password, String email, String creditCard);

	public void editPersonalInformation(int userID, String username, String password, String email, String creditCard);

	public void deleteAccount(int userID);
	
	public boolean validateLogin(String email, String password);

	UserDTO getUserByID(int userID);

	UserDTO getUserByEmail(String email);

	boolean canRegister(String username, String email);

	void userLoggedIn(int userID);
}
