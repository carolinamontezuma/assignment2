package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.LoggerFactory;

import data.Content;
import data.Manager;
import data.User;
import dto.ManagerDTO;
import utils.PasswordHasher;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class ManagerEJB implements ManagerEJBLocal {
	@PersistenceContext(name = "Managers")
	EntityManager em;

	private org.slf4j.Logger logger;
	
	/**
	 * Default constructor.
	 */
	public ManagerEJB() {
		logger = LoggerFactory.getLogger(ManagerEJB.class);
	}
	
	@Override
	public void populate()
	{
		Manager m[] = new Manager[] {
				new Manager("admin1", PasswordHasher.plainTextToHash("admin1"), "admin1@admin.com"),
				new Manager("admin2", PasswordHasher.plainTextToHash("admin2"), "admin2@admin.com")
		};
		
		for(Manager man : m)
			em.persist(man);
	}

	// adicionar informação de um novo manager (=criar conta)
	@Override
	public void addAccount(String username, String password, String email) {
		Manager manager = new Manager(username, password, email);

		em.persist(manager);
		
		logger.info("New account created for manager " + manager);
	}

	// apagar conta
	@Override
	public void deleteAccount(int managerID) {
		em.remove(em.find(Manager.class, managerID));
		
		logger.info("Manager with id " + managerID + " was removed");
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
	public void managerLoggedIn(int managerID)
	{
		logger.info("Manager with id " + managerID + " has logged in");
	}
	
	@Override
	public void managerLoggedOut(int managerID)
	{
		logger.info("Manager with id " + managerID + " has logged out");
	}

	@Override
	public ManagerDTO getManagerByID(int managerID)
	{
		return new ManagerDTO(em.find(Manager.class, managerID));
	}
	
	@Override
	public ManagerDTO getManagerByEmail(String email)
	{
		Query query = em.createQuery("SELECT m FROM Manager m WHERE m.email = :email").setParameter("email", email);
		Manager manager = (Manager) query.getSingleResult();
		
		return new ManagerDTO(manager);
	}
	
	@Override
	public boolean isUsernameValid(String username)
	{
		return username != null && username.length() >= 4;
	}
	
	@Override
	public boolean isPasswordValid(String password)
	{
		return password != null && password.length() >= 4;
	}
	
	@Override
	public boolean isEmailValid(String email)
	{
		if(email == null || email.isEmpty())
			return false;
		
		String[] parts = email.split("@");
		if(parts.length != 2)
			return false;
		
		Query query = em.createQuery("SELECT m FROM Manager m WHERE m.email LIKE :email").setParameter("email", email);
		return query.getResultList().isEmpty();
	}
}
