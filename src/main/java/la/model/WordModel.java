package la.model;

import java.util.ArrayList;
import java.util.List;

import la.bean.WordBean;

public class WordModel {

	public static List<WordBean> searchByKeyword(String keyword, List<WordBean> dictionary) {
		List<WordBean> results = new ArrayList<WordBean>();
		for (WordBean word : dictionary) {
			if (word.getEnglish().contains(keyword) || word.getJapanese().contains(keyword)) {
				results.add(word);
			}
		}
		return results;
	}

}
