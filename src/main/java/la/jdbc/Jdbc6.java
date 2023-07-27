package la.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Jdbc6 {

	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql:sample";
	private static final String DB_USER = "student";
	private static final String DB_PASSWORD = "himitu";
	
	public static void main(String[] args) {
		
		try (Scanner scanner = new Scanner(System.in)) {
			// ユーザからの入力値を種痘
			System.out.print("年齢の下限を入力してください：");
			int lowerAge = scanner.nextInt();
			System.out.print("年齢の上限を入力してください：");
			int upperAge = scanner.nextInt();
			
			// 実行するSQLの設定
			String sql = "SELECT * FROM emp WHERE age BETWEEN ? AND ?";
			
			// JDBCドライバのロード
			Class.forName(DB_DRIVER);
			try (// データベース接続オブジェクトの取得
				 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 // SQL実行オブジェクトの取得
				 PreparedStatement pstmt = con.prepareStatement(sql);
				) {
				// プレースホルダにデータっをバインド
				pstmt.setInt(1, lowerAge);
				pstmt.setInt(2, upperAge);
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
