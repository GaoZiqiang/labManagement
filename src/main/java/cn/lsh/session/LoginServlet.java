package cn.lsh.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lsh.bean.Db;
import cn.lsh.bean.User;

@RequestScoped
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	// private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println("打印获取到的username: " + username);
		System.out.println("打印获取到的password: " + password);
		List<User> list = Db.getAllUser();
		for (User user : list) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				// 登陆成功，把user存入session域对象中
				request.getSession().setAttribute("user", user);
				// 重定向到index.jsp
				//注意是绝对路径
				response.sendRedirect("index.jsp");
				return;
			} else {
				out.print("登录失败：用户名或密码错误");
			}
		}
		// 登陆失败

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}