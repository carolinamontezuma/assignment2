package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import data.Content;
import data.Manager;
import data.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class ManagerEJB implements ManagerEJBRemote {

	EntityManager em;

	/**
	 * Default constructor.
	 */
	public ManagerEJB() {

	}

	// adicionar informação de um novo manager (=criar conta)
	@Override
	public void addAccount(String username, String password) {
		Manager manager = new Manager(username, password);

		em.persist(manager);
	}

	// apagar conta
	@Override
	public void deleteAccount(int managerID) {
		em.remove(em.find(Manager.class, managerID));
	}

	// Adicionar novo Content à aplicação
	@Override
	public void addNewContent(String title, String director, int year, String category) {
		Content newContent = new Content();
		newContent.setTitle(title);
		newContent.setDirector(director);
		newContent.setYear(year);
		newContent.setCategory(category);

		em.persist(newContent);
	}

	@Override
	public void removeContent(int contentID)
	{
		Query queryContent = em.createQuery("SELECT c FROM Content c WHERE c.id = :id").setParameter("id", contentID);
		Content content = (Content) queryContent.getSingleResult();
		Query queryUsers = em.createQuery("SELECT u FROM User WHERE :content MEMBER OF u.watchList").setParameter("content", content);
		List<User> users = queryUsers.getResultList();
		
		for(User u : users)
		{
			u.getWatchList().remove(content);
			em.persist(u);
		}
		
		em.remove(em.find(Content.class, content.getID()));
	}
	
	@Override
	public void populate() {
		Manager[] managers = { new Manager("admin", "admin") };

		for (Manager m : managers)
			em.persist(m);
	}

}
