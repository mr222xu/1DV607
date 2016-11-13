package model.search.impl;

import model.search.ComplexSearchStrategy;
import model.search.SearchStrategy;

public class Search {
	
	public SearchStrategy age(int age) {
		return new AgeSearchStrategy(age);
	}
	
	public SearchStrategy name(String regex) {
		return new NameRegexSearchStrategy(regex);
	}
	
	public SearchStrategy month(String month) {
		return new MonthSearchStrategy(month);
	}
	
	public ComplexSearchStrategy andOr() {
		return new AndOrComplexSearchStrategy();
	}
}
