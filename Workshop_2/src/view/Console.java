package view;

import static java.lang.System.err;
import static java.lang.System.out;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import model.Boat;
import model.BoatType;
import model.Member;

/**
 * View class
 * 
 * @author mr222xu
 *
 */
public class Console implements View {
	
	// Member
	Scanner in = new Scanner(System.in);
	
	@Override
	public Event getEvent() {
		out.println();
		out.println("########## The Happy Pirate Menu ##########");
		out.println();
		out.println("  0. Login");
		out.println("  1. Create member");
		out.println("  2. List all members (compact)");
		out.println("  3. List all members (verbose)");
		out.println("  4. Delete member");
		out.println("  5. Update member");
		out.println("  6. Check member");
		out.println("  7. Create boat");
		out.println("  8. Delete boat");
		out.println("  9. Update boat");
		out.println("  q. Quit");
		out.println();
		out.println("###########################################");
		out.println();
		
		return Event.getEnum(getInput(null));
	}
	
	/**
	 * Show invalid input message
	 * 
	 * @param invalid - The invalid input given
	 */
	public void showInvalidInput(String invalid) {
		err.println();
		err.print("Input " + invalid + " is not valid. Press any key to continue...");
		getInput(null);
		err.println();
	}
	
	/**
	 * Show exception error message
	 * 
	 * @param error - The exception object
	 */
	public void showError(Exception error) {
		err.println();
		err.print(error.getMessage() + " Press any key to continue...");
		getInput(null);
		err.println();
	}
	
	/**
	 * Get member index
	 * 
	 * @param members - A list of members
	 * @param text - A text to print
	 * @return - An index
	 */
	private int getMemberIndex(List<Member> members, String text) {
		int i = 1;
		for (Member member : members)
			out.println(new StringBuilder()
					.append(i++)
					.append(". ")
					.append(member.getName())
					.toString());
		
		return Integer.parseInt(getInput(text)) - 1;
	}
	
	/**
	 * Get boat index
	 * 
	 * @param member - The member to show boats of
	 * @param text - A text to print
	 * @return - An index
	 */
	public int getBoatIndex(Member member, String text) {
		int i = 1;
		for (Boat boat : member.getBoats())
			out.println(new StringBuilder()
					.append("\t\t")
					.append(i++)
					.append(". ")
					.append(boat)
					.toString());
		
		return Integer.parseInt(getInput(text)) - 1;
	}
	
	/**
	 * Delete a member menu
	 * 
	 * @param members - A list of members
	 * @return - Index of member to delete
	 */
	public int deleteMember(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to delete");
	}

	/**
	 * Update a member menu
	 * 
	 * @param members - A list of members
	 * @return - Index of member to update
	 */
	public int updateMember(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to update");
	}
	
	/**
	 * Check a member menu
	 * 
	 * @param members - A list of members
	 * @return - Index of member to update
	 */
	public int checkMember(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to look at");
	}
	
	/**
	 * Create a boat menu
	 * 
	 * @param members - A list of members
	 * @return - Index of member to register a boat for
	 */
	public int createBoat(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to create a boat for");
	}
	
	/**
	 * Delete a boat menu
	 * 
	 * @param members - A list of members
	 * @return - Index of member to delete a boat for
	 */
	public int deleteBoat(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to delete a boat for");
	}
	
	/**
	 * Delete a boat menu
	 * 
	 * @param member - A member to delete a boat for
	 * @return - Index of boat to delete
	 */
	public int deleteBoat(Member member) {
		return getBoatIndex(member, "Enter number of boat to delete");
	}

	/**
	 * Update a boat menu
	 * 
	 * @param members - A list of members
	 * @return - Index of member to update a boat for
	 */
	public int updateBoat(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to update a boat for");
	}
	
	/**
	 * Update a boat menu
	 * 
	 * @param member - A member to update a boat for
	 * @return - Index of boat to update
	 */
	public int updateBoat(Member member) {
		return getBoatIndex(member, "Enter number of boat to update");
	}
	
	/**
	 * Show compact list of members
	 * 
	 * @param members - List of members
	 */
	public void showMembersCompact(List<Member> members) {
		int i = 1;
		for (Member member : members)
			out.println(new StringBuilder()
					.append(i++)
					.append(". ")
					.append(member.getName())
					.append("\n\tMember ID: ")
					.append(member.getId())
					.append("\n\tNo. of boats: ")
					.append(member.getBoats().size())
					.toString());
	}
	
	/**
	 * Show verbose list of members
	 * 
	 * @param members - List of members
	 */
	public void showMembersVerbose(List<Member> members) {
		int i = 1;
		
		for (Member member : members) {
			out.println(new StringBuilder()
					.append(i++)
					.append(". ")
					.append(member.getName())
					.append("\n\tPersonal number: ")
					.append(member.getPersonalNumber())
					.append("\n\tMember ID: ")
					.append(member.getId())
					.append("\n\tBoats:")
					.toString());
			
			int j = 1;
			for (Boat boat : member.getBoats())
				out.println(new StringBuilder()
						.append("\t\t")
						.append(j++)
						.append(". ")
						.append(boat)
						.toString());
		}
	}
	
	/**
	 * Show verbose info of one member
	 * 
	 * @param member - A member
	 */
	public void showMember(Member member) {
		showMembersVerbose(Arrays.asList(member));
	}
	
	/**
	 * Get username
	 * 
	 * @return - username
	 */
	public String getUsername() {
		return getInput("Username");
	}
	
	/**
	 * Get password
	 * 
	 * @return - password
	 */
	public String getPassword() {
		return getInput("Password");
	}
	
	/**
	 * Get first name
	 * 
	 * @return - First name
	 */
	public String getFirstname() {
		return getInput("First name");
	}
	
	/**
	 * Get last name
	 * 
	 * @return - Last name
	 */
	public String getLastname() {
		return getInput("Last name");
	}
	
	/**
	 * Get personal number
	 * 
	 * @return - Personal number
	 */
	public String getPersonalNumber() {
		return getInput("Personal Number");
	}
	
	/**
	 * Get boat type
	 * 
	 * @return - Boat type
	 */
	public String getBoatType(BoatType[] types) {
		StringBuilder sb = new StringBuilder("Boat type (Enter full name: ");
		
		// Print all boat types
		for (BoatType type : types)
			sb.append(type.toString())
				.append(", ");
		
		sb.delete(sb.length() - 2, sb.length());
		sb.append(")");
		
		return getInput(sb.toString());
	}
	
	/**
	 * Get boat length
	 * 
	 * @return - Boat length
	 */
	public String getBoatLength() {
		return getInput("Boat length");
	}
	
	/**
	 * Get input from keyboard
	 * 
	 * @param text - A text to print to user
	 * @return - The user's input
	 */
	private String getInput(String text) {
		out.print((text != null ? text : "")  + "> ");
		
		return in.nextLine();
	}
}
