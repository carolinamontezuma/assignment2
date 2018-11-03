package ejb;

import javax.ejb.Remote;

@Remote
public interface ManagerEJBRemote {
	public void addAccount(String username, String password);

	public void deleteAccount(int managerID);
	
	public boolean validateLogin(String username, String password);
	
	public int addNewContent(String title, String director, int year, String category);
	
	public void removeContent(int contentID);

	public void populate();
}
