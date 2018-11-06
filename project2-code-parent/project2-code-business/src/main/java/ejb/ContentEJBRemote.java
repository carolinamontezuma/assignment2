package ejb;

import java.util.List;

import javax.ejb.Remote;

import dto.ContentDTO;

@Remote
public interface ContentEJBRemote {
	public void populate();
	public int addNewContent(String title, String director, int year, String category);
	public void editContent(int opcao, int id, String info);
	public void removeContent(int contentID);
	public List<ContentDTO> seeAllContent(int ordem);
	public List<ContentDTO> seeContentFromCategory(String category);
	public List<ContentDTO> seeContentFromYears(int year1, int year2);
	public List<ContentDTO> seeContentFromDirector(String director);
	public void addContentToWatchList(int contentID, int userID);
	public void removeContentFromWatchList(int contentID, int userID);
	public List<String> getDirectorName(int ordem);
	public List<String> getCategories(int ordem);
	public List<ContentDTO> seeWatchList(int id);
	public List<ContentDTO> aplicarFiltros(String diretor, String categoria);
	public List<ContentDTO> orderTable(int opcaoFiltro, int opcaoOrdena);
}
