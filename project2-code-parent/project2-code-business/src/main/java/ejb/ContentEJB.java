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
	
	// Adicionar novo Content à aplicação
	@Override
	public int addNewContent(String title, String director, int year, String category) {
		Query q = em.createQuery("SELECT COUNT(c.title) FROM Content c WHERE c.title =:title")
				.setParameter("title", title);
		long count = (long)q.getSingleResult();
		if(count==0) {
			Content newContent = new Content();
			newContent.setTitle(title);
			newContent.setDirector(director);
			newContent.setYear(year);
			newContent.setCategory(category);
	
			em.persist(newContent);
			return 1;
		}
		return 0;
	}

	@Override
	public void removeContent(int contentID)
	{
		Query queryContent = em.createQuery("SELECT c FROM Content c WHERE c.id = :id").setParameter("id", contentID);
		Content content = (Content) queryContent.getSingleResult();
		Query queryUsers = em.createQuery("SELECT u FROM User u WHERE :content MEMBER OF u.watchList").setParameter("content", content);
		List<User> users = queryUsers.getResultList();
		
		for(User u : users)
		{
			u.getWatchList().remove(content);
			em.persist(u);
		}
		
		em.remove(em.find(Content.class, content.getID()));
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

	// adicionar um Content à watchList de um user
	@Override
	public void addContentToWatchList(int contentID, int userID) {
		Query queryContent = em.createQuery("SELECT c FROM Content c WHERE c.id = :id")
				.setParameter("id", contentID);
		Query queryUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id")
				.setParameter("id", userID);
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
	
	//Listar a watch list de um determinado utilizador
	public List<ContentDTO> seeWatchList(int id){
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query = em.createQuery("SELECT u.watchList FROM User u where u.id =:id")
				.setParameter("id", id);
		c = query.getResultList();
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
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
				.setParameter("date1", year1)
				.setParameter("date2", year2);
		c = query.getResultList();
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}
	//Aplicar os filtros
	
	public List<ContentDTO> aplicarFiltros(String diretor, String categoria){
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query;
		if(diretor.equals("-") && !(categoria.equals("-"))) {
			query = em.createQuery("SELECT c FROM Content c WHERE c.category LIKE:Categoria")
					.setParameter("Categoria", categoria);
			c = query.getResultList();
		}
		else if(!(diretor.equals("-")) && categoria.equals("-")) {
			query = em.createQuery("SELECT c FROM Content c WHERE c.director LIKE:Diretor")
					.setParameter("Diretor", diretor);
			c = query.getResultList();
		}
		else if(!(diretor.equals("-")) && !(categoria.equals("-"))) {
			query = em.createQuery("SELECT c FROM Content c WHERE c.director LIKE:Diretor AND c.category LIKE:Categoria")
					.setParameter("Diretor", diretor)
					.setParameter("Categoria",categoria);
			c = query.getResultList();
		}
		else {
			query = em.createQuery("SELECT FROM Content");
			c = query.getResultList();
		}
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		return cd;
	}
	
	public List<ContentDTO> orderTable(int opcaoFiltro, int opcaoOrdena) {
		List<Content> c = new ArrayList<Content>();
		List<ContentDTO> cd = new ArrayList<ContentDTO>();
		Query query;
		//Vai ordenar de forma ascendente
		if(opcaoOrdena == 1) {
			//TITULO
			if(opcaoFiltro==1) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.title ASC");
				c = query.getResultList();
			}
			//CATEGORIA
			if(opcaoFiltro==2) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.category ASC");
				c = query.getResultList();
			}
			//DIRETOR
			if(opcaoFiltro==3) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.director ASC");
				c = query.getResultList();
			}
			//YEAR
			if(opcaoFiltro==4) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.year ASC");
				c = query.getResultList();
			}
		}
		else if(opcaoOrdena == 2) {
			//Ordenar por titulo
			if(opcaoFiltro==1) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.title DESC");
				c = query.getResultList();
			}
			if(opcaoFiltro==2) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.category DESC");
				c = query.getResultList();
			}
			//CATEGORIA
			if(opcaoFiltro==3) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.director DESC");
				c = query.getResultList();
			}
			//DIRETOR
			if(opcaoFiltro==4) {
				query = em.createQuery("SELECT c FROM Content c ORDER BY c.year DESC");
				c = query.getResultList();
			}
		}
		else {
			query = em.createQuery("SELECT FROM Content");

		}
		for (Content con : c) {
			cd.add(new ContentDTO(con));
		}
		
		return cd;
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
