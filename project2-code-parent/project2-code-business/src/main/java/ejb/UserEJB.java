package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import data.Content;
import data.User;
import dto.ContentDTO;

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
        // TODO Auto-generated constructor stub
    }
    
    
    //editar a informação pessoal de um utilizador
    @Override
    public void editPersonalInformation(int userID, String username, String email, String creditCard) {
    	Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
    	User user = (User) query.getSingleResult();
    	
    	user.setUsername(username);
    	user.setEmail(email);
    	user.setCreditCard(creditCard);
    	
    	em.merge(user);
    }
    
    //apagar conta -> SERÁ QUE ISTO CHEGA? O RUGU SÓ TINHA ESTAS LINHAS +OU- (com a adição de uns try-catch)
    @Override
    public void deleteAccount(int userID) {
    	em.remove(em.find(User.class, userID));
    }
    
    @Override
    public void populate() {
    	  User [] users = { 
    	    new User("Carolina", "carolina", "sdjhsd"), 
    	    new User("Joao", "carolina", "sdjhsd"), 
    	    new User("Cesar", "carolina", "sdjhsd"), 
    	  };
    	  
    	  for (User t : users)
    	   em.persist(t);   
    }

 

}
