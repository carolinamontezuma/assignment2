package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.Manager;
import dto.ManagerDTO;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class ManagerEJB implements ManagerEJBRemote {
	@PersistenceContext(name = "Managers")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public ManagerEJB() {

	}
	
	@Override
	public void populate() {
		Manager[] managers = { new Manager("Admin", "admin", "admin@admin.com") };

		for (Manager m : managers)
			em.persist(m);
	}

	// adicionar informação de um novo manager (=criar conta)
	@Override
	public void addAccount(String username, String password, String email) {
		Manager manager = new Manager(username, password, email);

		em.persist(manager);
	}

	// apagar conta
	@Override
	public void deleteAccount(int managerID) {
		em.remove(em.find(Manager.class, managerID));
	}
	
	@Override
	public boolean validateLogin(String email, String password)
	{
		Query query = em.createQuery("SELECT m FROM Manager m WHERE m.email = :email AND m.password = :password")
				.setParameter("email", email)
				.setParameter("password", password);
		
		return query.getResultList().size() == 1; //True se apenas houver 1 manager, False caso contrário
	}

	@Override
	public ManagerDTO getManagerByID(int managerID)
	{
		Query query = em.createQuery("SELECT m FROM Manager m WHERE m.id = :id").setParameter("id", managerID);
		Manager manager = (Manager) query.getSingleResult();
		
		return new ManagerDTO(manager);
	}
	
	@Override
	public ManagerDTO getManagerByEmail(String email)
	{
		Query query = em.createQuery("SELECT m FROM Manager m WHERE m.email = :email").setParameter("email", email);
		Manager manager = (Manager) query.getSingleResult();
		
		return new ManagerDTO(manager);
	}
}
