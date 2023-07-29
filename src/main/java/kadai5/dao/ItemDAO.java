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
	 * クラス定数
	 */
	// データベース接続情報文字列定数群
	private static final String DB_DRIVER   = "org.postgresql.Driver";
	private static final String DB_URL      = "jdbc:postgresql:sample";
	private static final String DB_USER     = "student";
	private static final String DB_PASSWORD = "himitu";
	
	// SQL文字列定数群
	private static final String PHRASE_ORDER_BY_CODE = "ORDER BY code";
	private static final String PHRASE_ORDER_BY_PRICE = "ORDER BY price";
	private static final String PHRASE_ORDER_Z2A = " DESC";
	
	private static final String SQL_FIND_ALL = "SELECT * FROM item ";
	private static final String SQL_FIND_ALL_ORDER_BY_CODE = SQL_FIND_ALL + PHRASE_ORDER_BY_CODE;
	private static final String SQL_FIND_BY_PRIMARYKEY = SQL_FIND_ALL + "WHERE code = ?";
	private static final String SQL_FIND_BY_PRICE = SQL_FIND_ALL + "WHERE price <= ? " + PHRASE_ORDER_BY_PRICE;
	private static final String SQL_SORT_BY_PRICE = SQL_FIND_ALL + PHRASE_ORDER_BY_PRICE;
	
	private static final String SQL_INSERT_ITEM = "INSERT INTO item (name, price) VALUES (?, ?)"; 
	private static final String SQL_UPDATE_ITEM = "UPDATE item SET name = ?, price = ? WHERE code = ?";
	private static final String SQL_DELETE_ITEM = "DELETE FROM item WHERE code = ?";
	
	// メッセージ文字列定数群
	private static final String MESSAGE_FAIL_LOAD_DRIVER = "ドライバの登録に失敗しました。";
	private static final String MESSAGE_FAIL_CONNECT_DB = "データベースへの接続に失敗しました。";
	private static final String MESSAGE_FAIL_FIND_RECORD = "レコードの取得に失敗しました。";
	private static final String MESSAGE_FAIL_ADD_RECORD = "レコードの追加に失敗しました。";
	private static final String MESSAGE_FAIL_UPDATE_RECORD = "レコードの更新に失敗しました。";
	private static final String MESSAGE_FAIL_DELETE_RECORD = "レコードの削除に失敗しました。";
	
	
	
	/**
	 * フィールド：データベース接続オブジェクト
	 */
	private Connection conn;
	
	/**
	 * コンストラクタ
	 * @throws DAOException 
	 */
	public ItemDAO() throws DAOException {
		try {
			Class.forName(DB_DRIVER);
			this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException(MESSAGE_FAIL_LOAD_DRIVER);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(MESSAGE_FAIL_CONNECT_DB);
		}
	}

	/**
	 * 全商品を取得する
	 * @return List<ItemBean> 全商品のリスト
	 * @throws DAOException
	 */
	public List<ItemBean> findAll() throws DAOException {
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_ALL_ORDER_BY_CODE);
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
			throw new DAOException(MESSAGE_FAIL_FIND_RECORD);
		}
		
	}

	/**
	 * 指定した商品番号の商品を取得する
	 * @param code 取得対象の商品の商品番号
	 * @return ItemBean | null 商品番号に対応した商品がある場合はItemBeanのいインスタンス、それ以外はnull
	 * @throws DAOException
	 */
	public ItemBean findByPrimaryKey(int code) throws DAOException {
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_PRIMARYKEY);
			) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, code);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットからインスタンスを生成
				ItemBean bean = null;
				if (rs.next()) {
					String name =  rs.getString("name");
					int price = rs.getInt("price");
					bean = new ItemBean(code, name, price);
				}
				return bean;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(MESSAGE_FAIL_FIND_RECORD);
		}
		
	}

	/**
	 * 指定した価格以下の商品を取得する
	 * @param upperPrice 価格上限
	 * @return List<ItemBean> 価格上限以下の商品リスト 
	 * @throws DAOException
	 */
	public List<ItemBean> findByPrice(int upperPrice) throws DAOException {
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_PRICE);
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
			throw new DAOException(MESSAGE_FAIL_FIND_RECORD);
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
		String sql = SQL_SORT_BY_PRICE;
		// 並べ替えが降順である場合：SQLの並べ替えモードを追加
		if (!orderByAsc) sql += PHRASE_ORDER_Z2A;
		try (// SQL実行オブジェクトの取得
			 PreparedStatement pstmt = this.conn.prepareStatement(sql);
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
			throw new DAOException(MESSAGE_FAIL_FIND_RECORD);
		}
		
	}

	/**
	 * 商品を追加する
	 * @param item 追加する商品
	 * @throws DAOException
	 */
	public void add(ItemBean item) throws DAOException {
		try (// SQL実行オブジェクトの取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_INSERT_ITEM);) {
			// プレースホルダにデータをバインド
			pstmt.setString(1, item.getName());
			pstmt.setInt(2, item.getPrice());
			// SQLの実行
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(MESSAGE_FAIL_ADD_RECORD);
		}
		
	}

	/**
	 * 指定された商品を更新する
	 * @param item 更新する商品
	 * @throws DAOException
	 */
	public void update(ItemBean item) throws DAOException {
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt =this.conn.prepareStatement(SQL_UPDATE_ITEM);
			) {
			// プレースホルダにデータをバインド
			pstmt.setString(1, item.getName());
			pstmt.setInt(2, item.getPrice());
			pstmt.setInt(3, item.getCode());
			// SQLを実行
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(MESSAGE_FAIL_UPDATE_RECORD);
		}
		
	}
	
	/**
	 * 指定された商品番号の商品を削除する
	 * @param code 削除対象商品の商品番号
	 * @throws DAOException
	 */
	public void delete(int code) throws DAOException {
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_DELETE_ITEM);
			 ) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, code);
			// SQLの実行
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(MESSAGE_FAIL_DELETE_RECORD);
		}
		
	}
	
}
