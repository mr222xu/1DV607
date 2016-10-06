package app;

import controller.Controller;
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
		Controller c = new Controller(new Console(), new MemberDAOImpl());
		while(c.run());
	}
}
