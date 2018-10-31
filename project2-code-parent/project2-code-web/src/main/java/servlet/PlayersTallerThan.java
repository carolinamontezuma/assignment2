
package main.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ContentDTO;
import ejb.ContentEJBRemote;

/**
 * Servlet implementation class PlayersTallerThan
 */

//http://localhost:8080/project2-code-web/Webflix?fill=1
//url = http://localhost:8080/project2-code-web/Webflix?category=comedy

@WebServlet("/PlayersTallerThan")
public class PlayersTallerThan extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 ContentEJBRemote ejbremote;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public PlayersTallerThan() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 PrintWriter out = response.getWriter();
	 response.setContentType("text/html");
	 
	 if (request.getParameter("fill") != null) {
		 ejbremote.populate();
		 out.println("<h1>Populate Content: OK!</h1>");
	 }
	 
	 if(request.getParameter("director") != null) {
    	 String directorName = request.getParameter("director");
    	 List<ContentDTO> content =  ejbremote.seeContentFromDirector(directorName);
         request.setAttribute("listDirector", content);
         RequestDispatcher dispatcher = request.getRequestDispatcher("/listContents.jsp");
         dispatcher.forward(request, response);
     }
 
    
 }


 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 doGet(request, response);
	     

 }
}

