package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import data.User;

@Remote
public interface UserEJBRemote {
	public void editPersonalInformation(int userID, String username, String email, String creditCard);
	public void deleteAccount(int userID);
	public void populate();
}
