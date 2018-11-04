package ejb;

import javax.ejb.Remote;

import dto.ManagerDTO;

@Remote
public interface ManagerEJBRemote {
	public void populate();
	
	public void addAccount(String username, String email, String password);

	public void deleteAccount(int managerID);
	
	public boolean validateLogin(String username, String password);

	ManagerDTO getManagerByID(int managerID);

	ManagerDTO getManagerByEmail(String email);
}
