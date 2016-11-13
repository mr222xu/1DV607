package app;

import java.util.List;

import model.Member;
import model.dao.DAOException;
import model.dao.MemberDAO;
import model.dao.impl.MemberDAOImpl;
import model.search.ComplexSearchOperator;
import model.search.SearchOperator;
import model.search.impl.Search;
import view.Console;

/**
 * Main class
 * 
 * @author mr222xu
 *
 */
public class TheHappyPirateSearchExample {
	
	public static void main(String... args) {
		Console view = new Console();
		Search search = new Search();
		
		
		
		try {
			MemberDAO memberDAO = new MemberDAOImpl();
			List<Member> members = memberDAO.getMembers();
			
			
			System.out.println("Searching for members of age greater than 18");
			System.out.println("Found:");
			view.showMembersVerbose(search.age(18).search(members, SearchOperator.GREATER_THAN));
			
			System.out.println("Searching for members with a name matching regex ni*");
			System.out.println("Found:");
			view.showMembersVerbose(search.name("ni*").search(members, SearchOperator.EQUALS));
			
			System.out.println("Searching for members born in the month of January");
			System.out.println("Found:");
			view.showMembersVerbose(search.month("January").search(members, SearchOperator.EQUALS));
			
			System.out.println("Searching for members born in greater than the month of January");
			System.out.println("Found:");
			view.showMembersVerbose(search.month("January").search(members, SearchOperator.GREATER_THAN));
			
			System.out.println("Searching for members born in smaller than the month of January");
			System.out.println("Found:");
			view.showMembersVerbose(search.month("January").search(members, SearchOperator.SMALLER_THAN));
			
			
			System.out.println("Doing complex search (month==Jan || (name=\"Ann*\" && age > 18)");
			System.out.println("Found:");
			view.showMembersVerbose(
				search.andOr().search(
						search.month("January").search(members, SearchOperator.EQUALS),
						search.andOr().search(
								search.name("Ann*").search(members, SearchOperator.EQUALS),
								search.age(18).search(members, SearchOperator.GREATER_THAN), ComplexSearchOperator.AND),
						ComplexSearchOperator.OR)
			);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
