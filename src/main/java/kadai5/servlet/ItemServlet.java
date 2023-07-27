package kadai5.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kadai5.bean.ItemBean;
import kadai5.dao.DAOException;
import kadai5.dao.ItemDAO;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの解析は特になし：送信データがないため
		try {
			// モデルを使って全商品を取得
			ItemDAO dao = new ItemDAO();
			List<ItemBean> list = dao.findAll();
			// リストをスコープに入れて画面遷移
			request.setAttribute("items", list);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/showItem.jsp");
			// 商品一覧画面に遷移
			dispatcher.forward(request, response);
		} catch (DAOException e) {
			e.printStackTrace();
			// スコープにメッセージを登録
			request.setAttribute("message", "内部エラーが発生しました。");
			// エラー画面に遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/errInternal.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
