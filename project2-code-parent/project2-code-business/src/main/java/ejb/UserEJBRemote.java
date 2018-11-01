package ejb;

import javax.ejb.Remote;

@Remote
public interface UserEJBRemote {
	public void addAccount(String username, String password, String email, String creditCard);

	public void editPersonalInformation(int userID, String username, String email, String creditCard);

	public void deleteAccount(int userID);
	
	public boolean validateLogin(String email, String password);

	public void populate();
}
