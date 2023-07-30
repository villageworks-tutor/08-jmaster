package kadai5.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ItemDAOTest {
	
	/** テスト対象クラス：system under test */
	TestItemDao sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new TestItemDao();
	}

	@Nested
	@DisplayName("ItemDAO#createSqlメソッドのテストクラス")
	class CreateSqlTest {
		@Test
		@DisplayName("【Test-04】下限値と上限値ともに-1の場合は「SELECT * FROM item ORDER BY code」である")
		void test04() {
			// setup
			int lowerPrice = -1;
			int uppperPrice = -1;
			String expected = "SELECT * FROM item ORDER BY code";
			// execute
			String actual = sut.generateCriteriaInPriceRange(lowerPrice, uppperPrice);
			// verify
			assertThat(actual, is(expected));
		}
		@Test
		@DisplayName("【Test-03】2000円以下の商品を取得するためのプレースホルダ付きSQLは「SELECT * FROM item WHERE price <= ?」である")
		void test03() {
			// setup
			int lowerPrice = -1;
			int uppperPrice = 2000;
			String expected = "SELECT * FROM item WHERE price <= ? ORDER BY code";
			// execute
			String actual = sut.generateCriteriaInPriceRange(lowerPrice, uppperPrice);
			// verify
			assertThat(actual, is(expected));
		}
		@Test
		@DisplayName("【Test-02】1000円以上の商品を取得するためのプレースホルダ付きSQLは「SELECT * FROM item WHERE price >= ?」」である")
		void test02() {
			// setup
			int lowerPrice = 1000;
			int uppperPrice = -1;
			String expected = "SELECT * FROM item WHERE price >= ? ORDER BY code";
			// execute
			String actual = sut.generateCriteriaInPriceRange(lowerPrice, uppperPrice);
			// verify
			assertThat(actual, is(expected));
		}
		@Test
		@DisplayName("【Test-01】1000円以上2000円以下の商品を取得するためのプレースホルダ付きSQLは「SELECT * FROM item WHERE price BETWEEN ? AND ?」である")
		void test01() {
			// setup
			int lowerPrice = 1000;
			int uppperPrice = 2000;
			String expected = "SELECT * FROM item WHERE price BETWEEN ? AND ? ORDER BY code";
			// execute
			String actual = sut.generateCriteriaInPriceRange(lowerPrice, uppperPrice);
			// verify
			assertThat(actual, is(expected));
		}
	}

}
