package ejb;

import java.util.List;

import javax.ejb.Local;

import dto.ContentDTO;

@Local
public interface ContentEJBLocal {	
	void populate();
	int addNewContent(String title, String director, int year, String category);
	void editContent(int opcao, int id, String info);
	void removeContent(int contentID);
	List<ContentDTO> seeAllContent(int ordem);
	List<ContentDTO> seeContentFromCategory(String category);
	List<ContentDTO> seeContentFromYears(int year1, int year2);
	List<ContentDTO> seeContentFromDirector(String director);
	void addContentToWatchList(int contentID, int userID);
	void removeContentFromWatchList(int contentID, int userID);
	List<String> getDirectorName(int ordem);
	List<String> getCategories(int ordem);
	List<ContentDTO> seeWatchList(int id);
	List<ContentDTO> orderTable(int opcaoFiltro, int opcaoOrdena);
	List<String> getAvailableCategories();
	List<ContentDTO> aplicarFiltros(String diretor, String categoria, int minYear, int maxYear, int opcao, boolean asc);
	List<ContentDTO> aplicarFiltros(String diretor, String categoria, int minYear, int maxYear);
	List<ContentDTO> getSuggestedCotent(int userID);
}
