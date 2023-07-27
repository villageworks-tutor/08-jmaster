package la.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class jdbc3 {

	/**
	 * クラス定数：データベース接続情報文字列群
	 */
	private static final String DB_DRIVER   = "org.postgresql.Driver";
	private static final String DB_URL      = "jdbc:postgresql://localhost:5432/sample";
	private static final String DB_USER     = "student";
	private static final String DB_PASSWORD = "himitu";
	
	
	public static void main(String[] args) {
		// 実行するSQLの設定
		String sql = "INSERT INTO emp VALUES (?, ?, ?, ?)";
		
		// JDBCドライバをロード
		try (Scanner scanner = new Scanner(System.in);) {
			// ユーザからの入力値を取得
			System.out.print("コードを入力してください：");
			int code = scanner.nextInt();
			System.out.print("名前を入力してください：");
			String name = scanner.next();
			System.out.print("年齢を入力してください：");
			int age = scanner.nextInt();
			System.out.print("電話番号を入力してください：");
			String tel = scanner.next();
			
			Class.forName(DB_DRIVER);
			try (// データベース接続文字列の取得
				 Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 // SQL実行オブジェクトの取得
				 PreparedStatement pstmt = con.prepareStatement(sql);
				) {
				// プレースホルダにデータをバインド
				pstmt.setInt(1, code);
				pstmt.setString(2, name);
				pstmt.setInt(3, age);
				pstmt.setString(4, tel);
				
				// SQLを実行
				int row = pstmt.executeUpdate();
				
				// メッセージを表示
				System.out.println(row + "件、レコードを登録しました。");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
