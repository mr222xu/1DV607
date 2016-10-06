package model.dao;

import java.util.List;

import model.Member;
import model.auth.AuthException;
import model.auth.Authorization;

/**
 * Member data access object interface
 * 
 * @author mr222xu
 *
 */
public interface MemberDAO {
	
	/**
	 * Method to create a new member in the persistence store
	 * 
	 * @param m - The member object to create
	 * @param a - Authorization object
	 * @throws AuthException - If invoked when not authenticated
	 * @throws DAOException - If an error occurs while writing the object to the persistence store
	 */
	public void create(Member m, Authorization a) throws AuthException, DAOException;
	
	/**
	 * Method to delete an existing member in the persistence store
	 * 
	 * @param m - The member object to delete
	 * @param a - Authorization object
	 * @throws AuthException - If invoked when not authenticated
	 * @throws DAOException
	 */
	public void delete(Member m, Authorization a) throws AuthException, DAOException;
	
	/**
	 * Method to update an existing member in the persistence store
	 * 
	 * @param m - The member object to update
	 * @param a - Authorization object
	 * @throws AuthException - If invoked when not authenticated
	 * @throws DAOException - If an error occurs while updating the object in the persistence store
	 */
	public void update(Member m, Authorization a) throws AuthException, DAOException;
	
	/**
	 * Method to get all members in the persistence store
	 * 
	 * @return - A list with all members
	 * @throws DAOException - If an error occurs while retrieving the objects from the persistence store
	 */
	public List<Member> getMembers() throws DAOException;
	
	/**
	 * Method that will return the next available member ID
	 * 
	 * @return - Next available member ID
	 * @throws DAOException - If an error occurs while looking up next available ID from the persistence store
	 */
	public int getNextId() throws DAOException;
}
