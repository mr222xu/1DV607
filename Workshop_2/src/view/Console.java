package view;

import static java.lang.System.err;
import static java.lang.System.out;

import java.util.List;
import java.util.Scanner;

import model.Boat;
import model.Member;

public class Console {
	
	Scanner in = new Scanner(System.in);
	
	public void showMenu() {
		out.println();
		out.println("########## The Happy Pirate Menu ##########");
		out.println();
		out.println("  1. Create member");
		out.println("  2. List all members (compact)");
		out.println("  3. List all members (verbose)");
		out.println("  4. Delete member");
		out.println("  5. Update member");
		out.println("  6. Create boat");
		out.println("  7. Delete boat");
		out.println("  8. Update boat");
		out.println("  q. Quit");
		out.println();
		out.println("###########################################");
		out.println();
	}
	
	public void showInvalidInput(String invalid) {
		err.println();
		err.print("Input " + invalid + " is not valid. Press any key to continue...");
		getInput(null);
		err.println();
	}
	
	public void showError(Exception error) {
		err.println();
		err.print(error.getMessage() + " Press any key to continue...");
		getInput(null);
		err.println();
	}
	
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
	
	private int getBoatIndex(Member member, String text) {
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
	
	public int deleteMember(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to delete");
	}
	
	public int updateMember(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to update");
	}
	
	public int createBoat(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to create a boat for");
	}
	
	public int deleteBoat(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to delete a boat for");
	}
	
	public int deleteBoat(Member member) {
		return getBoatIndex(member, "Enter number of boat to delete");
	}
	
	public int updateBoat(List<Member> members) {
		return getMemberIndex(members, "Enter number of member to update a boat for");
	}
	
	public int updateBoat(Member member) {
		return getBoatIndex(member, "Enter number of boat to update");
	}
	
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
	
	public void showMembersVerbose(List<Member> members) {
		int i = 1;
		int j = 1;
		
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
			
			for (Boat boat : member.getBoats())
				out.println(new StringBuilder()
						.append("\t\t")
						.append(j++)
						.append(". ")
						.append(boat)
						.toString());
		}
	}
	
	public String getFirstname() {
		return getInput("First name");
	}
	
	public String getBoatType() {
		return getInput("Boat type");
	}
	
	public String getBoatLength() {
		return getInput("Boat length");
	}
	
	public String getLastname() {
		return getInput("Last name");
	}
	
	public String getPersonalNumber() {
		return getInput("Personal Number");
	}
	
	private String getInput(String text) {
		out.print((text != null ? text : "")  + "> ");
		
		return in.nextLine();
	}
	
	public char getChar() {
		return getInput(null).charAt(0);
	}
}
