package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.Content;
import data.Manager;
import data.User;
import dto.ContentDTO;
import dto.UserDTO;
import utils.PasswordHasher;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class UserEJB implements UserEJBRemote {
	@PersistenceContext(name = "Users")
	@Resource(name="java:jboss/mail/gmail")
	private Session session;
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public UserEJB() {

	}
	
	// adicionar informação pessoal de um novo utilizador (=criar conta)
	@Override
	public void addAccount(String username, String password, String email, String creditCard) {
		Date date = new Date();
		User user = new User(username, password, email, creditCard,date);
		em.persist(user);
	}

	// editar a informação pessoal de um utilizador
	@Override
	public void editPersonalInformation(int userID, String username, String password, String email, String creditCard) {
		User user = em.find(User.class, userID);
		
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setCreditCard(creditCard);

		//em.merge(user);
	}

	// apagar conta
	@Override
	public void deleteAccount(int userID) {
		em.remove(em.find(User.class, userID));
	}
	
	@Override
	public boolean canRegister(String username, String email)
	{
		Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.email = :email")
				.setParameter("username", username)
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
	
	
	//Função do tutorial para enviar emails
	public void send(String to,String subject,String body) {
		try {
				Message message =new MimeMessage(session);
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setSubject(subject);
				message.setText(body);
				Transport.send(message);
				
		}catch(MessagingException e) {
			e.printStackTrace();
		}
	}
	
	//Função para listar todos os utilizadores
	public List<UserDTO> listAllUsers(){
		List<User> c = new ArrayList<User>();
		List<UserDTO> cd = new ArrayList<UserDTO>();
		
		Query query=em.createQuery("FROM User");
		c = query.getResultList();
		
		for (User con : c) {
			cd.add(new UserDTO(con));
		}
		return cd;
	}
	
	
}
