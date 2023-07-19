package la.bean;

/**
 * 単語情報を保持するJavaBean
 */
public class WordBean {
	
	/**
	 * フィールド
	 */
	private String japanese; // 日本語
	private String english;   // 英語
	
	/**
	 * デフォルトコンストラクタ
	 */
	public WordBean() {}

	/**
	 * コンストラクタ
	 * @param japanesee 日本語
	 * @param english   英語
	 */
	public WordBean(String japanese, String english) {
		this.japanese = japanese;
		this.english = english;
	}

	/** アクセサメソッド群 */
	
	public String getJapanese() {
		return japanese;
	}

	public void setJapanesee(String japanese) {
		this.japanese = japanese;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}
	
}
