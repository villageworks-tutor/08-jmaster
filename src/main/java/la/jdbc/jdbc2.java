package la.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class jdbc2 {

	/**
	 * クラス定数：データベース接続情報文字列群
	 */
	private static final String DB_DRIVER   = "org.postgresql.Driver";
	private static final String DB_URL      = "jdbc:postgresql://localhost:5432/sample";
	private static final String DB_USER     = "student";
	private static final String DB_PASSWORD = "himitu";
	
	
	public static void main(String[] args) {
		// 実行するSQLの設定
		String sql = "SELECT * FROM emp WHERE name LIKE ?";
		
		// JDBCドライバをロード
		try (Scanner scanner = new Scanner(System.in);) {
			// ユーザからの入力値を取得
			System.out.print("名前を入力してください：");
			String name = scanner.next();
			
			Class.forName(DB_DRIVER);
			try (// データベース接続文字列の取得
				 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 // SQL実行オブジェクトの取得
				 PreparedStatement pstmt = con.prepareStatement(sql);
				) {
				// プレースホルダにデータをバインド
				pstmt.setString(1, "%" + name + "%");
				try (//SQLの実行と結果セットの取得
					 ResultSet rs = pstmt.executeQuery();
					){
					// 結果セットの表示
					while (rs.next()) {
						System.out.print(rs.getInt("code"));
						System.out.print("\t");
						System.out.print(rs.getString("name"));
						System.out.print("\t");
						System.out.print(rs.getInt("age"));
						System.out.print("\t");
						System.out.print(rs.getString("tel"));
						System.out.print("\n");
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
