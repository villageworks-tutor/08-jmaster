package kadai5add.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kadai5.dao.DAOException;
import kadai5add.bean.EmpBean;
import kadai5add.dao.EmpDAO;

/**
 * Servlet implementation class EmpServlet
 */
@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// 処理分岐用のリクエストパラメータを取得
		String action = request.getParameter("action");
		// 処理の分岐
		if (action.equals("step1")) {
			try {
				// 入力されたリクエストパラメータを取得
				int lowerAge = Integer.parseInt(request.getParameter("lowerAge"));
				int upperAge = Integer.parseInt(request.getParameter("upperAge"));
				// 入力値検査・範囲チェック：課題に年齢の指定がなかったので、暫定的に年齢の範囲を「0以上120以下」とした。
				if (!(0 <= lowerAge && lowerAge <= 120) || !(0 <= upperAge && upperAge <= 120)) {
					this.gotoError(request, response, "正しい年齢を入力してください。");
					return;
				}
				// 入力値検査・大小チェック
				if (lowerAge > upperAge) {
					this.gotoError(request, response, "正しい年齢を入力してください。");
					return;
				}
				// 指定された年齢の範囲の従業員リストを取得
				EmpDAO dao = new EmpDAO();
				List<EmpBean> list = dao.findByAge(lowerAge, upperAge);
				// 取得した従業員リストをスコープに登録
				request.setAttribute("empList", list);
				// 画面遷移
				this.gotoPage(request, response, "/emp.jsp");
			} catch (NumberFormatException e) {
				this.gotoError(request, response, "正しい年齢を入力してください。");
			} catch (DAOException e) {
				e.printStackTrace();
				this.gotoError(request, response, e.getMessage());
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * 指定されたURLに遷移する
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param nextURL  遷移先URL
	 * @throws ServletException
	 * @throws IOException
	 */
	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String nextURL) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextURL);
		dispatcher.forward(request, response);
	}

	/**
	 * エラーページに遷移する
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param message  エラーメッセージ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void gotoError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("message", message);
		this.gotoPage(request, response, "/error.jsp");
	}

}
