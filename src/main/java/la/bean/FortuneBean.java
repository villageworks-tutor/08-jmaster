package la.bean;

/**
 * 占い結果を保持するためのJavaBeans
 */
public class FortuneBean {
	
	/**
	 * フィールド
	 */
	private int month;    // 誕生月
	private String item;  // ラッキーアイテム
	private String color; // ラッキーカラー
	private int rank;     // ランク
	
	/**
	 * デフォルトコンストラクタ
	 */
	public FortuneBean() {}

	/**
	 * コンストラクタ
	 * @param month 誕生月
	 * @param item  ラッキーアイテム
	 * @param color ラッキーカラー
	 * @param rank  ランク
	 */
	public FortuneBean(int month, String item, String color, int rank) {
		super();
		this.month = month;
		this.item = item;
		this.color = color;
		this.rank = rank;
	}

	/** アクセサメソッド群 */
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
