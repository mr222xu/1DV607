package controller;

import java.util.List;

import model.Boat;
import model.BoatType;
import model.Member;
import model.PersonalNumber;
import model.dao.MemberDAO;
import view.Console;

public class Controller {

	private Console view;
	private MemberDAO memberDAO;
	
	public Controller(Console view, MemberDAO memberDAO) {
		this.view = view;
		this.memberDAO = memberDAO;
	}
	
	public boolean run() {
		view.showMenu();
		Member member;
		Boat boat;
		List<Member> list;
		List<Boat> boats;
		String fn;
		String ln;
		PersonalNumber pno;
		char c = view.getChar();
		
		switch (c) {
		// Create and save member
		case '1':
			fn = view.getFirstname();
			ln = view.getLastname();

			try {
				pno = new PersonalNumber(view.getPersonalNumber());
				member = new Member(fn, ln, pno);
				memberDAO.create(member);
			} catch (IllegalArgumentException e) {
				view.showError(e);
			}

			break;
		// List all members (compact)
		case '2':
			view.showMembersCompact(memberDAO.getMembers());
			break;
		// List all members (verbose)
		case '3':
			view.showMembersVerbose(memberDAO.getMembers());
			break;
		// Delete member
		case '4':
			list = memberDAO.getMembers();
			try {
				member = list.get(view.deleteMember(list));
				memberDAO.delete(member);
			} catch (Exception e) {
				view.showError(e);
			}
			break;
		case '5':
			list = memberDAO.getMembers();
			try {
				member = list.get(view.updateMember(list));
				fn = view.getFirstname();
				member.setFirstname(fn);
				ln = view.getLastname();
				member.setLastname(ln);
				pno = new PersonalNumber(view.getPersonalNumber());
				member.setPersonalNumber(pno);
				memberDAO.update(member);
			} catch (Exception e) {
				view.showError(e);
			}
			break;
		case '6':
			list = memberDAO.getMembers();
			try {
				member = list.get(view.createBoat(list));
				boat = new Boat(BoatType.valueOf(view.getBoatType()), 
									Integer.parseInt(view.getBoatLength()));
				member.addBoat(boat);
				memberDAO.update(member);
			} catch (Exception e) {
				view.showError(e);
			}
			break;
		case '7':
			list = memberDAO.getMembers();
			try {
				member = list.get(view.deleteBoat(list));
				boat = member.getBoats().get(view.deleteBoat(member));
				member.removeBoat(boat);

				memberDAO.update(member);
			} catch (Exception e) {
				view.showError(e);
			}
			break;
		case '8':
			list = memberDAO.getMembers();
			try {
				member = list.get(view.updateBoat(list));
				boats = member.getBoats();
				if (boats.size() > 0) {
					boat = boats.get(view.updateBoat(member));
					member.removeBoat(boat);
					boat = new Boat(BoatType.valueOf(view.getBoatType()), 
							Integer.parseInt(view.getBoatLength()));
					member.addBoat(boat);
					memberDAO.update(member);	
				}

			} catch (Exception e) {
				view.showError(e);
			}
			break;
		// Quit application
		case 'q':
			return false;
		default:
			view.showInvalidInput(Character.toString(c));
		}
		
		return true;
	}

	
}
