package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.Manager;
import data.User;

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

	// adicionar informação pessoal de um novo utilizador (=criar conta)
	@Override
	public void addAccount(String username, String password, String email, String creditCard) {
		User user = new User(username, password, email, creditCard);

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
	public boolean validateLogin(String email, String password) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
				.setParameter("email", email).setParameter("password", password);

		return query.getResultList().size() == 1; // True se apenas houver 1 user, False caso contrário
	}

	@Override
	public void populate() {
		User[] users = { new User("Carolina", "carolina", "carolina@mail.com", "123456789"),
				new User("Joao", "joao", "joao@mail.com", "123456789"),
				new User("Cesar", "cesar", "cesar@mail.com", "123456789") };

		for (User u : users)
			em.persist(u);
	}

}
