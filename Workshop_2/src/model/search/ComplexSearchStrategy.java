package model.search;

import java.util.List;

import model.Member;

public interface ComplexSearchStrategy {
	
	/**
	 * Will return each member that match the implemented search method
	 * 
	 * @param members1 A list of members
	 * @param members2 Another list of members
	 * @param operator Which comparison operator to use
	 * @return A list of matching members
	 */
	public List<Member> search(List<Member> members1,  List<Member> members2, ComplexSearchOperator operator);

}
