package kadai5.dao;

/**
 * すべてのDAOクラスで発生する例外を統一的に扱うための独自例外
 */
public class DAOException extends Exception {

	public DAOException(String message) {
		super(message);
	}

}
