package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import data.Manager;
import data.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class UserEJB implements UserEJBRemote {

	EntityManager em;

	/**
	 * Default constructor.
	 */
	public UserEJB() {

	}

	// adicionar informação pessoal de um novo utilizador (=criar conta)
	@Override
	public void addAccount(String username, String email, String creditCard) {
		User user = new User(username, email, creditCard);

		em.persist(user);
	}

	// editar a informação pessoal de um utilizador
	@Override
	public void editPersonalInformation(int userID, String username, String email, String creditCard) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
		User user = (User) query.getSingleResult();

		user.setUsername(username);
		user.setEmail(email);
		user.setCreditCard(creditCard);

		em.merge(user);
	}

	// apagar conta -> SERÁ QUE ISTO CHEGA? O RUGU SÓ TINHA ESTAS LINHAS +OU- (com a
	// adição de uns try-catch)
	@Override
	public void deleteAccount(int userID) {
		em.remove(em.find(User.class, userID));
	}

	@Override
	public void populate() {
		User[] users = { new User("Carolina", "carolina", "sdjhsd"),
				new User("Joao", "carolina", "sdjhsd"), new User("Cesar", "carolina", "sdjhsd") };

		for (User u : users)
			em.persist(u);
	}

}
