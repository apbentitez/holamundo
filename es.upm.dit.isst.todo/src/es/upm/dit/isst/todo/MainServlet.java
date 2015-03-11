package es.upm.dit.isst.todo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.todo.dao.TodoDAO;
import es.upm.dit.isst.todo.dao.TodoDAOImpl;
import es.upm.dit.isst.todo.model.Todo;
public class MainServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
public void doGet(HttpServletRequest req, HttpServletResponse resp)
throws IOException {
TodoDAO dao = TodoDAOImpl.getInstance();
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();
String url = userService.createLoginURL(req.getRequestURI());
String urlLinktext = "Login";
List<Todo> todos = new ArrayList<Todo>();

if (user != null){
 url = userService.createLogoutURL(req.getRequestURI());
 urlLinktext = "Logout";
 todos = dao.getTodos(user.getNickname());
 }
 req.getSession().setAttribute("user", user);
 req.getSession().setAttribute("todos", new ArrayList<Todo>(todos));
 req.getSession().setAttribute("url", url);
 req.getSession().setAttribute("urlLinktext", urlLinktext);
 RequestDispatcher view = req.getRequestDispatcher("TodoApplication.jsp");
 try {
	view.forward(req, resp);
} catch (ServletException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
 }
 }