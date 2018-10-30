package ejb;

import javax.ejb.Remote;

@Remote
public interface ManagerEJBRemote {
	public void addAccount(String username, String password);

	public void deleteAccount(int managerID);
	
	public void addNewContent(String title, String director, int year, String category);
	
	public void removeContent(int contentID);

	public void populate();
}
