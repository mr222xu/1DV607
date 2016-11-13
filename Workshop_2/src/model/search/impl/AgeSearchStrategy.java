package model.search.impl;

import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.search.SearchOperator;
import model.search.SearchStrategy;

public class AgeSearchStrategy implements SearchStrategy {
	
	// Members
	private int age;
	
	/**
	 * Constructor
	 * 
	 * @param age - Exact age to match
	 */
	public AgeSearchStrategy(int age) {
		this.age = age;
	}

	@Override
	public List<Member> search(List<Member> members, SearchOperator operator) {
		List<Member> matchingMembers = new ArrayList<>();
		Boolean match = false;
		
		for (Member m : members) {
			switch (operator) {
			case EQUALS:
				match = m.getPersonalNumber().getAge() == age;
				break;
			case NOT_EQUALS:
				match = m.getPersonalNumber().getAge() != age;
				break;
			case SMALLER_THAN:
				match = m.getPersonalNumber().getAge() < age;
				break;
			case GREATER_THAN:
				match = m.getPersonalNumber().getAge() > age;
				break;
			case SMALLER_EQUALS:
				match = m.getPersonalNumber().getAge() <= age;
				break;
			case GREATER_EQUALS:
				match = m.getPersonalNumber().getAge() >= age;
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
