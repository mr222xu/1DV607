package app;

import controller.Controller;
import model.auth.impl.AuthorizationImpl;
import model.dao.impl.MemberDAOImpl;
import view.Console;

/**
 * Main class
 * 
 * @author mr222xu
 *
 */
public class TheHappyPirate {
	
	public static void main(String... args) {
		Controller c = new Controller(new Console(), new MemberDAOImpl(), new AuthorizationImpl());
		while(c.run());
	}
}
