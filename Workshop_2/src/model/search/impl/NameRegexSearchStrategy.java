package model.search.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Member;
import model.search.SearchOperator;
import model.search.SearchStrategy;

public class NameRegexSearchStrategy implements SearchStrategy {
	
	// Members
	private Pattern pattern;
	
	/**
	 * Constructor
	 * 
	 * @param regex - A regular expression to match member name
	 */
	public NameRegexSearchStrategy(String regex) {
		pattern = Pattern.compile(regex);
	}

	@Override
	public List<Member> search(List<Member> members, SearchOperator operator) {
		List<Member> matchingMembers = new ArrayList<>();
		Boolean match = false;
		
		for (Member m : members) {
			// Get matcher for specific member name
			Matcher matcher = pattern.matcher(m.getName());
			
			switch (operator) {
			case EQUALS:
				match = matcher.find();
				break;
			case NOT_EQUALS:
				match = !matcher.find();
				break;
			default:
				throw new IllegalArgumentException(this.getClass().getName() + " does not accept operator " + operator);
			}
			
			if (match)
				matchingMembers.add(m);
		}

		return matchingMembers;
	}

}
