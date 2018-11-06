package ejb;

import javax.ejb.Remote;

import dto.ManagerDTO;

@Remote
public interface ManagerEJBRemote {
	void populate();
	
	void addAccount(String username, String email, String password);

	void deleteAccount(int managerID);
	
	boolean validateLogin(String username, String password);

	ManagerDTO getManagerByID(int managerID);

	ManagerDTO getManagerByEmail(String email);

	boolean isUsernameValid(String username);

	boolean isPasswordValid(String password);

	boolean isEmailValid(String email);
}
