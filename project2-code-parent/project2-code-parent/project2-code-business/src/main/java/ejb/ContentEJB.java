package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.Content;
import dto.ContentDTO;

/**
 * Session Bean implementation class ContentEJB
 */
@Stateless
@LocalBean
public class ContentEJB implements ContentEJBRemote {
	@PersistenceContext(name="Players")
	 EntityManager em;
    /**
     * Default constructor. 
     */
    public ContentEJB() {
        // TODO Auto-generated constructor stub
    }
    
    //	Adicionar Contents à BD - função do professor
    public void populate(){
        Content[] contents = {
                new Content("Breaking Bad","John",1996,"Comedy"),
                new Content("Suits","Peter",2000,"Comedy"),
                new Content("Game Of Thrones","Henry",2005,"Action")};
       
        for (Content c : contents)
            em.persist(c);

    }
    
    //Adicionar novo Content à aplicação
	public void addNewContent(String title,String director,int year,String category) {
		Content newContent = new Content(); 
		newContent.setTitle(title);
		newContent.setDirector(director);
		newContent.setYear(year);
		newContent.setCategory(category);
		
		em.persist(newContent);
	}

    
    //adicionar um Content à watchList de um user
    
    //listar watchList de um user
    
   
    //listar todos os Content
    public List<ContentDTO> seeAllContent(){
    	List<Content> c = new ArrayList<Content>();
    	List<ContentDTO> cd = new ArrayList<ContentDTO>();
    	Query query = em.createQuery("FROM Content");	
    	c = query.getResultList();
    	for(Content con :c) {
    		cd.add(new ContentDTO(con.getTitle(),con.Director(),con.getYear(),con.getCategory()));
    	}
    	return cd;
    }
    
    
    //listar Content de determinada categoria
    
    public List <ContentDTO> seeContentFromCategory(String category){
    	List<Content> c = new ArrayList<Content>();
    	List<ContentDTO> cd = new ArrayList<ContentDTO>();
    	Query query = em.createQuery("SELECT c FROM Content c where c.category LIKE:categoryN").setParameter("categoryN", category);
    	c = query.getResultList();
    	for(Content con :c) {
    		cd.add(new ContentDTO(con.getTitle(),con.Director(),con.getYear(),con.getCategory()));
    	}
    	return cd;
    }
    //listar Content de determinado director
    
    
    //listar Content por intervalo 
    
    public List <Content> seeContentFromYears(int year1,int year2){
    	Query query = em.createQuery("SELECT c FROM Content c where c.year BETWEEN :date1 and :date2")
    			.setParameter("date1", year1)
    			.setParameter("date2", year2);
    	return query.getResultList();

    }
   
    
    
    //
    
    

}
