package model.auth;

/**
 * Exception class for authorization errors
 * 
 * @author mr222xu
 *
 */
public class AuthException extends Exception {

	// Generated by Eclipse
	private static final long serialVersionUID = 3225486855578971554L;

	/**
	 * Constructor
	 * 
	 * @param message - An error message
	 */
	public AuthException(String message) {
		super(message);
	}
}
