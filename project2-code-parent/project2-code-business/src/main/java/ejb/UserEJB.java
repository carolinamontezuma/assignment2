package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;


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
    public void editPersonalInformation() {
    	
    	
    }
    
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
