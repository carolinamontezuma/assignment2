
package main.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ContentDTO;
import dto.ManagerDTO;
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
	ContentEJBRemote ejbcontent;
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
	
	///////////////// Métodos para verificar login /////////////////
	private Integer getLoginToken(HttpServletRequest request)
	{
		return (Integer)request.getSession().getAttribute("loginToken");
	}
	
	private boolean loginIsAdmin(HttpServletRequest request)
	{
		Integer loginToken = getLoginToken(request);
		return loginToken == null? false : (boolean)request.getSession().getAttribute("loginIsAdmin");
	}
	
	private boolean sessionHasLogin(HttpServletRequest request)
	{
		return getLoginToken(request) != null;
	}
	////////////////////////////////////////////////////////////////
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		RequestDispatcher dispatcher;
		// Adicionar elementos à BD
		if (request.getParameter("fill") != null) {
			ejbcontent.populate();
			ejbuser.populate();
			ejbmanager.populate();
			out.println("<h1>Populate Content: OK!</h1>");
		}
		
//		///// Verificar autenticação /////
//		if (!sessionHasLogin(request))
//		{
//			//Se não estiver autenticado, é reencaminhado para a página de login
//			dispatcher = request.getRequestDispatcher("/Login.jsp");
//			dispatcher.forward(request, response);
//		}
//		//////////////////////////////////

		// -------------------- USER SCREEN -------------------------------//
		// Listar a watch list do utilizador
		if (request.getParameter("listWatchList") != null) {
			//List<ContentDTO> content = ejbremote.seeWatchList(id);
			request.setAttribute("action", "watchlist");
			//request.setAttribute("watchList", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}

		// Listar todo o conteúdo da aplicação
		if (request.getParameter("listAll") != null) {
			List<ContentDTO> content = ejbcontent.seeAllContent(1);
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
			List<ContentDTO> content = ejbcontent.seeAllContent(3);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("descAll") != null) {
			List<ContentDTO> content = ejbcontent.seeAllContent(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("ascDirector") != null) {
			List<String> content = ejbcontent.getDirectorName(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("descDirector") != null) {
			List<String> content = ejbcontent.getDirectorName(3);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("ascCategory") != null) {
			List<String> content = ejbcontent.getCategories(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("descCategory") != null) {
			List<String> content = ejbcontent.getCategories(3);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}

		// ----- FILTRAR ---
		// Por director
		if (request.getParameter("filtroD") != null) {
			List<String> names = ejbcontent.getDirectorName(1);
			request.setAttribute("allContents", names);
			request.setAttribute("action", "listDirectors");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		// Por categoria
		if (request.getParameter("filtroC") != null) {
			List<String> content = ejbcontent.getCategories(1);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "listCategories");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("filtroY") != null) {
			List<ContentDTO> content = ejbcontent.seeAllContent(2);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}

		// Listar todo o conteúdo de um determinado director
		if (request.getParameter("listDirector") != null) {
			String directorName = request.getParameter("director");
			List<ContentDTO> content = ejbcontent.seeContentFromDirector(directorName);
			// definir a ação
			request.setAttribute("action", "director");

			request.setAttribute("listDirector", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		// Listar todo o conteúdo de um determinado director
		if (request.getParameter("listCategory") != null) {
			String category = request.getParameter("category");
			List<ContentDTO> content = ejbcontent.seeContentFromCategory(category);
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
			List<ContentDTO> content = ejbcontent.seeContentFromYears(year1, year2);
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
			 int valor =ejbcontent.addNewContent(title, director,year, category);
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
			List<ContentDTO> content = ejbcontent.seeAllContent(2);
			request.setAttribute("list", content);
			 dispatcher = request.getRequestDispatcher("/removeContent.jsp");
			 dispatcher.forward(request, response);
		}
		if(request.getParameter("remove") != null) {
			int id= Integer.parseInt(request.getParameter("user_id"));
			ejbcontent.removeContent(id);
			request.setAttribute("action", "newcontent");
			 dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			 dispatcher.forward(request, response);
		}
		//----------EDITAR UM CONTEUDO -------------
		if(request.getParameter("editContent") != null) {
			request.setAttribute("action", "edit");
			List<ContentDTO> content = ejbcontent.seeAllContent(1);
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
			ejbcontent.editContent(opcao, id, newT);
		}
		if(request.getParameter("editarDirector")!=null) {
			int opcao = Integer.parseInt(request.getParameter("opcaoEdit"));
			String newT = request.getParameter("newD");
			int id = Integer.parseInt(request.getParameter("id"));
			ejbcontent.editContent(opcao, id, newT);
		}
		if(request.getParameter("editarCategoria")!=null) {
			int opcao = Integer.parseInt(request.getParameter("opcaoEdit"));
			String newT = request.getParameter("newC");
			int id = Integer.parseInt(request.getParameter("id"));
			ejbcontent.editContent(opcao, id, newT);
		}
		if(request.getParameter("editarAno")!=null) {
			int opcao = Integer.parseInt(request.getParameter("opcaoEdit"));
			String newT = request.getParameter("newY");
			int id = Integer.parseInt(request.getParameter("id"));
			ejbcontent.editContent(opcao, id, newT);
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
		if (request.getParameter("registar") != null && !sessionHasLogin(request)) {
			String name = request.getParameter("fname");
			String pass = request.getParameter("fpass");
			String email = request.getParameter("fmail");
			String card1 = request.getParameter("fcard1");
			String card2 = request.getParameter("fcard2");
			String card3 = request.getParameter("fcard3");
			String card4 = request.getParameter("fcard4");
			
			if(ejbuser.canRegister(name, email))
			{
				ejbuser.addAccount(name, pass, email, card1 + card2 + card3 + card4);
				dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				//TODO: notificar que este email já está a ser usado
				out.println("ERROR: Este login já está em uso");
			}
		}

		// Login
		if (request.getParameter("login") != null && !sessionHasLogin(request)) {
			String email = request.getParameter("fmail");
			String pass = request.getParameter("fpass");
			boolean hasUser = ejbuser.validateLogin(email, pass);
			boolean hasManager = ejbmanager.validateLogin(email, pass);
			
			if(hasUser && !hasManager)
			{
				UserDTO user = ejbuser.getUserByEmail(email);
				request.getSession().setMaxInactiveInterval(10);
				request.getSession().setAttribute("loginName", user.getUsername());
				request.getSession().setAttribute("loginToken", user.getID());
				request.getSession().setAttribute("loginIsAdmin", hasManager);
				ejbuser.userLoggedIn(user.getID());
				dispatcher = request.getRequestDispatcher("/userScreen.jsp");
				dispatcher.forward(request, response);
			}
			else if(!hasUser && hasManager)
			{
				ManagerDTO manager = ejbmanager.getManagerByEmail(email);
				request.getSession().setMaxInactiveInterval(10);
				request.getSession().setAttribute("loginName", manager.getUsername());				
				request.getSession().setAttribute("loginToken", manager.getID());
				request.getSession().setAttribute("loginIsAdmin", hasManager);
				dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
				dispatcher.forward(request, response);
			}
			else if(hasUser && hasManager)
			{
				out.println("ERROR: can't have users and managers with the same login");
			}
			else
			{
				//TODO: não existe user nem manager com este login
				out.println("O login não existe!");
			}
			
		}
		
		// Logout
		if (request.getParameter("logout") != null) {
			if(sessionHasLogin(request))
			{
				request.getSession().invalidate();
				
				dispatcher = request.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
