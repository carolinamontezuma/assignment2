package ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.LoggerFactory;

import data.Content;
import data.User;
import dto.ContentDTO;
import dto.UserDTO;

/**
 * Session Bean implementation class ContentEJB
 */
@Stateless
@LocalBean
public class ContentEJB implements ContentEJBLocal {
	@PersistenceContext(name = "Contents")
	EntityManager em;

	private org.slf4j.Logger logger;
	
	/**
	 * Default constructor.
	 */
	public ContentEJB() {
		logger = LoggerFactory.getLogger(ContentEJB.class);
	}
	
	@Override
	public void populate()
	{
		Content c[] = new Content[] {
				new Content("Breaking Bad", "John", 2000, "Action", "breakingbad-john"),
				new Content("Game of Thrones", "Henry", 2009, "Action", "gameofthrones-henry"),
				new Content("Suits", "Peter", 2003, "Comedy", "suits-peter"),
				new Content("Big Bang Theory", "Mike", 2011, "Comedy", "bigbangtheory-mike"),
				new Content("American Horror Story", "Douglas", 2008, "Horror", "americanhorrorstory-douglas"),
				new Content("Lord of the Rings", "Richard", 2001, "Fantasy", "lordoftherings-richard"),
				new Content("Pirates of the Caribean", "Henry", 2014, "Adventure", "piratesofthecaribean-henry"),
				new Content("Star Wars", "Mike", 1980, "Science fiction", "starwars-mike"),
				new Content("Coco", "Pablo", 1960, "Animation", "coco-pablo"),
				new Content("Zorro", "Mike", 1990, "Romance", "zorro-mike")
		};
		
		for(Content con : c)
			em.persist(con);
	}
	
	// Adicionar novo Content à aplicação
	@Override
	public boolean addNewContent(String title, String director, int year, String category) {
		Query q = em.createQuery("SELECT COUNT(c.title) FROM Content c WHERE c.title =:title")
				.setParameter("title", title);
		long count = (long)q.getSingleResult();
		if(count==0) {
			Content newContent = new Content();
			newContent.setTitle(title);
			String str = title.replaceAll("\\s+","").toLowerCase();
			StringBuilder value = new StringBuilder();
			//value.append("/contents/");
			value.append(str);
			value.append("-");
			String str2 = director.replaceAll("\\s+","").toLowerCase();
			value.append(str2);
			newContent.setMultimedia(value.toString());
			newContent.setDirector(director);
			newContent.setYear(year);
			newContent.setCategory(category);
			em.persist(newContent);
			
			logger.info("New content has been added (" + newContent + ")");
			
			return true;
		}
		
		logger.info("Failed to add new content with title " + title + " - content with same title already exists");
		
		return false;
	}

	@Override
	public boolean removeContent(int contentID)
	{
		Content content = em.find(Content.class, contentID);
		Query queryUsers = em.createQuery("SELECT u FROM User u WHERE :content MEMBER OF u.watchList").setParameter("content", content);
		List<User> users = queryUsers.getResultList();
		
		for(User u : users)
			u.getWatchList().remove(content);
		
		em.remove(content);
		
		if(em.find(Content.class, contentID) == null)
			return false;
		
		logger.info("Content with id " + contentID + " and title + \"" + content.getTitle() + "\" has been removed");
		
		return true;
	}
		
	//Editar conteudo 
	@Override
	public void editContent(int contentID, String title, String director, String category, int year)
	{
		Content content = em.find(Content.class, contentID);
		
		if(title != null)
			content.setTitle(title);
		if(director != null)
			content.setDirector(director);
		if(category != null)
			content.setCategory(category);
		if(year > 1800)
			content.setYear(year);
		
		logger.info("Content with id " + contentID + " and title + \"" + content.getTitle() + "\" has been edited");
	}

	// adicionar um Content à watchList de um user
	@Override
	public void addContentToWatchList(int contentID, int userID) {
		User user = em.find(User.class, userID);
		Content content = em.find(Content.class, contentID);

		user.getWatchList().add(content);
		
		logger.info("Content with id " + contentID + " and title + \"" + content.getTitle() + "\""
				+ "has been added to the watchlist of the user with id " + userID);
	}

	// remover um Content da watchList de um user
	@Override
	public void removeContentFromWatchList(int contentID, int userID) {
		User user = em.find(User.class, userID);
		Content content = em.find(Content.class, contentID);

		user.getWatchList().remove(content);
		
		logger.info("Content with id " + contentID + " and title + \"" + content.getTitle() + "\""
				+ "has been removed from the watchlist of the user with id " + userID);
	}

	// listar Content de determinada categoria
	@Override
	public List<ContentDTO> seeContentFromCategory(String category) {
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query = em.createQuery("SELECT c FROM Content c where c.category LIKE:categoryN")
				.setParameter("categoryN", category);
		c = query.getResultList();
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}

