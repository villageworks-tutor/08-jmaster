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

	/**
	 * 全商品を取得する
	 * @return List<ItemBean> 全商品のリスト
	 * @throws DAOException
	 */
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

	/**
	 * 価格で並べ換える
	 * @param orderByAsc 昇順の場合はtrue、それ以外はfalse
	 * @return List<ItemBean> 並べ替えられた商品リスト
	 * @throws DAOException
	 */
	public List<ItemBean> sortByPrice(boolean orderByAsc) throws DAOException {
		// 実行するSQLを設定
		String sql = "SELECT * FROM item ORDER BY price";
		// 並べ替えが降順である場合：SQLの並べ替えモードを追加
		if (!orderByAsc) sql += " DESC";
		try (// データベースに接続
			 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 // SQL実行オブジェクトの取得
			 PreparedStatement pstmt = con.prepareStatement(sql);
			 // SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();
			) {
			//　結果セットから商品リストへの詰替え
			List<ItemBean> list = new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	/**
	 * 商品を追加する
	 * @param item 追加する商品
	 * @throws DAOException
	 */
	public void add(ItemBean item) throws DAOException {
		// 実行するSQLの設定
		String sql = "INSERT INTO item (name, price) VALUES (?, ?)";
		try (// データベースに接続
			 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 // SQL実行オブジェクトの取得
			 PreparedStatement pstmt = con.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setString(1, item.getName());
			pstmt.setInt(2, item.getPrice());
			// SQLの実行
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの追加に失敗しました。");
		}
	}

	/**
	 * 指定した価格以下の商品を取得する
	 * @param upperPrice 価格上限
	 * @return List<ItemBean> 価格上限以下の商品リスト 
	 * @throws DAOException
	 */
	public List<ItemBean> findByPrice(int upperPrice) throws DAOException {
		// 実行するSQLの設定
		String sql = "SELECT * FROM item WHERE price <= ? ORDER BY price";
		try (// データベースに接続
			 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 // SQL実行オブジェクトを取得
			 PreparedStatement pstmt = con.prepareStatement(sql);
			 ) {
			// プレースホルダにパラメータをバインド
			pstmt.setInt(1, upperPrice);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();
				 ) {
				// 結果セットから商品リストに詰替え
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				return list;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	
	
}
