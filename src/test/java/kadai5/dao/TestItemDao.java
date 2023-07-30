package kadai5.dao;

public class TestItemDao extends ItemDAO {

	public TestItemDao() throws DAOException {
		super();
	}
	
	public String generateCriteriaInPriceRange(int lowerPrice, int upperPrice) {
		return super.generateCriteriaInPriceRange(lowerPrice, upperPrice);
	}

}
