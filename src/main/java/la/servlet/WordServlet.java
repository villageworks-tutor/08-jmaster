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
		// リクエストパラメータを取得
		String english = request.getParameter("english");
		String japanese = request.getParameter("japanese");
		WordBean bean = new WordBean(japanese, english);
		dictionary.add(bean);
		
		// 画面遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listWord.jsp");
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
