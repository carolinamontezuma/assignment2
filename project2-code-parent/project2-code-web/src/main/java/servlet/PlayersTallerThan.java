
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
import ejb.ManagerEJBRemote;
import dto.UserDTO;
import ejb.UserEJBRemote;

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
	@EJB
	UserEJBRemote ejbuser;
	@EJB
	ManagerEJBRemote ejbmanager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlayersTallerThan() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		RequestDispatcher dispatcher;
		// Adicionar elementos à BD
		if (request.getParameter("fill") != null) {
			ejbremote.populate();
			ejbuser.populate();
			ejbmanager.populate();
			out.println("<h1>Populate Content: OK!</h1>");
		}

		// -------------------- USER SCREEN -------------------------------//
		// Listar a watch list do utilizador
		if (request.getParameter("listWatchList") != null) {

			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}

		// Listar todo o conteúdo da aplicação
		if (request.getParameter("listAll") != null) {
			List<ContentDTO> content = ejbremote.seeAllContent(1);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		// Editar a informação do utilizador
		if (request.getParameter("editPersonal") != null) {

			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}
		// -------------------- LIST CONTENTS -------------------------------//

		// ORDENAR
		if (request.getParameter("ascAll") != null) {
			List<ContentDTO> content = ejbremote.seeAllContent(3);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("descAll") != null) {
			List<ContentDTO> content = ejbremote.seeAllContent(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("ascDirector") != null) {
			List<String> content = ejbremote.getDirectorName(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("descDirector") != null) {
			List<String> content = ejbremote.getDirectorName(3);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("ascCategory") != null) {
			List<String> content = ejbremote.getCategories(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("descCategory") != null) {
			List<String> content = ejbremote.getCategories(3);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}

		// ----- FILTRAR ---
		// Por director
		if (request.getParameter("filtroD") != null) {
			List<String> names = ejbremote.getDirectorName(1);
			request.setAttribute("allContents", names);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		// Por categoria
		if (request.getParameter("filtroC") != null) {
			List<String> content = ejbremote.getCategories(1);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listCategories");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("filtroY") != null) {
			List<ContentDTO> content = ejbremote.seeAllContent(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}

		// Listar todo o conteúdo de um determinado director
		if (request.getParameter("listDirector") != null) {
			String directorName = request.getParameter("director");
			List<ContentDTO> content = ejbremote.seeContentFromDirector(directorName);
			// definir a ação
			request.setAttribute("action", "director");

			request.setAttribute("listDirector", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		// Listar todo o conteúdo de um determinado director
		if (request.getParameter("listCategory") != null) {
			String category = request.getParameter("category");
			List<ContentDTO> content = ejbremote.seeContentFromCategory(category);
			// definir a ação
			request.setAttribute("action", "category");

			request.setAttribute("listCategory", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		// Listar todo o conteúdo de um determinado director
		if (request.getParameter("listYears") != null) {
			int year1 = Integer.parseInt(request.getParameter("year"));
			int year2 = Integer.parseInt(request.getParameter("year"));
			List<ContentDTO> content = ejbremote.seeContentFromYears(year1, year2);
			// definir a ação
			request.setAttribute("action", "years");

			request.setAttribute("listYears", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}

		// -------------------- EDITAR INFORMAÇÃO PESSOAL
		// -------------------------------//
		if (request.getParameter("editPersonal") != null) {
			// List<String> content = ejbremote.getCategories(3);
			// request.setAttribute("allContents", content);
			// request.setAttribute("action","listDirectors");
			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		RequestDispatcher dispatcher;

		// Registar (criar conta)
		if (request.getParameter("registar") != null) {
			String name = request.getParameter("fname");
			String pass = request.getParameter("fpass");
			String email = request.getParameter("fmail");
			String card = request.getParameter("fcard");
			ejbuser.addAccount(name, pass, email, card);
			request.setAttribute("user", name);
			dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			dispatcher.forward(request, response);
		}

		// Login
		if (request.getParameter("login") != null) {
			String fmail = request.getParameter("fmail");
			String pass = request.getParameter("fpass");
			boolean hasUser = ejbuser.validateLogin(fmail, pass);
			boolean hasManager = ejbmanager.validateLogin(fmail, pass);
			
			if(hasUser && !hasManager)
			{
				//TODO: login como user
			}
			else if(!hasUser && hasManager)
			{
				//TODO: login como manager
			}
			else
			{
				//TODO: NOPE ->não pode haver user e manager com o mesmo login
			}
			
			// ejbuser.addAccount(name, pass, email, card);
			// request.setAttribute("user", name);
			// dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			// dispatcher.forward(request, response);
		}
	}
}
