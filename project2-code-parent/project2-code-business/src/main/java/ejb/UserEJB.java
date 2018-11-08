package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.Manager;
import data.User;
import dto.UserDTO;
import utils.PasswordHasher;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class UserEJB implements UserEJBRemote {
	@PersistenceContext(name = "Users")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public UserEJB() {

	}
	
	@Override
	public void populate() {
		User[] users = { new User("Carolina", PasswordHasher.plainTextToHash("carolina"), "carolina@mail.com", "1234123412341234"),
				new User("João", PasswordHasher.plainTextToHash("joao"), "joao@mail.com", "2345234523452345"),
				new User("Cesar", PasswordHasher.plainTextToHash("cesar"), "cesar@mail.com", "3456345634563456") };

		for (User u : users)
			em.persist(u);
	}
	
	// adicionar informação pessoal de um novo utilizador (=criar conta)
	@Override
	public void addAccount(String username, String password, String email, String creditCard) {
		User user = new User(username, password, email, creditCard);

		em.persist(user);
	}

	// editar a informação pessoal de um utilizador
	@Override
	public void editPersonalInformation(int userID, String username, String password, String email, String creditCard) {
		User user = em.find(User.class, userID);
		
		if(username != null)
			user.setUsername(username);
		if(password != null)
			user.setPassword(password);
		if(email != null)
			user.setEmail(email);
		if(creditCard != null)
			user.setCreditCard(creditCard);

		//em.merge(user);
	}

	// apagar conta
	@Override
	public void deleteAccount(int userID) {
		em.remove(em.find(User.class, userID));
	}
	
	@Override
	public boolean canRegister(String email)
	{
		Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email")
				.setParameter("email", email);
		
		return query.getResultList().size() == 0;
	}

	@Override
	public boolean validateLogin(String email, String password) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
				.setParameter("email", email).setParameter("password", password);

		return query.getResultList().size() == 1; // True se apenas houver 1 user, False caso contrário
	}
	
	@Override
	public void userLoggedIn(int userID)
	{
		User user = em.find(User.class, userID);

		user.updateLoginCount();

		//em.merge(user);
	}
	
	@Override
	public UserDTO getUserByID(int userID)
	{
		User user = em.find(User.class, userID);
		
		return new UserDTO(user);
	}
	
	@Override
	public UserDTO getUserByEmail(String email)
	{
		Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
		User user = (User) query.getSingleResult();
		
		return new UserDTO(user);
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
		
		Query query = em.createQuery("SELECT u FROM User u WHERE u.email LIKE :email").setParameter("email", email);
		return query.getResultList().isEmpty();
	}
	
	@Override
	public boolean isCreditCardValid(String creditCard)
	{
		return creditCard != null && creditCard.matches("[0-9]+") && creditCard.length() == 16;
	}
}
