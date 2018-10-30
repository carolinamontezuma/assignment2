
package main.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
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

@WebServlet("/Webflix")
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
/*
  if (request.getParameter("fill") != null) {
   ejbremote.populate();
   out.println("<h1>Populate Content: OK!</h1>");
  }
  else if(request.getParameterValues("range") != null) {
	  List<ContentDTO> content =  ejbremote.seeContentFromYears(1997, 2001);
	   out.println("<h1> Contents </h1>");
	   for (ContentDTO p : content)
	    out.println(p + "<br/>");

  }
  else if(request.getParameterValues("all") != null) {
	  List<ContentDTO> content =  ejbremote.seeAllContent();
	   out.println("<h1> Contents </h1>");
	   for (ContentDTO p : content)
	    out.println(p + "<br/>");

  }
  else if(request.getParameterValues("director") != null) {
	  List<ContentDTO> content =  ejbremote.seeContentFromDirector("John");
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

*/

     String productName = request.getParameter("director");
	   out.println(productName);

     /*String addressPath = "/listContents.jsp";
     RequestDispatcher dispatcher = request.getRequestDispatcher(addressPath);
     dispatcher.forward(request, response);*/
     if(request.getParameterValues("director") != null) {
   	  List<ContentDTO> content =  ejbremote.seeContentFromDirector(productName);
   	   out.println("<h1> Contents </h1>");
   	   for (ContentDTO p : content)
   	    out.println(p + "<br/>");

     }
    
 }


 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 doGet(request, response);

 }
}

