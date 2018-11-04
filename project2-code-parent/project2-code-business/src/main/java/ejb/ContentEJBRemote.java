package ejb;

import java.util.List;

import javax.ejb.Remote;

import dto.ContentDTO;

@Remote
public interface ContentEJBRemote {
	public void populate();
	void addNewContent(String title, String director, int year, String category);
	void removeContent(int contentID);
	public List<ContentDTO> seeAllContent(int ordem);
	public List<ContentDTO> seeContentFromCategory(String category);
	public List<ContentDTO> seeContentFromYears(int year1, int year2);
	public List<ContentDTO> seeContentFromDirector(String director);
	public List<ContentDTO> seeUserWatchList(int userID);
	public void addContentToWatchList(int contentID, int userID);
	public void removeContentFromWatchList(int contentID, int userID);
	public List<String> getDirectorName(int ordem);
	public List<String> getCategories(int ordem);
}
