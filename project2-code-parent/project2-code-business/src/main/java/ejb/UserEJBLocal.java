package ejb;

import javax.ejb.Local;

import dto.UserDTO;

@Local
public interface UserEJBLocal {
	void populate();
	
	void addAccount(String username, String password, String email, String creditCard);

	void editPersonalInformation(int userID, String username, String password, String email, String creditCard);

	void deleteAccount(int userID);
	
	boolean validateLogin(String email, String password);

	UserDTO getUserByID(int userID);

	UserDTO getUserByEmail(String email);

	boolean canRegister(String email);

	void userLoggedIn(int userID);
	
	boolean isUsernameValid(String username);
	
	boolean isPasswordValid(String password);

	boolean isEmailValid(String email);

	boolean isCreditCardValid(String creditCard);
}
