package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.Content;
import data.User;
import dto.ContentDTO;

/**
 * Session Bean implementation class ContentEJB
 */
@Stateless
@LocalBean
public class ContentEJB implements ContentEJBRemote {
	@PersistenceContext(name = "Contents")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public ContentEJB() {

	}

	// Adicionar Contents à BD - função do professor
	@Override
	public void populate() {
		Content[] contents = { new Content("Breaking Bad", "John", 1996, "Comedy, claramente"),
				new Content("Suits", "Peter", 2000, "Comedy"),
				new Content("Game Of Thrones", "Henry", 2005, "Action") };

		for (Content c : contents)
			em.persist(c);

	}

	// adicionar um Content à watchList de um user
	@Override
	public void addContentToWatchList(int contentID, int userID) {
		Query queryContent = em.createQuery("SELECT c FROM Content c WHERE c.id = :id").setParameter("id", contentID);
		Query queryUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
		User user = (User) queryUser.getSingleResult();
		Content content = (Content) queryContent.getSingleResult();

		user.getWatchList().add(content);

		em.merge(user);
	}

	// remover um Content da watchList de um user
	@Override
	public void removeContentFromWatchList(int contentID, int userID) {
		Query queryContent = em.createQuery("SELECT c FROM Content c WHERE c.id = :id").setParameter("id", contentID);
		Query queryUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
		User user = (User) queryUser.getSingleResult();
		Content content = (Content) queryContent.getSingleResult();

		user.getWatchList().remove(content);

		em.merge(user);
	}
	public void removeContent(int id) {
		em.remove(em.find(Content.class, id));

	}

	// listar watchList de um user
	@Override
	public List<ContentDTO> seeUserWatchList(int userID) {
		Query queryUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
		User user = (User) queryUser.getSingleResult();

		List<Content> originalContent = user.getWatchList();
		List<ContentDTO> dtoContent = new ArrayList<>();
		for (Content c : originalContent)
			dtoContent.add(new ContentDTO(c));

		return dtoContent;
	}

	// listar todos os Content
	@Override
	public List<ContentDTO> seeAllContent(int ordem) {
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query;
		switch(ordem) {
		//Sem ordem
		case 1:
			query = em.createQuery("FROM Content");
			c = query.getResultList();
			for (Content con : c) {
				cd.add(new ContentDTO(con));
			}
			
			break;
		//Ordem descendente
		case 2:
			query = em.createQuery("FROM Content c ORDER BY c.title DESC");
			c = query.getResultList();
			for (Content con : c) {
				cd.add(new ContentDTO(con));
			}
			break;
		case 3:
			query = em.createQuery("FROM Content c ORDER BY c.title ASC");
			c = query.getResultList();
			for (Content con : c) {
				cd.add(new ContentDTO(con));
			}
			break;
		}
		return cd;
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

	public List<ContentDTO> seeContentFromYears(int year1, int year2) {
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query = em.createQuery("SELECT c FROM Content c where c.year BETWEEN :date1 and :date2")
				.setParameter("date1", year1).setParameter("date2", year2);
		c = query.getResultList();
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}
	//Listar a watch list de um determinado utilizador
	public List<ContentDTO> seeWatchList(int id){
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query = em.createQuery("SELECT c.watchList FROM User u where c.id =:id")
				.setParameter("id", id);
		c = query.getResultList();
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}
	
	//Editar conteudo 
	public void editContent(int opcao, int id, String info) {
		Query query = em.createQuery("SELECT c FROM Content c WHERE c.id = :id")
				.setParameter("id", id);
		Content content = (Content) query.getSingleResult();
		if(opcao == 1) {
			content.setTitle(info);
		}
		if(opcao == 2) {
			content.setDirector(info);
		}
		if(opcao == 3) {
			content.setCategory(info);
		}
		if(opcao == 4) {
			int year = Integer.parseInt(info);
			content.setYear(year);
		}
		
		em.merge(content);
	}
	
	// Devolve todos os nomes dos directores 
	
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
	
}