	// listar Content de determinado director
	@Override
	public List<ContentDTO> seeContentFromDirector(String director) {
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query = em.createQuery("SELECT c FROM Content c where c.director LIKE:directorN")
				.setParameter("directorN", director);
		c = query.getResultList();
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}

	// listar Content por intervalo
	@Override
	public List<ContentDTO> seeContentFromYears(int year1, int year2) {
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query = em.createQuery("SELECT c FROM Content c where c.year BETWEEN :date1 and :date2")
				.setParameter("date1", year1)
				.setParameter("date2", year2);
		c = query.getResultList();
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}
	private int getMinMax(int opcao) {
		Query query;
		int result;
		if(opcao ==1) {
			query = em.createQuery("SELECT MIN(c.year) FROM Content c");
			result = (int)query.getSingleResult();
		}
		else {
			query = em.createQuery("SELECT MAX(c.year) FROM Content c");
			result = (int)query.getSingleResult();
		}
		
		return result;
	}
	//Aplicar os filtros
	@Override
	public List<ContentDTO> aplicarFiltros(){
		return aplicarFiltros("-", "-", -1, -1);
	}
	
	@Override
	public List<ContentDTO> aplicarFiltros(String diretor, String categoria, int minYear, int maxYear){
		return aplicarFiltros(diretor,categoria,minYear,maxYear,1,false);
	}
	
	@Override
	public List<ContentDTO> aplicarFiltros(String diretor, String categoria, int minYear, int maxYear, int opcao, boolean asc){
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query;
		if(countList()==0) {
			return cd;
		}
		if(minYear == -1) {
			minYear = getMinMax(1);
		}
		if(maxYear == -1) {
			maxYear = getMinMax(2);
		}

		String order;
		if(opcao == 1) {
			order="";
		}
		else if(opcao == 2) {
			order="ORDER BY c.director "+(asc?"ASC":"DESC");
		}
		else if(opcao == 3) {
			order="ORDER BY c.title "+(asc?"ASC":"DESC");;
		}
		else if(opcao == 4) {
			order="ORDER BY c.year "+(asc?"ASC":"DESC");;
		}
		else {
			order="ORDER BY c.category "+(asc?"ASC":"DESC");
		}
		
		if(diretor == null || categoria == null)
			diretor = categoria = "-";

		boolean dir = !diretor.equals("-");
		boolean cat = !categoria.equals("-");
		
		if(!dir && cat) {
			query = em.createQuery("SELECT c FROM Content c WHERE c.category LIKE:Categoria AND c.year BETWEEN :yearMin AND :yearMax " + order)
					.setParameter("Categoria", categoria)
					.setParameter("yearMin", minYear)
					.setParameter("yearMax", maxYear);
			c = query.getResultList();
		}
		else if(dir && !cat) {
			query = em.createQuery("SELECT c FROM Content c WHERE c.director LIKE:Diretor AND c.year BETWEEN :yearMin AND :yearMax " + order)

					.setParameter("Diretor", diretor)
					.setParameter("yearMin", minYear)
					.setParameter("yearMax", maxYear);
			c = query.getResultList();
		}
		else if(dir && cat) {
			query = em.createQuery("SELECT c FROM Content c WHERE c.director LIKE:Diretor AND c.category LIKE:Categoria AND c.year BETWEEN :yearMin AND :yearMax " + order)
					.setParameter("Diretor", diretor)
					.setParameter("Categoria",categoria)
					.setParameter("yearMin", minYear)
					.setParameter("yearMax", maxYear);
			c = query.getResultList();
		}
		else {
			query = em.createQuery("SELECT c FROM Content c WHERE c.year BETWEEN :yearMin AND :yearMax " + order)
					.setParameter("yearMin", minYear)
					.setParameter("yearMax", maxYear);
			c = query.getResultList();
		}
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;

	}
	
	@Override
	public List<ContentDTO> aplicarFiltrosWL(int userID){
		return aplicarFiltrosWL(userID,1,false);
	}
	public long countList() {
		Query query = em.createQuery("SELECT COUNT (c) FROM Content c");
		return (long)query.getSingleResult();
	}
	
	@Override
	public List<ContentDTO> aplicarFiltrosWL(int userID, int opcao, boolean asc){
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query;
		if(countList()==0) {
			return cd;
		}
		String order;
		if(opcao == 1) {
			order="";
		}
		else if(opcao == 2) {
			order="ORDER BY c.director "+(asc?"ASC":"DESC");
		}
		else if(opcao == 3) {
			order="ORDER BY c.title "+(asc?"ASC":"DESC");;
		}
		else if(opcao == 4) {
			order="ORDER BY c.year "+(asc?"ASC":"DESC");;
		}
		else {
			order="ORDER BY c.category "+(asc?"ASC":"DESC");
		}

		query = em.createQuery("SELECT c FROM Content c, User u WHERE u.id = " + userID + " AND c MEMBER OF u.watchList " + order);
		c = query.getResultList();
		
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}
	
