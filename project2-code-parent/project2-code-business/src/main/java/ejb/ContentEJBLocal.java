package ejb;

import java.util.List;

import javax.ejb.Local;

import dto.ContentDTO;

@Local
public interface ContentEJBLocal {
	void populate();
	boolean addNewContent(String title, String director, int year, String category);
	void editContent(int contentID, String title, String director, String category, int year);
	boolean removeContent(int contentID);
	List<ContentDTO> seeContentFromCategory(String category);
	List<ContentDTO> seeContentFromYears(int year1, int year2);
	List<ContentDTO> seeContentFromDirector(String director);
	void addContentToWatchList(int contentID, int userID);
	void removeContentFromWatchList(int contentID, int userID);
	List<String> getDirectorName(int ordem);
	List<String> getCategories(int ordem);
	List<String> getAvailableCategories();
	List<ContentDTO> aplicarFiltros();
	List<ContentDTO> aplicarFiltros(String diretor, String categoria, int minYear, int maxYear);
	List<ContentDTO> aplicarFiltros(String diretor, String categoria, int minYear, int maxYear, int opcao, boolean asc);
	List<ContentDTO> aplicarFiltrosWL(int userID);
	List<ContentDTO> aplicarFiltrosWL(int userID, int opcao, boolean asc);
	List<ContentDTO> getSuggestedCotent(int userID);
	long countList();
	boolean isTitleValid(String title);
	boolean isDirectorValid(String director);
	boolean isCategoryValid(String category);
	boolean isYearValid(int year);
	ContentDTO getContentByID(int contentID);
}
