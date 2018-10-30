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
import dto.UserDTO;

/**
 * Session Bean implementation class ContentEJB
 */
@Stateless
@LocalBean
public class ContentEJB implements ContentEJBRemote {
	@PersistenceContext(name="Contents")
	 EntityManager em;
    /**
     * Default constructor. 
     */
    public ContentEJB() {
        // TODO Auto-generated constructor stub
    }
    
    //	Adicionar Contents à BD - função do professor
    @Override
    public void populate(){
        Content[] contents = {
                new Content("Breaking Bad","John",1996,"Comedy, claramente"),
                new Content("Suits","Peter",2000,"Comedy"),
                new Content("Game Of Thrones","Henry",2005,"Action")};
       
        for (Content c : contents)
            em.persist(c);

    }
    
    //Adicionar novo Content à aplicação
    @Override
	public void addNewContent(String title,String director,int year,String category) {
		Content newContent = new Content(); 
		newContent.setTitle(title);
		newContent.setDirector(director);
		newContent.setYear(year);
		newContent.setCategory(category);
		
		em.persist(newContent);
	}

    
    //adicionar um Content à watchList de um user
    @Override
    public void addContentToWatchList(int contentID, int userID){
    	Query queryContent = em.createQuery("SELECT c FROM Content c WHERE c.id = :id").setParameter("id", contentID);
    	Query queryUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
    	User user = (User) queryUser.getSingleResult();
    	Content content = (Content) queryContent.getSingleResult();
    	
    	user.getWatchList().add(content);
    	
    	em.merge(user);
    }
    
    //remover um Content da watchList de um user
    @Override
    public void removeContentFromWatchList(int contentID, int userID){
    	Query queryContent = em.createQuery("SELECT c FROM Content c WHERE c.id = :id").setParameter("id", contentID);
    	Query queryUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
    	User user = (User) queryUser.getSingleResult();
    	Content content = (Content) queryContent.getSingleResult();
    	
    	user.getWatchList().remove(content);
    	
    	em.merge(user);
    }
    
    //listar watchList de um user
    @Override
    public List<ContentDTO> seeUserWatchList(int userID){
    	Query queryUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", userID);
    	User user = (User) queryUser.getSingleResult();
    	
    	List<Content> originalContent = user.getWatchList();
    	List<ContentDTO> dtoContent = new ArrayList<>();
    	for(Content c : originalContent)
    		dtoContent.add(new ContentDTO(c));
    	
    	return dtoContent;
    }
   
    //listar todos os Content
    @Override
    public List<ContentDTO> seeAllContent(){
    	List<Content> c = new ArrayList<Content>();
    	List<ContentDTO> cd = new ArrayList<ContentDTO>();
    	Query query = em.createQuery("FROM Content");	
    	c = query.getResultList();
    	for(Content con :c) {
    		cd.add(new ContentDTO());
    	}
    	return cd;
    }
    
    
    //listar Content de determinada categoria
    @Override
    public List <ContentDTO> seeContentFromCategory(String category){
    	List<Content> c = new ArrayList<Content>();
    	List<ContentDTO> cd = new ArrayList<ContentDTO>();
    	Query query = em.createQuery("SELECT c FROM Content c where c.category LIKE:categoryN").setParameter("categoryN", category);
    	c = query.getResultList();
    	for(Content con :c) {
    		cd.add(new ContentDTO(con));
    	}
    	return cd;
    }
    
    
    //listar Content de determinado director
    public List <ContentDTO> seeContentFromDirector(String director){
    	List<Content> c = new ArrayList<Content>();
    	List<ContentDTO> cd = new ArrayList<ContentDTO>();
    	Query query = em.createQuery("SELECT c FROM Content c where c.director LIKE:directorN").setParameter("directorN", director);
    	c = query.getResultList();
    	for(Content con :c) {
    		cd.add(new ContentDTO(con.getTitle(),con.Director(),con.getYear(),con.getCategory()));
    	}
    	return cd;
    }
    
    //listar Content por intervalo 
    
    public List <ContentDTO> seeContentFromYears(int year1,int year2){
    	List<Content> c = new ArrayList<Content>();
    	List<ContentDTO> cd = new ArrayList<ContentDTO>();
    	Query query = em.createQuery("SELECT c FROM Content c where c.year BETWEEN :date1 and :date2")
    			.setParameter("date1", year1)
    			.setParameter("date2", year2);
    	for(Content con :c) {
    		cd.add(new ContentDTO(con.getTitle(),con.Director(),con.getYear(),con.getCategory()));
    	}
    	return cd;


    
    
    //
    
    

}