	// Devolve todos os nomes dos directores 
	@Override
	public List<String> getDirectorName(int ordem){
		List<String> result = new ArrayList<String>();
		Query query;
		
		switch(ordem) {
		//Sem ordem
		case 1:
			query = em.createQuery("SELECT DISTINCT c.director FROM Content c");
			result = query.getResultList();
			
			break;
		//Ordem descendente
		case 2:
			query = em.createQuery("SELECT DISTINCT c.director FROM Content c ORDER BY c.director ASC");
			result = query.getResultList();

			break;
		case 3:
			query = em.createQuery("SELECT DISTINCT c.director FROM Content c ORDER BY c.director DESC");
			result = query.getResultList();
			break;
		}
		return result;
	}
	
	//Devolve todas as categorias 
	@Override
	public List<String> getCategories(int ordem){
		List<String> result = new ArrayList<String>();
		Query query;
		
		switch(ordem) {
		//Sem ordem
		case 1:
			query = em.createQuery("SELECT DISTINCT c.category FROM Content c");
			result = query.getResultList();
			
			break;
		//Ordem descendente
		case 2:
			query = em.createQuery("SELECT DISTINCT c.category FROM Content c ORDER BY c.category ASC");
			result = query.getResultList();

			break;
		case 3:
			query = em.createQuery("SELECT DISTINCT c.category FROM Content c ORDER BY c.category DESC");
			result = query.getResultList();
			break;
		}
		return result;
	}
	
	@Override
	public List<String> getAvailableCategories()
	{
		return Arrays.asList(Content.Categories);
	}
	
	@Override
	public List<ContentDTO> getSuggestedCotent(int userID)
	{
		List<ContentDTO> watchList = aplicarFiltrosWL(userID);
		List<ContentDTO> suggestedContent = new ArrayList<>();
		
		long contentCount = (long) em.createQuery("SELECT COUNT(c.id) FROM Content c").getSingleResult();
		int count = (int) (contentCount >= 5? 5 : contentCount);
		
		if(!watchList.isEmpty())
		{
			int rand = new Random().nextInt(count > watchList.size()? watchList.size() : count);
			Collections.shuffle(watchList);
			List<ContentDTO> selectedFromWL = watchList.subList(0, rand);
			Map<String, String> categories = new HashMap<>();
			for(int i = 0; i < selectedFromWL.size(); i++)
				categories.put("param" + i, selectedFromWL.get(i).getCategory());
			String queryString = "SELECT DISTINCT c FROM Content c";
			if(!categories.isEmpty())
			{
				queryString += " WHERE ";
				for(String key : categories.keySet())
					queryString += " c.category LIKE :" + key + " OR";
				queryString = queryString.substring(0, queryString.length() - " OR".length());
			}
			Query query = em.createQuery(queryString);
			for(String key : categories.keySet())
				query.setParameter(key, categories.get(key));
			List<ContentDTO> result = new ArrayList<>();
			for(Content c : (List<Content>)query.getResultList())
				result.add(new ContentDTO(c));
			Collections.shuffle(result);
			suggestedContent.addAll(result.subList(0, rand > result.size()? result.size() : rand));
			count -= rand > result.size()? result.size() : rand;
		}
		
		Map<String, Integer> alreadySuggested = new HashMap<>();
		for(int i = 0; i < suggestedContent.size(); i++)
			alreadySuggested.put("param" + i, suggestedContent.get(i).getID());
		String queryString = "SELECT c FROM Content c";
		if(!alreadySuggested.isEmpty())
		{
			queryString += " WHERE ";
			for(String key : alreadySuggested.keySet())
				queryString += " c.id != :" + key + " AND";
			queryString = queryString.substring(0, queryString.length() - " AND".length());
		}
		Query query = em.createQuery(queryString);
		for(String key : alreadySuggested.keySet())
			query.setParameter(key, alreadySuggested.get(key));
		List<ContentDTO> notYetSuggested = new ArrayList<>();
		for(Content c : (List<Content>)query.getResultList())
			notYetSuggested.add(new ContentDTO(c));
		Collections.shuffle(notYetSuggested);
		suggestedContent.addAll(notYetSuggested.subList(0, count > notYetSuggested.size()? notYetSuggested.size() : count));
		
		logger.info("Generating list of suggested content for user with id " + userID);
		
		return suggestedContent;
	}
	
	@Override
	public boolean isTitleValid(String title)
	{
		if(title.isEmpty())
			return false;
		return em.createQuery("SELECT c FROM Content c WHERE c.title LIKE :title").setParameter("title", title).getResultList().isEmpty();
	}
	
	@Override
	public boolean isDirectorValid(String director)
	{
		return !director.isEmpty();
	}
	
	@Override
	public boolean isCategoryValid(String category)
	{
		return !category.isEmpty();
	}
	
	@Override
	public boolean isYearValid(int year)
	{
		return year > 1800;
	}

	@Override
	public ContentDTO getContentByID(int contentID)
	{
		Content content = em.find(Content.class, contentID);
		
		return new ContentDTO(content);
	}
}
