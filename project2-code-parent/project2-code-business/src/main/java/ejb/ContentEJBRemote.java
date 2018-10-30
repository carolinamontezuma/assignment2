package ejb;

import java.util.List;

import javax.ejb.Remote;

import data.Content;
import dto.ContentDTO;

@Remote
public interface ContentEJBRemote {
	public void populate();
	public List<ContentDTO> seeAllContent();
	public List <ContentDTO> seeContentFromCategory(String category);
	public List <ContentDTO> seeContentFromYears(int year1,int year2);
	public List <ContentDTO> seeContentFromDirector(String director);
	public void addNewContent(String title,String director,int year,String category);
	

	public List<ContentDTO> seeUserWatchList(int userID);
	public void addContentToWatchList(int contentID, int userID);
	public void removeContentFromWatchList(int contentID, int userID);
}
