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
	 * 指定範囲の価格の商品を取得する
	 * @param lowerPrice 指定範囲の価格の下限値（範囲に含む）
	 * @param upperPrice 指定範囲の価格の上限値（範囲に含む）
	 * @return List<ItemBean> 指定範囲の価格に含まれる商品がある場合は商品リスト、それ以外は空リスト
	 * @throws DAOException
	 */
	public List<ItemBean> findByPrice(int lowerPrice, int upperPrice) throws DAOException {
		// 引数の負数チェックによって設定するSQLを分岐
		String sql = this.generateCriteriaInPriceRange(lowerPrice, upperPrice);
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(sql);
			 ) {
			// プレースホルダにデータをバインド
			if (lowerPrice >= 0 && upperPrice >= 0) {
				pstmt.setInt(1, lowerPrice);
				pstmt.setInt(2, upperPrice);
			} else if (lowerPrice >= 0 && upperPrice < 0) {
				pstmt.setInt(1, lowerPrice);
			} else if (lowerPrice < 0 && upperPrice >= 0) {
				pstmt.setInt(1, upperPrice);
			}
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();
				 ) {
				// 結果セットから商品リストへの詰替え
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");;
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品リストを返却
				return list;
			}
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
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
	
	/**
	 * 下限値と上限値から価格の範囲検索を実行するSQL文字列を生成する
	 * @param lowerPrice 価格の下限値
	 * @param upperPrice 価格の上限値
	 * @return 価格の範囲検索を実行するSQL文字列
	 */
	protected String generateCriteriaInPriceRange(int lowerPrice, int upperPrice) {
		final String PREPARED_SQL_FIND_PRICE_BETWEEN  = SQL_FIND_ALL + "WHERE price BETWEEN ? AND ? " + PHRASE_ORDER_BY_CODE;
		final String PREPARED_SQL_FIND_PRICE_GE_LOWER = SQL_FIND_ALL + "WHERE price >= ? " + PHRASE_ORDER_BY_CODE;
		final String PREPARED_SQL_FIND_PRICE_LE_UPPER = SQL_FIND_ALL + "WHERE price <= ? " + PHRASE_ORDER_BY_CODE;
		
		if (lowerPrice >= 0 && upperPrice >= 0) {
			// 下限値上限値ともに正の整数が指定されている場合
			return PREPARED_SQL_FIND_PRICE_BETWEEN;
		} else if (lowerPrice >= 0 && upperPrice < 0) {
			// 下限値だけに正の整数が指定されている場合
			return PREPARED_SQL_FIND_PRICE_GE_LOWER;
		} else if (lowerPrice < 0 && upperPrice >= 0) {
			// 上限値だけに正の整数が指定されている場合
			return PREPARED_SQL_FIND_PRICE_LE_UPPER;
		} else {
			// 上記以外の場合
			return SQL_FIND_ALL_ORDER_BY_CODE;
		}
		
	}

	/**
	 * 商品名、価格によって商品を検索する
	 * @param name     商品名
	 * @param minPrice 価格の下限
	 * @param maxPrice 価格の上限
	 * @return List<ItemBean> 商品リスト
	 * @throws DAOException
	 */
	public List<ItemBean> findByNameAndPrice(String name, String minPrice, String maxPrice) throws DAOException {
		// 実行するSQLの設定：WHERE句の先頭の条件に恒真式「1=1」を記述しているのは、商品名、価格の条件式を統一して「AND」で連結することが目的
		String sql = SQL_FIND_ALL + "WHERE 1=1 ";
		if (!(name == null || name.isEmpty())) {
			sql += "AND name LIKE ? ";
		}
		if (!(minPrice == null || minPrice.isEmpty())) {
			sql += "AND price >= ? ";
		}
		if (!(maxPrice == null || maxPrice.isEmpty())) {
			sql += "AND price <= ?";
		}
		
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(sql);
			 ) {
			// プレースホルダにデータをバインド
			int count = 0; // プレースホルダのカウント
			if (!(name == null || name.isEmpty())) {
				count++;
				pstmt.setString(count, "%" + name + "%");
			}
			if (!(minPrice == null || minPrice.isEmpty())) {
				count++;
				pstmt.setInt(count, Integer.parseInt(minPrice));
			}
			if (!(maxPrice == null || maxPrice.isEmpty())) {
				count++;
				pstmt.setInt(count, Integer.parseInt(maxPrice));
			}
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから商品リストへの詰替え
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String itemName = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, itemName, price);
					list.add(bean);
				}
				return list;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(MESSAGE_FAIL_FIND_RECORD);
		}
		
	}
	
}
