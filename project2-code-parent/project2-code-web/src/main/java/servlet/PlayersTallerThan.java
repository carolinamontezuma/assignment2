
package main.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
		
		//apresentar os detalhes dos contents
		if (request.getParameter("details") != null) {
			request.setAttribute("action", "details");
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
		//Listar a Watch List
		if (request.getParameter("listWatchList") != null) {
			//List<ContentDTO> content = ejbremote.seeWatchList(id);
			request.setAttribute("action", "watchlist");
			//request.setAttribute("watchList", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		
		
		//------------------- FUNÇÕES DO MANAGER ---------------
		if(request.getParameter("managerscreen")!=null) {
			request.setAttribute("action", "newcontent");
			dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			dispatcher.forward(request, response);
		}
		//--------- ADICIONAR UM CONTEUDO -------
		if (request.getParameter("newContent") != null) {
			dispatcher = request.getRequestDispatcher("/addContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("addContent") != null) {
			 String title = request.getParameter("ftitle");
			 String director = request.getParameter("fdirector");
			 String category = request.getParameter("fcategory");
			 int year = Integer.parseInt(request.getParameter("fyear"));
			 int valor =ejbmanager.addNewContent(title, director,year, category);
			 request.setAttribute("action", "teste");
			 request.setAttribute("valor", valor);
			 dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			 dispatcher.forward(request, response);
		}
		if(request.getParameter("continueManager") != null) {
			request.setAttribute("action", "newcontent");
			 dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			 dispatcher.forward(request, response);
		}
		// ----- APAGAR UM CONTEUDO --------
		if(request.getParameter("deleteContent") != null) {
			List<ContentDTO> content = ejbremote.seeAllContent(2);
			request.setAttribute("list", content);
			 dispatcher = request.getRequestDispatcher("/removeContent.jsp");
			 dispatcher.forward(request, response);
		}
		if(request.getParameter("remove") != null) {
			int id= Integer.parseInt(request.getParameter("user_id"));
			ejbremote.removeContent(id);
			request.setAttribute("action", "newcontent");
			 dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			 dispatcher.forward(request, response);
		}
		//----------EDITAR UM CONTEUDO -------------
		if(request.getParameter("editContent") != null) {
			request.setAttribute("action", "edit");
			List<ContentDTO> content = ejbremote.seeAllContent(1);
			request.setAttribute("allContents", content);
			 dispatcher = request.getRequestDispatcher("/listContents.jsp");
			 dispatcher.forward(request, response);
		}
		if(request.getParameter("buttonEdit") != null) {
			request.setAttribute("action", "selectEdit");
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("editTitle") != null) {
			request.setAttribute("action", "edittitle");
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("editDirector") != null) {
			request.setAttribute("action", "editdirector");
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("editCategory") != null) {
			request.setAttribute("action", "editcategory");
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("editYear") != null) {
			request.setAttribute("action", "edityear");
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("editarTitulo")!=null) {
			int opcao = Integer.parseInt(request.getParameter("opcaoEdit"));
			String newT = request.getParameter("newT");
			int id = Integer.parseInt(request.getParameter("id"));
			ejbremote.editContent(opcao, id, newT);
		}
		if(request.getParameter("editarDirector")!=null) {
			int opcao = Integer.parseInt(request.getParameter("opcaoEdit"));
			String newT = request.getParameter("newD");
			int id = Integer.parseInt(request.getParameter("id"));
			ejbremote.editContent(opcao, id, newT);
		}
		if(request.getParameter("editarCategoria")!=null) {
			int opcao = Integer.parseInt(request.getParameter("opcaoEdit"));
			String newT = request.getParameter("newC");
			int id = Integer.parseInt(request.getParameter("id"));
			ejbremote.editContent(opcao, id, newT);
		}
		if(request.getParameter("editarAno")!=null) {
			int opcao = Integer.parseInt(request.getParameter("opcaoEdit"));
			String newT = request.getParameter("newY");
			int id = Integer.parseInt(request.getParameter("id"));
			ejbremote.editContent(opcao, id, newT);
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
