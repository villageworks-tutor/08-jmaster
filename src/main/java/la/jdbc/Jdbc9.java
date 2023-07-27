package la.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc9 {

	/**
	 * クラス定数：データベース接続情報文字列群
	 */
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql:sample";
	private static final String DB_USER = "student";
	private static final String DB_PASSWORD = "himitu";

	public static void main(String[] args) {

		// 実行するSQLの設定
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT");
		builder.append(" CASE");
		builder.append("  WHEN 20 <= age AND age < 30 THEN '20代'");
		builder.append("  WHEN 30 <= age AND age < 40 THEN '30代'");
		builder.append("  WHEN 40 <= age AND age < 50 THEN '40代'");
		builder.append("  WHEN 50 <= age AND age < 60 THEN '50代'");
		builder.append(" END AS range, ");
		builder.append(" count(*)");
		builder.append(" FROM emp");
		builder.append(" GROUP BY range");
		builder.append(" ORDER BY range");
		String sql = builder.toString();

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
					System.out.print(rs.getString("range") + "：");
					System.out.print(rs.getInt("count") + "人");
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
