package model.search.impl;

import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.search.ComplexSearchOperator;
import model.search.ComplexSearchStrategy;

public class AndOrComplexSearchStrategy implements ComplexSearchStrategy {

	@Override
	public List<Member> search(List<Member> members1, List<Member> members2, ComplexSearchOperator operator) {
		List<Member> matchingMembers = new ArrayList<>();
		Boolean match = false;
		
		if (operator == ComplexSearchOperator.OR) {
			// Union of members1 and members2
			matchingMembers.addAll(members1);
			matchingMembers.removeAll(members2);
			matchingMembers.addAll(members2);
		} else {
			for (Member m : members1) {
				switch (operator) {
				case AND:
					match = members2.contains(m);
					break;
				default:
					throw new IllegalArgumentException(this.getClass().getName() + " does not accept operator " + operator);
				}
				
				if (match)
					matchingMembers.add(m);
			}
		}

		return matchingMembers;
	}

}
