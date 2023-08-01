package kadai5add.bean;

import java.io.Serializable;

/**
 * empテーブルの1レコード分の情報を管理するJavaBean
 */
public class EmpBean implements Serializable {

	/**
	 * フィールド
	 */
	private int code;    // 従業員番号
	private String name; // 従業員名
	private int age;     // 年齢
	private String tel;  // 電話番号
	
	/**
	 * デフォルトコンストラクタ
	 */
	public EmpBean() {}

	/**
	 * コンストラクタ
	 * @param code 従業員番号
	 * @param name 従業員名
	 * @param age  年齢
	 * @param tel  電話番号
	 */
	public EmpBean(int code, String name, int age, String tel) {
		super();
		this.code = code;
		this.name = name;
		this.age = age;
		this.tel = tel;
	}

	/** アクセサメソッド群 */
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmpBean [");
		builder.append("code=" + code + ", ");
		builder.append("name=" + name + ", ");
		builder.append("age=" + age + ", ");
		builder.append("tel=" + tel + "]");
		return builder.toString();
	}
	
}
