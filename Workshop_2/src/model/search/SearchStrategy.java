package model.search;

import java.util.List;

import model.Member;

public interface SearchStrategy {
	
	/**
	 * Will return each member that match the implemented search
	 * 
	 * @param members A list of members
	 * @param operator Which comparison operator to use
	 * @return A list of matching members
	 */
	public List<Member> search(List<Member> members, SearchOperator operator);

}
