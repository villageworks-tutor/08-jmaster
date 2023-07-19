package la.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/ShowServlet")
public class ShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションの開始
		HttpSession session = request.getSession(false);
		// セッションからメッセージリストを取得
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("messages");
		
		// レスポンスヘッダの設定
		response.setContentType("text/html; charset=utf-8");
		// プリントライタの取得
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"ja\"><head><title>掲示板 ─ JM10</title></head><body>");
		
		out.println("\t<form action=\"/jmaster/BbsServlet2\" method=\"post\">");
		out.println("\t\t名前：<br />");
		out.println("\t\t<input type=\"text\" name=\"name\" /><br />");
		out.println("\t\tメッセージ：<br />");
		out.println("\t\t<textarea name=\"message\" rows=\"5\" cols=\"36\"></textarea><br />");
		out.println("\t\t<input type=\"submit\" value=\"書き込み\" />");
		out.println("\t\t<input type=\"hidden\" name=\"action\" value=\"write\" />");
		out.println("\t</form>");
		out.println("\t<hr />");
		
		// メッセージリストの内容を表示
		for (String comment : messages) {
			out.println(comment + "<br />");
			out.println("\t<hr />");
		}
			
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
