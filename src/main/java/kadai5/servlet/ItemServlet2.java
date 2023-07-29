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
@WebServlet("/ItemServlet2")
public class ItemServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// リクエストパラメータの文字コード設定
			request.setCharacterEncoding("utf-8");
			// 処理分岐スイッチ用リクエストパラメータactionキーの取得
			String  action = request.getParameter("action");
			// パラメータの解析：actionキーによる処理の分岐
			if (action == null || action.isEmpty()) {
				// 初期表示または何らかのエラーがあった場合の強制表示：モデルを使って全商品を取得
				ItemDAO dao = new ItemDAO();
				List<ItemBean> list = dao.findAll();
				// 商品リストをスコープに登録
				request.setAttribute("items", list);
				// 遷移先URLを設定して画面遷移
				String nextPage = "/showItem2.jsp";
				this.gotoPage(request, response, nextPage);
			} else if (action.equals("sort")) {
				// 値段で並べ替える場合
				// リクエストパラメータからクエリ文字列keyを取得
				String key = request.getParameter("key");
				// モデルを使って並べ替えた商品リストを取得
				ItemDAO dao = new ItemDAO();
				List<ItemBean> list = null;
				if (key.equals("price_asc")) {
					// 昇順の場合
					list = dao.sortByPrice(true);
				} else {
					// 降順の場合
					list = dao.sortByPrice(false);
				}
				// 商品リストをスコープに登録
				request.setAttribute("items", list);
				// 遷移先URLを設定して画面遷移
				String nextPage = "/showItem2.jsp";
				this.gotoPage(request, response, nextPage);
			} else if (action.equals("add")) {
				// 商品を追加する場合
				// リクエストパラメータを取得
				String name = request.getParameter("name");
				int price = Integer.parseInt(request.getParameter("price"));
				// 追加する商品のインスタンスを生成
				ItemBean item = new ItemBean();
				item.setName(name);
				item.setPrice(price);
				// モデルを使って商品を追加
				ItemDAO dao = new ItemDAO();
				dao.add(item);
				// 商品一覧リストを取得
				List<ItemBean> list = dao.findAll();
				// 商品一覧リストをスコープに登録
				request.setAttribute("items", list);
				// 遷移先URLを設定して画面遷移
				String nextPage = "/showItem2.jsp";
				this.gotoPage(request, response, nextPage);
			} else if (action.equals("search")) {
				// 価格で検索する場合
				// リクエストパラメータを取得
				int price = Integer.parseInt(request.getParameter("price"));
				// モデルを使って検索結果の商品リストを取得
				ItemDAO dao = new ItemDAO();
				List<ItemBean> list = dao.findByPrice(price);
				// 商品一覧リストをスコープに登録
				request.setAttribute("items", list);
				// 遷移先URLを設定して画面遷移
				String nextPage = "/showItem2.jsp";
				this.gotoPage(request, response, nextPage);
			} else if (action.equals("delete")) {
				// 指定された商品番号の商品を削除する場合
				// リクエストパラメータを取得
				int code = Integer.parseInt(request.getParameter("code"));
				// モデルを使って取得した商品番号の商品を削除
				ItemDAO dao  = new ItemDAO();
				dao.delete(code);
				// 商品一覧リストを取得
				List<ItemBean> list = dao.findAll();
				// 商品一覧リストをスコープに登録
				request.setAttribute("items", list);
				// 遷移先URLを設定して画面遷移
				String nextPage = "/showItem2.jsp";
				this.gotoPage(request, response, nextPage);
			} else {
				request.setAttribute("message", "正しく操作してください。");
				this.gotoPage(request, response, "/errInternal.jsp");
			}
			
		} catch (DAOException e) {
			e.printStackTrace();
			// スコープにメッセージを登録
			request.setAttribute("message", "内部エラーが発生しました。");
			// エラー画面に遷移
			this.gotoPage(request, response, "/errInternal.jsp");
		}
	}

	/**
	 * 指定されたURLに遷移する
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param nextPage 遷移先URL
	 * @throws ServletException
	 * @throws IOException
	 */
	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String nextPage) throws ServletException, IOException {
		// 遷移先URLをもとに遷移実行オブジェクトを取得
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		// 指定されたURLに遷移
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
