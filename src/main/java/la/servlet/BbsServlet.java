package la.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BbsServlet
 */
@WebServlet("/BbsServlet")
public class BbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// メッセージを蓄積しておくメッセージリスト
	List<String> messages = new ArrayList<String>();
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コード設定
		request.setCharacterEncoding("utf-8");
		// リクエストパラメータを取得
		String message = request.getParameter("message");
		
		// メッセージリストに追加
		messages.add(message);
		
		// レスポンスヘッダの設定
		response.setContentType("text/html; charset=utf-8");
		// プリントライタの取得
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"ja\"><head><title>掲示板 ─ JM10</title></head><body>");
		out.println("\tメッセージ：<br />");
		out.println("\t<form action=\"#\" method=\"post\">");
		out.println("\t\t<textarea name=\"message\" rows=\"5\" cols=\"36\"></textarea><br />");
		out.println("\t\t<input type=\"submit\" value=\"書き込み\" />");
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
		// ここではdoGetメソッドに処理を丸投げするようにしているが、doPostに処理を実装しても構わない。
		doGet(request, response);
	}

}
