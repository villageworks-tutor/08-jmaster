package la.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		// リクエストパラメータ（処理分岐用actionキー）を取得
		String action = request.getParameter("action");
		
		// 取得したactionキーの値により処理を分岐：switch-case文で表現してもかまわない（switch文のほうが可読性は上がるかもしれないが、ここでは慣習的な表現を採用した）
		if (action.equals("write")) {
			// 処理に必要なリクエストパラメータを取得
			String message = request.getParameter("message");
			String name = request.getParameter("name");
			
			// 書き込まれた日時を取得
			LocalDateTime now = LocalDateTime.now();
			// 日時の書式パターンを設定：「yyyy/MM/dd HH:mm:ss」形式
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
			// 書き込まれた日時の文字列を取得
			String nowString = formatter.format(now);
			
			// 出力用文字列を生成
			String output = nowString + "　" + name + "："+ message;
			// メッセージリストに追加
			messages.add(output);
		} else if (action.equals("delete")) {
			// 処理に必要なリクエストパラメータを取得：リクエストパラメータの取得と整数への変換の処理を行で分けても構わない
			int row = Integer.parseInt(request.getParameter("row"));
			// 該当するメッセージをメッセージリストから削除
			messages.remove(row - 1);
		}
		
		// レスポンスヘッダの設定
		response.setContentType("text/html; charset=utf-8");
		// プリントライタの取得
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"ja\"><head><title>掲示板 ─ JM10</title></head><body>");
		out.println("\t<form action=\"/jmaster/BbsServlet\" method=\"post\">");
		out.println("\t\t名前：<br />");
		out.println("\t\t<input type=\"text\" name=\"name\" /><br />");
		out.println("\t\tメッセージ：<br />");
		out.println("\t\t<textarea name=\"message\" rows=\"5\" cols=\"36\"></textarea><br />");
		out.println("\t\t<input type=\"submit\" value=\"書き込み\" />");
		out.println("\t\t<input type=\"hidden\" name=\"action\" value=\"write\" />");
		out.println("\t</form>");
		out.println("\t<hr />");
		
		// メッセージリストの内容を表示
		int rows = 0; // 行カウンタ
		for (String message : messages) {
			rows++;
			out.println(message + "［<a href=\"/jmaster/BbsServlet?action=delete&row=" + rows + "\">削除</a>］<br />");
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
