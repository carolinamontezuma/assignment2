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
	public List <Content> seeContentFromYears(int year1, int year2);
	public void addNewContent(String title, String director, int year, String category);
	public List<ContentDTO> seeUserWatchList(int userID);
	public void addContentToWatchList(int contentID, int userID);
	public List<ContentDTO> seeContentByDirector(String director);
}
