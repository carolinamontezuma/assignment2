package ejb;

import javax.ejb.Remote;

@Remote
public interface UserEJBRemote {
	public void addAccount(String username, String email, String creditCard);

	public void editPersonalInformation(int userID, String username, String email, String creditCard);

	public void deleteAccount(int userID);

	public void populate();
}
