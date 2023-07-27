package la.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc7 {

	/**
	 * クラス定数：データベース接続情報文字列群
	 */
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql:sample";
	private static final String DB_USER = "student";
	private static final String DB_PASSWORD = "himitu";

	public static void main(String[] args) {

		// 実行するSQLの設定
		String sql = "SELECT min(age), max(age), avg(age) FROM emp";

		try {
			// JDBCドライバのロード
			Class.forName(DB_DRIVER);
			try (// データベース接続オブジェクトの取得
					Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					// SQL実行オブジェクトの取得
					PreparedStatement pstmt = con.prepareStatement(sql);
					// SQLの実行と結果セットの取得
					ResultSet rs = pstmt.executeQuery();) {
				// 結果セットの表示
				while (rs.next()) {
					System.out.println("年齢の最小は：" + rs.getInt(1) + "歳");
					System.out.println("年齢の最大は：" + rs.getInt(2) + "歳");
					System.out.println("年齢の平均は：" + rs.getDouble(3) + "歳");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
