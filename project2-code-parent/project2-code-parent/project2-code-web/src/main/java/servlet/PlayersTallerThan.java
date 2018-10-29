
package main.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Content;
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
  else if(request.getParameterValues("range") != null) {
	  List<Content> content =  ejbremote.seeContentFromYears(1997, 2001);
	   out.println("<h1> Contents </h1>");
	   for (Content p : content)
	    out.println(p + "<br/>");

  }
  else if(request.getParameterValues("all") != null) {
	  List<ContentDTO> content =  ejbremote.seeAllContent();
	   out.println("<h1> Contents </h1>");
	   for (ContentDTO p : content)
	    out.println(p + "<br/>");

  }
  else {
   String category = request.getParameter("category");
   List<ContentDTO> content = ejbremote.seeContentFromCategory("comedy");

   out.println("<h1> Contents </h1>");
   for (ContentDTO p : content)
    out.println(p + "<br/>");
  }

	 
     /*request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);*/

 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 doGet(request, response);
	 
	 
	 //CODIGO DA NET
	 // Prepare messages.
     /*Map<String, String> messages = new HashMap<String, String>();
     request.setAttribute("messages", messages);

     // Get and validate name.
     String name = request.getParameter("name");
     if (name == null || name.trim().isEmpty()) {
         messages.put("name", "Please enter name");
     } else if (!name.matches("\\p{Alnum}+")) {
         messages.put("name", "Please enter alphanumeric characters only");
     }

     // Get and validate age.
     String age = request.getParameter("age");
     if (age == null || age.trim().isEmpty()) {
         messages.put("age", "Please enter age");
     } else if (!age.matches("\\d+")) {
         messages.put("age", "Please enter digits only");
     }

     // No validation errors? Do the business job!
     if (messages.isEmpty()) {
         messages.put("success", String.format("Hello, your name is %s and your age is %s!", name, age));
     }

     request.getRequestDispatcher("/WEB-INF/hello.jsp").forward(request, response);
 }*/
 }
}

