package controller;

import java.util.List;

import model.Boat;
import model.BoatType;
import model.Member;
import model.PersonalNumber;
import model.auth.Authorization;
import model.auth.impl.AuthorizationImpl;
import model.dao.MemberDAO;
import view.Console;

/**
 * Controller class
 * 
 * @author mr222xu
 *
 */
public class Controller {

	// Members
	private Console view;
	private MemberDAO memberDAO;
	private Authorization authorization;
	
	/**
	 * Constructor
	 * 
	 * @param view - The view
	 * @param memberDAO - The model
	 */
	public Controller(Console view, MemberDAO memberDAO) {
		this.view = view;
		this.memberDAO = memberDAO;
	}
	
	/**
	 * Start the program
	 * 
	 * @return - True if continue running, false if exit
	 */
	public boolean run() {
		try {
			// Get input from user
			switch (view.getEvent()) {
			case LOGIN:
				login();
				break;
			case CREATE_MEMBER:
				createMember();
				break;
			case LIST_MEMBERS_COMPACT:
				view.showMembersCompact(memberDAO.getMembers());
				break;
			case LIST_MEMBERS_VERBOSE:
				view.showMembersVerbose(memberDAO.getMembers());
				break;
			case DELETE_MEMBER:
				deleteMember();
				break;
			case UPDATE_MEMBER:
				updateMember();
				break;
			case CREATE_BOAT:
				createBoat();
				break;
			case DELETE_BOAT:
				deleteBoat();
				break;
			case UPDATE_BOAT:
				updateBoat();
				break;
			case QUIT:
				return false;
			default:
				// Invalid input
			}
		} catch (Exception e) {
			view.showError(e);
		}
		
		return true;
	}
	
	public void login() {
		try {
			authorization = new AuthorizationImpl();
			authorization.authorize(view.getUsername(), view.getPassword());
		} catch (Exception e) {
			view.showError(e);
		}
	}
	
	/**
	 * Create a member
	 */
	private void createMember() {
		try {
			Member member = new Member(view.getFirstname(), 
					view.getLastname(), new PersonalNumber(view.getPersonalNumber()));
			memberDAO.create(member, authorization);
		} catch (Exception e) {
			view.showError(e);
		}
	}
	
	/**
	 * Delete a member
	 */
	private void deleteMember() {
		try {
			List<Member> members = memberDAO.getMembers();
			Member member = members.get(view.deleteMember(members));
			memberDAO.delete(member, authorization);
		} catch (Exception e) {
			view.showError(e);
		}
	}
	
	/**
	 * Update a member
	 */
	private void updateMember() {
		try {
			List<Member> members = memberDAO.getMembers();
			Member member = members.get(view.updateMember(members));
			member.setFirstname(view.getFirstname());
			member.setLastname(view.getLastname());
			member.setPersonalNumber(new PersonalNumber(view.getPersonalNumber()));
			memberDAO.update(member, authorization);
		} catch (Exception e) {
			view.showError(e);
		}
	}
	
	/**
	 * Create a boat
	 */
	private void createBoat() {
		try {
			List<Member> members = memberDAO.getMembers();
			Member member = members.get(view.createBoat(members));
			Boat boat = new Boat(BoatType.valueOf(view.getBoatType(BoatType.values())), 
								Integer.parseInt(view.getBoatLength()));
			member.addBoat(boat);
			memberDAO.update(member, authorization);
		} catch (Exception e) {
			view.showError(e);
		}
	}
	
	/**
	 * Delete a boat
	 */
	private void deleteBoat() {
		try {
			List<Member> members = memberDAO.getMembers();
			Member member = members.get(view.deleteBoat(members));
			List<Boat> boats = member.getBoats();
			
			if (boats.size() > 0) {
				Boat boat = member.getBoats().get(view.deleteBoat(member));
				member.removeBoat(boat);
				memberDAO.update(member, authorization);
			}
		} catch (Exception e) {
			view.showError(e);
		}
	}
	
	/**
	 * Update a boat
	 */
	private void updateBoat() {
		try {
			List<Member> members = memberDAO.getMembers();
			Member member = members.get(view.updateBoat(members));
			List<Boat> boats = member.getBoats();
			
			if (boats.size() > 0) {
				Boat boat = boats.get(view.updateBoat(member));
				member.removeBoat(boat);
				boat = new Boat(BoatType.valueOf(view.getBoatType(BoatType.values())), 
						Integer.parseInt(view.getBoatLength()));
				member.addBoat(boat);
				memberDAO.update(member, authorization);	
			}
		} catch (Exception e) {
			view.showError(e);
		}
	}

	
}
