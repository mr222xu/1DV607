package model.auth.impl;

import model.auth.AuthException;
import model.auth.Authorization;

public class AuthorizationImpl implements Authorization {
	
	// Member
	private boolean authorized = false;
	
	// Static members
	private static final String USERNAME = "secretary";
	private static final String PASSWORD = "1DV607";

	@Override
	public void authorize(String username, String password) throws AuthException {
		if (!(USERNAME.equals(username) && PASSWORD.equals(password))) {
			authorized = false;
			throw new AuthException("Login failed for user " + username);
		}
		
		// Authorized
		authorized = true;
	}

	@Override
	public boolean isAuthorized() {
		return authorized;
	}

}
