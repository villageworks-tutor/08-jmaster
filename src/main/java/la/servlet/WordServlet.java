package la.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.WordBean;

/**
 * Servlet implementation class WordServlet
 */
@WebServlet("/WordServlet")
public class WordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションの開始
		HttpSession session = request.getSession();
		// 単語一覧を取得
		@SuppressWarnings("unchecked")
		List<WordBean> dictionary = (List<WordBean>) session.getAttribute("dictionary");
		if (dictionary == null) {
			dictionary = new ArrayList<WordBean>();
			session.setAttribute("dictionary", dictionary);
		}
		
		// リクエストパラメータの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// リクエストパラメータを取得：処理分岐用actionキーの取得
		String action = request.getParameter("action");
		// 遷移先URLの初期化
		String nextPage = "";
		if (action.equals("showRegist")) {
			// 遷移先URLの設定
			nextPage = "/registword.jsp";
		} else if (action.equals("registWord")) {
			// リクエストパラメータを取得
			String english = request.getParameter("english");
			String japanese = request.getParameter("japanese");
			// 登録する単語のインスタンス化
			WordBean bean = new WordBean(japanese, english);
			// 単語の登録
			dictionary.add(bean);
			// 遷移先URLの設定
			nextPage = "/listWord.jsp";
		} else if (action.equals("showSearch")) {
			// 遷移先URLの設定
			nextPage = "/searchword.jsp";
		}
		// 画面遷移
		gotoPage(request, response, nextPage);
	}

	/**
	 * 指定されたURLに遷移する
	 * @param request HttpServletRequestオブジェクト
	 * @param response HttpServletresponseオブジェクト
	 * @param nextPage 遷移先URL
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String nextPage) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
