
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

import data.User;
import dto.ContentDTO;
import dto.ManagerDTO;
import ejb.ContentEJBRemote;
import ejb.ManagerEJBRemote;
import dto.UserDTO;
import ejb.UserEJBRemote;
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
		 
		new java.util.Timer().schedule(new java.util.TimerTask() {
	        @Override
	        public void run() {
	        	List<UserDTO> user = ejbuser.listAllUsers();
	        	String body= new String();
	        	for(UserDTO u : user) {
	        		Date d = new Date();
	        		int diffInDays = (int)( (d.getTime() - u.getDate().getTime())/ (1000 * 60 * 60 * 24)); 
	        		if(diffInDays==0) {
	        			Random rand = new Random();
	        			int randomNum = rand.nextInt((9 - 0) + 1) + 0;
	        			if(randomNum<=5) {
	        				body="Sucesso ao efetuar pagamento";
	        			}
	        			else {
	        				body="Erro ao efetuar pagamento";
	        			}
	        			ejbuser.send(u.getEmail(),"pagamento",body);
	        		}
	        		else {
	        			continue;
	        		}
	        	}
	        }
		}, 60000);
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
			//ejbuser.populate();
			ejbmanager.populate();
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
			List<ContentDTO> content = ejbcontent.seeWatchList(idUser);
			request.setAttribute("action", "watchlist");
			request.setAttribute("watchList", content);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		//Adicionar conteudo a watchlist do utilizador
		if(request.getParameter("addtowl") != null) {
			int idUser = getLoginToken(request);
			int idContent = Integer.parseInt(request.getParameter("content_id"));
			ejbcontent.addContentToWatchList(idContent, idUser);

			List<ContentDTO> content = ejbcontent.seeAllContent(1);
			List<ContentDTO> wl = ejbcontent.seeWatchList(getLoginToken(request));
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("allContents", content);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("wl", wl);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");

			List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
			request.setAttribute("suggestedContent", suggestedContent);
			dispatcher = request.getRequestDispatcher("/userScreen.jsp");
			dispatcher.forward(request, response);

		}
		//Remover conteudo da watchlist
		if(request.getParameter("removeFromWL") != null) {
			int idUser = getLoginToken(request);
			int idContent = Integer.parseInt(request.getParameter("content_id"));
			ejbcontent.removeContentFromWatchList(idContent, idUser);
			List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
			request.setAttribute("suggestedContent", suggestedContent);
			dispatcher = request.getRequestDispatcher("/userScreen.jsp");
			dispatcher.forward(request, response);
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
			List<ContentDTO> content = ejbcontent.seeAllContent(1);
			List<ContentDTO> wl = ejbcontent.seeWatchList(getLoginToken(request));
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("allContents", content);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("wl", wl);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		//Aplicar os filtros - nome diretor/ categoria/ anos
		if(request.getParameter("filtrar")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			request.setAttribute("lastDirectorName", diretor);
			request.setAttribute("lastCategoryName", categoria);
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		
		if(request.getParameter("OrderTitleAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,3,true);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("OrderTitleDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,3,false);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("OrderCategoryAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,5,true);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("OrderCategoryDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,5,false);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);	
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("OrderDirectorAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,2,true);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("OrderDirectorDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,2,false);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("OrderYearAsc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,4,true);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("OrderYearDesc")!=null) {
			String diretor = request.getParameter("directorName");
			String categoria = request.getParameter("categoryName");
			int anoMin = -1;
			int anoMax = -1;
			if(request.getParameter("minYear").matches("[0-9]+")) {
				anoMin = Integer.parseInt(request.getParameter("minYear"));
			}
			if(request.getParameter("maxYear").matches("[0-9]+")) {
				anoMax = Integer.parseInt(request.getParameter("maxYear"));
			}
			if(anoMin > anoMax) {
				anoMin=-1;
			}
			List<ContentDTO> content = ejbcontent.aplicarFiltros(diretor, categoria,anoMin,anoMax,4,false);
			List<String> diretores = ejbcontent.getDirectorName(1);
			List<String> categorias = ejbcontent.getCategories(1);
			request.setAttribute("diretores", diretores);
			request.setAttribute("categorias", categorias);
			request.setAttribute("allContents", content);
			request.setAttribute("action", "allContents");
			dispatcher = request.getRequestDispatcher("/listContents.jsp");
			dispatcher.forward(request, response);
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
			 int valor =ejbcontent.addNewContent(title, director,year, category);
			 request.setAttribute("action", "teste");
			 request.setAttribute("valor", valor);
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
			int id= Integer.parseInt(request.getParameter("content_id"));
			ejbcontent.removeContent(id);
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
		if(request.getParameter("continueManager") != null) {
			dispatcher = request.getRequestDispatcher("/managerScreen.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("backUser") != null) {
			List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
			request.setAttribute("suggestedContent", suggestedContent);
			dispatcher = request.getRequestDispatcher("/userScreen.jsp");
			dispatcher.forward(request, response);
		}
		if(request.getParameter("botaoTitulo")!=null) {
			String multimedia =request.getParameter("multimedia");
			StringBuilder sb = new StringBuilder();
			sb.append(multimedia);
			String pathImage = sb.toString()+".png";
			String pathMovie = sb.toString()+".mp4";
			request.setAttribute("pathImage", pathImage);
			request.setAttribute("pathMovie", pathMovie);
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
			
			if(ejbuser.canRegister(name, email))
			{
				ejbuser.addAccount(name, PasswordHasher.plainTextToHash(pass), email, card1 + card2 + card3 + card4);
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
			boolean hasUser = ejbuser.validateLogin(email, PasswordHasher.plainTextToHash(pass));
			boolean hasManager = ejbmanager.validateLogin(email, PasswordHasher.plainTextToHash(pass));
			
			if(hasUser && !hasManager)
			{
				UserDTO user = ejbuser.getUserByEmail(email);
				request.getSession().setMaxInactiveInterval(60);
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
				request.getSession().setMaxInactiveInterval(60);
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
				request.getSession().invalidate();
			request.setAttribute("source", "servlet");
			dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		}
		
		// Editar conta
		if (request.getParameter("saveEdit") != null && sessionHasLogin(request)) {
			String name = request.getParameter("fname");
			String pass = request.getParameter("fpass");
			String email = request.getParameter("fmail");
			String card1 = request.getParameter("fcard1");
			String card2 = request.getParameter("fcard2");
			String card3 = request.getParameter("fcard3");
			String card4 = request.getParameter("fcard4");
			String card3_hidden = request.getParameter("fcard3_hidden");
			String card4_hidden = request.getParameter("fcard4_hidden");
			
			//TODO: check if user can use these new values (email not in use, etc)
			if(card3.isEmpty() || card4.isEmpty())
			{
				ejbuser.editPersonalInformation(getLoginToken(request), name, PasswordHasher.plainTextToHash(pass), email, card1+card2+card3_hidden+card4_hidden);
				List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
				request.setAttribute("suggestedContent", suggestedContent);
				dispatcher = request.getRequestDispatcher("/userScreen.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				ejbuser.editPersonalInformation(getLoginToken(request), name, PasswordHasher.plainTextToHash(pass), email, card1+card2+card3+card4);
				List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
				request.setAttribute("suggestedContent", suggestedContent);
				dispatcher = request.getRequestDispatcher("/userScreen.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// Cancelar editar conta
		if (request.getParameter("cancelEdit") != null && sessionHasLogin(request)) {
			List<ContentDTO> suggestedContent = ejbcontent.getSuggestedCotent(getLoginToken(request));
			request.setAttribute("suggestedContent", suggestedContent);
			dispatcher = request.getRequestDispatcher("/userScreen.jsp");
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
}
