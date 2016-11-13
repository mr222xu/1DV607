package model.search.impl;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.search.SearchOperator;
import model.search.SearchStrategy;

public class MonthSearchStrategy implements SearchStrategy {
	
	// Members
	private YearMonth month;
	
	/**
	 * Constructor
	 * 
	 * @param month - A birth month of member
	 */
	public MonthSearchStrategy(String month) {
		this.month = YearMonth.of(1970, Month.valueOf(month.toUpperCase()));
	}

	@Override
	public List<Member> search(List<Member> members, SearchOperator operator) {
		List<Member> matchingMembers = new ArrayList<>();
		Boolean match = false;
		
		for (Member m : members) {
			// The Java API doc didn't say how to compare Month Enum with Month Enum.
			// So using the YearMonth class instead.
			int compareTo = month.compareTo(YearMonth.of(1970, m.getPersonalNumber().getDate().getMonth()));
			
			switch (operator) {
			case EQUALS:
				match = compareTo == 0;
				break;
			case NOT_EQUALS:
				match = compareTo != 0;
				break;
			case SMALLER_THAN:
				match = compareTo > 0;
				break;
			case GREATER_THAN:
				match = compareTo < 0;
				break;
			case SMALLER_EQUALS:
				match = compareTo >= 0;
				break;
			case GREATER_EQUALS:
				match = compareTo <= 0;
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
