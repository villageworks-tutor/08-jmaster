package la.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import la.bean.FortuneBean;

/**
 * Servlet implementation class FortuneServlet
 */
@WebServlet("/FortuneServlet")
public class FortuneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String[] colors = new String[] {"赤", "黃", "白"};
	private String[] items = new String[] {"タオル", "カバン", "腕時計"};

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		int month = Integer.parseInt(request.getParameter("month"));
		// 乱数を発生させる準備
		Random random = new Random();
		// ラッキーアイテムの取得
		String item = items[random.nextInt(3)];
		// ラッキーカラーの取得
		String color = colors[random.nextInt(3)];
		// ランクの取得
		int rank = random.nextInt(12) + 1;
		// FortuneBeanをインスタンス化
		FortuneBean bean = new FortuneBean(month, item, color, rank);
		// リクエストスコープに登録
		request.setAttribute("fortune", bean);
		// 画面遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/fortune.jsp");
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
