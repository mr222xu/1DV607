package model.auth;

/**
 * Authorization interface
 * 
 * @author mr222xu
 *
 */
public interface Authorization {
	
	/**
	 * Authorize a user
	 * 
	 * @param username - The user
	 * @param password - The user's password
	 * @throws AuthException - If authentication fails
	 */
	public void authorize(String username, String password) throws AuthException;
	
	/**
	 * Check if user is authorized
	 * 
	 * @return - True if authorized, false otherwise
	 */
	public boolean isAuthorized();
}
