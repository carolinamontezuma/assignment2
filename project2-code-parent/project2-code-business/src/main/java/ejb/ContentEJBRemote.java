package ejb;

import java.util.List;

import javax.ejb.Remote;

import dto.ContentDTO;

@Remote
public interface ContentEJBRemote {
	public void populate();

	public List<ContentDTO> seeAllContent();

	public List<ContentDTO> seeContentFromCategory(String category);

	public List<ContentDTO> seeContentFromYears(int year1, int year2);

	public List<ContentDTO> seeContentFromDirector(String director);

	public List<ContentDTO> seeUserWatchList(int userID);

	public void addContentToWatchList(int contentID, int userID);

	public void removeContentFromWatchList(int contentID, int userID);
}
