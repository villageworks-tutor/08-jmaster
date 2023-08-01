package kadai5add.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kadai5.dao.DAOException;
import kadai5add.bean.EmpBean;

/**
 * empテーブルにアクセスするDAO
 */
public class EmpDAO {
	
	/**
	 * クラス定数：データベース接続情報文字列
	 */
	private static final String DB_DRIVER   = "org.postgresql.Driver";
	private static final String DB_URL      = "jdbc:postgresql:sample";
	private static final String DB_USER     = "student";
	private static final String DB_PASSWORD = "himitu";
	
	/**
	 * フィールド：データベース接続オブジェクト
	 */
	private Connection conn;

	/**
	 * コンストラクタ
	 * @throws DAOException 
	 */
	public EmpDAO() throws DAOException {
		this.getConnection();
	}

	/**
	 * データベースに接続する：データベース接続オブジェクトを取得する
	 * @throws DAOException
	 */
	private void getConnection() throws DAOException {
		try {
			Class.forName(DB_DRIVER);
			this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの読込みに失敗しました。");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベースの接続に失敗しました。");
		}
		
	}

	public List<EmpBean> findByAge(int lowerAge, int upperAge) throws DAOException {
		// 実行するSQLを設定
		String sql = "SELECT * FROM emp WHERE age BETWEEN ? AND ?";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, lowerAge);
			pstmt.setInt(2, upperAge);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから従業員リストに詰替え
				List<EmpBean> list = new ArrayList<EmpBean>();
				while (rs.next()) {
					EmpBean bean = new EmpBean();
					bean.setCode(rs.getInt("code"));
					bean.setName(rs.getString("name"));
					bean.setAge(rs.getInt("age"));
					bean.setTel(rs.getString("tel"));
					list.add(bean);
				}
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");
		}
		
	}

	public List<EmpBean> findByOrderByAgeDESC(int count) throws DAOException {
		// 実行するSQLの設定
		String sql = "SELECT * FROM emp ORDER BY age DESC LIMIT ?";
		try (// SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(sql);) {
			// プレースホルダにデータをバインド
			pstmt.setInt(1, count);
			try (// SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 結果セットから従業員リストへの詰替え
				List<EmpBean> list  = new ArrayList<EmpBean>();
				while (rs.next()) {
					EmpBean bean = new EmpBean();
					bean.setCode(rs.getInt("code"));
					bean.setName(rs.getString("name"));
					bean.setAge(rs.getInt("age"));
					bean.setTel(rs.getString("tel"));
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
