package kadai5.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kadai5.bean.ItemBean;

/**
 * itemテーブルにアクセスするDAOクラス
 */
public class ItemDAO {

	/**
	 * クラス定数：データベース接続情報文字列定数群
	 */
	private static final String DB_DRIVER    = "org.postgresql.Driver";
	private static final String DB_URL       = "jdbc:postgresql:sample";
	private static final String DB_USER      = "student";
	private static final String  DB_PASSWORD = "himitu";
	
	/**
	 * コンストラクタ
	 * @throws DAOException 
	 */
	public ItemDAO() throws DAOException {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("ドライバの登録に失敗しまし。た");
		}
	}


	public List<ItemBean> findAll() throws DAOException {
		String sql = "SELECT * FROM item";
		try (// データベース接続オブジェクトを取得：データベースに接続
			 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 // SQL実行オブジェクトを取得
			 PreparedStatement pstmt = con.prepareStatement(sql);
			 // SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();
			) {
			
			// 結果セットから商品リストへの詰替え
			List<ItemBean> list = new ArrayList<ItemBean>();
			while (rs.next()) {
				// １レコード分のフィールドを取得
				int code =  rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				// 取得したフィールドから商品クラスをインスタンス化
				ItemBean bean = new ItemBean(code, name, price);
				// 商品リストに追加
				list.add(bean);
			}
			// 商品リストを返却
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
		
	}
	
	
	
}
