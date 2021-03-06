
package main.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ContentDTO;
import dto.ManagerDTO;
import ejb.ContentEJBLocal;
import ejb.ManagerEJBLocal;
import dto.UserDTO;
import ejb.UserEJBLocal;
import utils.PasswordHasher;
import java.util.TimerTask;

/**
 * Servlet implementation class PlayersTallerThan
 */

//http://localhost:8080/project2-code-web/Webflix?fill=1
//url = http://localhost:8080/project2-code-web/Webflix?category=comedy
@WebServlet("/PlayersTallerThan")
public class PlayersTallerThan extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ContentEJBLocal ejbcontent;
	@EJB
	UserEJBLocal ejbuser;
	@EJB
	ManagerEJBLocal ejbmanager;
	
	private boolean hasPopulated = false;
	
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
		
		if(!hasPopulated)
		{
			ejbcontent.populate();
			ejbuser.populate();
			ejbmanager.populate();
			hasPopulated = true;
		}
		
		request.setAttribute("source", "servlet");
		
		if (request.getParameter("nullSource") != null) {
			if (!sessionHasLogin(request))
			{
				dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				if(loginIsAdmin(request))
				{
					dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
					request.setAttribute("suggestedContent", suggestedContent);
					dispatcher = request.getRequestDispatcher("/userScreen.jsp");
					dispatcher.forward(request, response);
				}
			}
			return;
		}
		
		if (request.getParameter("Login") != null) {
			dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (request.getParameter("Registar") != null) {
			dispatcher = request.getRequestDispatcher("/Registar.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		///// Verificar autenticação /////
		if (!sessionHasLogin(request))
		{
			//Se não estiver autenticado, é reencaminhado para a página de login
			dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		//////////////////////////////////
		// -------------------- USER SCREEN -------------------------------//
		if(request.getParameter("userScreen")!=null) {
			List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
			request.setAttribute("suggestedContent", suggestedContent);
			dispatcher = request.getRequestDispatcher("/userScreen.jsp");
			dispatcher.forward(request, response);
		}
		// Listar a watch list do utilizador
		if (request.getParameter("listWatchList") != null) {
			int idUser = getLoginToken(request);
			
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(idUser);
			request.setAttribute("wl", wl);
			request.setAttribute("action", "watchlist");
			ListContents(request, response);
		}
		//Adicionar conteudo a watchlist do utilizador
		if(request.getParameter("addtowl") != null) {
			int idUser = getLoginToken(request);
			int idContent = Integer.parseInt(request.getParameter("content_id"));
			ejbcontent.addContentToWatchList(idContent, idUser);

			List<ContentDTO> content = ejbcontent.aplicarFiltros();
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request));
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);

		}
		//Remover conteudo da watchlist
		if(request.getParameter("removeFromWL") != null) {
			int idUser = getLoginToken(request);
			int idContent = Integer.parseInt(request.getParameter("content_id"));
			ejbcontent.removeContentFromWatchList(idContent, idUser);

			List<ContentDTO> content = ejbcontent.aplicarFiltros();
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request));
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		// Editar a informação do utilizador
		if (request.getParameter("editPersonal") != null) {
			request.setAttribute("userDTO", ejbuser.getUserByID(getLoginToken(request)));
			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}
		// -------------------- LIST CONTENTS -------------------------------//
		
		// Listar todo o conteúdo da aplicação
		if (request.getParameter("listAll") != null) {
			List<ContentDTO> content = ejbcontent.aplicarFiltros();
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request));
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", "allContents");
			ListContents(request, response);
		}
		//Aplicar os filtros - nome diretor/ categoria/ anos
		if(request.getParameter("filtrar")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));

			if(anoMin > anoMax && anoMax != -1)
				anoMin=-1;
			
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request));
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		
		if(request.getParameter("OrderTitleAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));

			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,3,true);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 3, true);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		if(request.getParameter("OrderTitleDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));

			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,3,false);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 3, false);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		if(request.getParameter("OrderCategoryAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));

			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,5,true);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 5, true);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		if(request.getParameter("OrderCategoryDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));

			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,5,false);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 5, false);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		if(request.getParameter("OrderDirectorAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
	
			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,2,true);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 2, true);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		if(request.getParameter("OrderDirectorDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));

			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,2,false);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 2, false);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		if(request.getParameter("OrderYearAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));

			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,4,true);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 4, true);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		if(request.getParameter("OrderYearDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear") != null && request.getParameter("minYear").matches("[0-9]+"))
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			if(request.getParameter("maxYear") != null && request.getParameter("maxYear").matches("[0-9]+"))
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
				
			if(anoMin > anoMax)
				anoMin=-1;
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,4,false);
			List<ContentDTO> wl = ejbcontent.aplicarFiltrosWL(getLoginToken(request), 4, false);
			request.setAttribute("allContents", content);
			request.setAttribute("wl", wl);
			request.setAttribute("action", request.getParameter("action"));
			ListContents(request, response);
		}
		
		
		//------------------- FUNÇÕES DO MANAGER ---------------
		if(request.getParameter("managerScreen")!=null) {
			dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			dispatcher.forward(request, response);
		}
		//--------- ADICIONAR UM CONTEUDO -------
		if (request.getParameter("newContent") != null) {
			List<String> categories = ejbcontent.getAvailableCategories();
			categories.sort(Comparator.naturalOrder());
			request.setAttribute("categories", categories);
			dispatcher = request.getRequestDispatcher("/addContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("addContent") != null) {
			 String title = request.getParameter("ftitle");
			 String director = request.getParameter("fdirector");
			 String category = request.getParameter("fcategory");
			 int year = Integer.parseInt(request.getParameter("fyear"));
			 String message = "";
			 if(ejbcontent.addNewContent(title, director,year, category))
				 message = "Successfully added content \"" + title + "\"";
			 else
				 message = "Failed to add content \"" + title + "\"";
			 request.setAttribute("message", message);
			 dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			 dispatcher.forward(request, response);
		}
		// ----- APAGAR UM CONTEUDO --------
		if(request.getParameter("deleteContent") != null) {
			List<ContentDTO> content = ejbcontent.aplicarFiltros();
			request.setAttribute("list", content);
			dispatcher = request.getRequestDispatcher("/removeContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("remove") != null) {
			int id= Integer.parseInt(request.getParameter("content_id"));
			String message = "";
			if(ejbcontent.removeContent(id))
				 message = "Successfully removed content";
			else
				message = "Failed to remove content";
			 request.setAttribute("message", message);
			dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			dispatcher.forward(request, response);
			}
		//----------EDITAR UM CONTEUDO -------------
		if(request.getParameter("editContent") != null) {
			request.setAttribute("action", "edit");
			List<ContentDTO> content = ejbcontent.aplicarFiltros();
			request.setAttribute("allContents", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("buttonEdit") != null) {
			int contentID = Integer.parseInt(request.getParameter("content_id"));
			ContentDTO content = ejbcontent.getContentByID(contentID);
			List<String> categories = ejbcontent.getAvailableCategories();
			request.setAttribute("contentDTO", content);
			categories.sort(Comparator.naturalOrder());
			request.setAttribute("categories", categories);
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("backEditContent") != null) {
			request.setAttribute("action", "edit");
			List<ContentDTO> content = ejbcontent.aplicarFiltros();
			request.setAttribute("allContents", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("backUser") != null) {
			if(!loginIsAdmin(request))
			{
				List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
				request.setAttribute("suggestedContent", suggestedContent);
				dispatcher = request.getRequestDispatcher("/userScreen.jsp");
			}
			else
				dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("backAdmin") != null) {
			List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
			request.setAttribute("suggestedContent", suggestedContent);
			dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("botaoTitulo")!=null) {
			String multimedia =request.getParameter("multimedia");
			String titulo =request.getParameter("titulo");
			StringBuilder sbImage = new StringBuilder();
			StringBuilder sbMovie = new StringBuilder();
			sbImage.append(multimedia);
			sbMovie.append(multimedia);
			sbImage.append(".png");
			sbMovie.append(".mp4");
			request.setAttribute("pathImage", sbImage.toString());
			request.setAttribute("pathMovie", sbMovie.toString());
			request.setAttribute("titulo", titulo);
			dispatcher = request.getRequestDispatcher("/multimedia.jsp");
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
		
		request.setAttribute("source", "servlet");

		// Registar (criar conta)
		if (request.getParameter("registar") != null && !sessionHasLogin(request)) {
			String name = request.getParameter("fname");
			String pass = request.getParameter("fpass");
			String email = request.getParameter("fmail");
			String card1 = request.getParameter("fcard1");
			String card2 = request.getParameter("fcard2");
			String card3 = request.getParameter("fcard3");
			String card4 = request.getParameter("fcard4");
			
			if(!ejbuser.isUsernameValid(name))
				request.setAttribute("message", "Invalid user name");
			else if(!ejbuser.isPasswordValid(pass))
				request.setAttribute("message", "Invalid password");
			else if(!ejbuser.isEmailValid(email))
				request.setAttribute("message", "Invalid email");
			else if(!ejbuser.isCreditCardValid(card1+card2+card3+card4))
				request.setAttribute("message", "Invalid credit card number");
			
			if(request.getAttribute("message") != null)
			{
				request.setAttribute("usedName", name);
				request.setAttribute("usedEmail", email);
				request.setAttribute("usedPassword", pass);
				request.setAttribute("usedCard1", card1);
				request.setAttribute("usedCard2", card2);
				request.setAttribute("usedCard3", card3);
				request.setAttribute("usedCard4", card4);
				dispatcher = request.getRequestDispatcher("/Registar.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			if(ejbuser.canRegister(email))
			{
				ejbuser.addAccount(name, PasswordHasher.plainTextToHash(pass), email, card1 + card2 + card3 + card4);
				
				UserDTO user = ejbuser.getUserByEmail(email);
				request.getSession().setMaxInactiveInterval(60);
				request.getSession().setAttribute("loginName", user.getUsername());
				request.getSession().setAttribute("loginToken", user.getID());
				request.getSession().setAttribute("loginIsAdmin", false);
				ejbuser.userLoggedIn(user.getID());
				List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
				request.setAttribute("suggestedContent", suggestedContent);
				dispatcher = request.getRequestDispatcher("/userScreen.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				request.setAttribute("message", "Email already in use");
				dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
		}

		// Login
		if (request.getParameter("login") != null && !sessionHasLogin(request)) {
			String email = request.getParameter("fmail");
			String pass = request.getParameter("fpass");
			boolean hasUser = ejbuser.validateLogin(email, PasswordHasher.plainTextToHash(pass));
			boolean hasManager = ejbmanager.validateLogin(email, PasswordHasher.plainTextToHash(pass));
			
			if(hasUser && !hasManager)
			{
				UserDTO user = ejbuser.getUserByEmail(email);
				request.getSession().setMaxInactiveInterval(60*10);
				request.getSession().setAttribute("loginName", user.getUsername());
				request.getSession().setAttribute("loginToken", user.getID());
				request.getSession().setAttribute("loginIsAdmin", hasManager);
				ejbuser.userLoggedIn(user.getID());
				List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
				request.setAttribute("suggestedContent", suggestedContent);
				dispatcher = request.getRequestDispatcher("/userScreen.jsp");
				dispatcher.forward(request, response);
			}
			else if(!hasUser && hasManager)
			{
				ManagerDTO manager = ejbmanager.getManagerByEmail(email);
				request.getSession().setMaxInactiveInterval(60*10);
				request.getSession().setAttribute("loginName", manager.getUsername());				
				request.getSession().setAttribute("loginToken", manager.getID());
				request.getSession().setAttribute("loginIsAdmin", hasManager);
				ejbmanager.managerLoggedIn(manager.getID());
				dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
				dispatcher.forward(request, response);
			}
			else if(hasUser && hasManager)
			{
				request.setAttribute("message", "ERROR: can't have users and managers with the same login");
				dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				request.setAttribute("message", "Login does not exist");
				dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// Logout
		if (request.getParameter("logout") != null && sessionHasLogin(request)) {
			if(loginIsAdmin(request))
				ejbmanager.managerLoggedOut(getLoginToken(request));
			else
				ejbuser.userLoggedOut(getLoginToken(request));
			request.getSession().invalidate();
			request.setAttribute("source", "servlet");
			dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		}
		
		// Editar conta
		if (request.getParameter("editName") != null && sessionHasLogin(request))
		{
			String name = request.getParameter("fname");
			if(ejbuser.isUsernameValid(name))
				ejbuser.editPersonalInformation(getLoginToken(request), name, null, null, null);
			else
				request.setAttribute("message", "Invalid user name");
			UserDTO user = ejbuser.getUserByID(getLoginToken(request));
			request.setAttribute("userDTO", user);
			request.getSession().setAttribute("loginName", user.getUsername());
			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("editMail") != null && sessionHasLogin(request))
		{
			String email = request.getParameter("fmail");
			if(ejbuser.isEmailValid(email))
				ejbuser.editPersonalInformation(getLoginToken(request), null, null, email, null);
			else
				request.setAttribute("message", "Invalid email");
			request.setAttribute("userDTO", ejbuser.getUserByID(getLoginToken(request)));
			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("editPass") != null && sessionHasLogin(request))
		{
			String pass = request.getParameter("fpass");
			if(ejbuser.isPasswordValid(pass))
				ejbuser.editPersonalInformation(getLoginToken(request), null, PasswordHasher.plainTextToHash(pass), null, null);
			else
				request.setAttribute("message", "Invalid password");
			request.setAttribute("userDTO", ejbuser.getUserByID(getLoginToken(request)));
			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("editCredit") != null && sessionHasLogin(request))
		{
			String card1 = request.getParameter("fcard1");
			String card2 = request.getParameter("fcard2");
			String card3 = request.getParameter("fcard3");
			String card4 = request.getParameter("fcard4");
			if(ejbuser.isCreditCardValid(card1+card2+card3+card4))
				ejbuser.editPersonalInformation(getLoginToken(request), null, null, null, card1+card2+card3+card4);
			else
				request.setAttribute("message", "Invalid credit card number");
			request.setAttribute("userDTO", ejbuser.getUserByID(getLoginToken(request)));
			dispatcher = request.getRequestDispatcher("/editPersonal.jsp");
			dispatcher.forward(request, response);
		}
		
		// Editar conteúdo
		if (request.getParameter("editTitle") != null && sessionHasLogin(request))
		{
			String title = request.getParameter("title");
			int contentID = Integer.parseInt(request.getParameter("content_id"));
			if(ejbcontent.isTitleValid(title))
				ejbcontent.editContent(contentID, title, null, null, -1);
			else
				request.setAttribute("message", "Invalid title");
			ContentDTO content = ejbcontent.getContentByID(contentID);
			List<String> categories = ejbcontent.getAvailableCategories();
			request.setAttribute("contentDTO", content);
			categories.sort(Comparator.naturalOrder());
			request.setAttribute("categories", categories);
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("editDirector") != null && sessionHasLogin(request))
		{
			String director = request.getParameter("director");
			int contentID = Integer.parseInt(request.getParameter("content_id"));
			if(ejbcontent.isDirectorValid(director))
				ejbcontent.editContent(contentID, null, director, null, -1);
			else
				request.setAttribute("message", "Invalid director");
			ContentDTO content = ejbcontent.getContentByID(contentID);
			List<String> categories = ejbcontent.getAvailableCategories();
			request.setAttribute("contentDTO", content);
			categories.sort(Comparator.naturalOrder());
			request.setAttribute("categories", categories);
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("editCategory") != null && sessionHasLogin(request))
		{
			String category = request.getParameter("category");
			int contentID = Integer.parseInt(request.getParameter("content_id"));
			if(ejbcontent.isCategoryValid(category))
				ejbcontent.editContent(contentID, null, null, category, -1);
			else
				request.setAttribute("message", "Invalid category");
			ContentDTO content = ejbcontent.getContentByID(contentID);
			List<String> categories = ejbcontent.getAvailableCategories();
			request.setAttribute("contentDTO", content);
			categories.sort(Comparator.naturalOrder());
			request.setAttribute("categories", categories);
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("editYear") != null && sessionHasLogin(request))
		{
			int year = Integer.parseInt(request.getParameter("year"));
			int contentID = Integer.parseInt(request.getParameter("content_id"));
			if(ejbcontent.isYearValid(year))
				ejbcontent.editContent(contentID, null, null, null, year);
			else
				request.setAttribute("message", "Invalid year");
			ContentDTO content = ejbcontent.getContentByID(contentID);
			List<String> categories = ejbcontent.getAvailableCategories();
			request.setAttribute("contentDTO", content);
			categories.sort(Comparator.naturalOrder());
			request.setAttribute("categories", categories);
			dispatcher = request.getRequestDispatcher("/editContent.jsp");
			dispatcher.forward(request, response);
		}
		
		// Apagar conta
		if (request.getParameter("deleteAccount") != null && sessionHasLogin(request)) {
			ejbuser.deleteAccount(getLoginToken(request));
			request.getSession().invalidate();
			request.setAttribute("source", "servlet");
			dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);

		}
	}
	
	private void ListContents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher;
		request.setAttribute("diretores", ejbcontent.getDirectorName(1));
		request.setAttribute("categorias", ejbcontent.getCategories(1));
		request.setAttribute("lastDirectorName", request.getParameter("directorName"));
		request.setAttribute("lastCategoryName", request.getParameter("categoryName"));
		request.setAttribute("lastMinYear", request.getParameter("minYear"));
		request.setAttribute("lastMaxYear", request.getParameter("maxYear"));
		dispatcher = request.getRequestDispatcher("/listContents.jsp");
		dispatcher.forward(request, response);
	}
	
}
