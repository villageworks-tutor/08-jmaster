package la.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Jdbc8 {

	/**
	 * クラス定数：データベース接続情報文字列群
	 */
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql:sample";
	private static final String DB_USER = "student";
	private static final String DB_PASSWORD = "himitu";
	
	public static void main(String[] args) {
		
		// 実行するSQLの設定
		String sql = "SELECT * FROM emp ORDER BY age DESC LIMIT ?";
		
		try (Scanner scanner = new Scanner(System.in)) {
			// ユーザからの入力値を種痘
			System.out.print("上位の人数を入力してください：");
			int upperLimit = scanner.nextInt();
			
			// JDBCドライバのロード
			Class.forName(DB_DRIVER);
			try (// データベース接続オブジェクトの取得
				 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 // SQL実行オブジェクトの取得
				 PreparedStatement pstmt = con.prepareStatement(sql);
				) {
				// プレースホルダにデータをバインド
				pstmt.setInt(1, upperLimit);
			try (// SQLの実行と結果セットの取得
					 ResultSet rs = pstmt.executeQuery();
					) {
					// 結果セットの表示
					while(rs.next()) {
						System.out.print(rs.getInt("code"));
						System.out.print("\t");
						System.out.print(rs.getString("name"));
						System.out.print("\t");
						System.out.print(rs.getString("age"));
						System.out.print("\t");
						System.out.print(rs.getString("tel"));
						System.out.println();
					}
				}	
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
