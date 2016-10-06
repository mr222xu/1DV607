package model.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Boat;
import model.BoatType;
import model.Member;
import model.PersonalNumber;
import model.auth.AuthException;
import model.auth.Authorization;
import model.dao.DAOException;
import model.dao.MemberDAO;

/**
 * MemberDAO Implementation with XML database file persistence store
 * 
 * @author mr222xu
 *
 */
public class MemberDAOImpl implements MemberDAO {
	
	// Members
	private DocumentBuilderFactory documentBuilderfactory;
	private DocumentBuilder documentBuilder;
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	
	// Static members
	private final static String DATABASE = "database.xml";
	private final static String EMPTY_DATABASE_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>"
															+ "\n<members>"
															+ "\n</members>";

	/**
	 * Constructor
	 * 
	 * @throws DAOException - If member initialization fails
	 */
	public MemberDAOImpl() {
		try {
			documentBuilderfactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderfactory.newDocumentBuilder();
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
		} catch (ParserConfigurationException | TransformerConfigurationException e) {
			// This should ideally never happen
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Private method that creates the XML member database file if it
	 * doesn't exist
	 * 
	 * @throws IOException - If an I/O error occurs
	 */
	private void createXml() throws IOException {
		// Create file object
		File database = new File(DATABASE);
		
		// If the XML database file doesn't exist, create it
		if (!database.exists()) {
			FileWriter out = new FileWriter(database);
			out.write(EMPTY_DATABASE_CONTENT);
			out.close();
		}
	}
	
	/**
	 * Writes the XML member database to disk
	 * 
	 * @param document - The DOM node
	 * @throws TransformerException -  If an unrecoverable error occurs during transformation
	 */
	private void writeXml(Document document) throws TransformerException {
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(DATABASE));
		transformer.transform(domSource, streamResult);
	}

	@Override
	public void create(Member m, Authorization a) throws AuthException, DAOException {
		// Validate auth
		if (a == null || !a.isAuthorized())
			throw new AuthException("Must be authorized to create");
		
		// Get all members and see if m object is already persisted
		if (getMembers().contains(m))
			return;
		
		try {
			// Create XML database file if it doesn't exist
			createXml();
			
			// Parse the XML database file
			Document document = documentBuilder.parse(DATABASE);
			
			// Get the first child, i.e. members
			Node root = document.getFirstChild();
			
			// Create a new member element
			Element member = document.createElement("member");
			
			// Set ID and append to new member
			Element id = document.createElement("id");
			id.appendChild(document.createTextNode(Integer.toString(m.getId())));
			member.appendChild(id);
			
			// Set firstname and append to member
			Element firstname = document.createElement("firstname");
			firstname.appendChild(document.createTextNode(m.getFirstname()));
			member.appendChild(firstname);
			
			// Set lastname and append to member
			Element lastname = document.createElement("lastname");
			lastname.appendChild(document.createTextNode(m.getLastname()));
			member.appendChild(lastname);
			
			// Set personal number and append to member
			Element personalNumber = document.createElement("personalnumber");
			personalNumber.appendChild(document.createTextNode(m.getPersonalNumber().toString()));
			member.appendChild(personalNumber);
			
			// Create any boat elements
			Element boats = document.createElement("boats");
			
			// Iterate over all boats
			for (Boat b : m.getBoats()) {
				// Create boat
				Element boat = document.createElement("boat");
				
				// Set type
				Element type = document.createElement("type");
				type.appendChild(document.createTextNode(b.getType().toString()));
				boat.appendChild(type);
				
				// Set length
				Element length = document.createElement("length");
				length.appendChild(document.createTextNode(Integer.toString(b.getLength())));
				boat.appendChild(length);
				
				// Append boat to boats
				boats.appendChild(boat);
			}
			
			// Append boats to member
			member.appendChild(boats);
			
			// Append member to members
			root.appendChild(member);
			
			// Write to disk
			writeXml(document);
		} catch (SAXException | IOException | TransformerException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<Member> getMembers() throws DAOException {
		List<Member> members = new ArrayList<>();

		try {
			// Create XML database file if it doesn't exist
			createXml();
			
			// Parse the XML database file
			Document document = documentBuilder.parse(DATABASE);
			
			// Get all member nodes
			NodeList memberNodes = document.getElementsByTagName("member");

			// Iterate over member nodes
			for (int i = 0; i < memberNodes.getLength(); i++) {
				// Get all member attributes
				NodeList memberAttributes = memberNodes.item(i).getChildNodes();
				
				// Initialize member builder
				Member.Builder memberBuilder = new Member.Builder();
				
				// Create member from its XML attributes
				for (int j = 0; j < memberAttributes.getLength(); j++) {
					Node memberAttribute = memberAttributes.item(j);
					
					if ("id".equals(memberAttribute.getNodeName())) {
						memberBuilder.id(Integer.parseInt(memberAttribute.getTextContent()));
					} else if ("firstname".equals(memberAttribute.getNodeName())) {
						memberBuilder.firstname(memberAttribute.getTextContent());
					} else if ("lastname".equals(memberAttribute.getNodeName())) {
						memberBuilder.lastname(memberAttribute.getTextContent());
					} else if ("personalnumber".equals(memberAttribute.getNodeName())) {
						memberBuilder.personalNumber(new PersonalNumber(memberAttribute.getTextContent()));
					} else if ("boats".equals(memberAttribute.getNodeName())) {
						NodeList boatNodes = memberAttribute.getChildNodes();
						Set<Boat> boats = new HashSet<>();
						
						for (int m = 0; m < boatNodes.getLength(); m++) {
							NodeList boatAttributes = boatNodes.item(m).getChildNodes();
							Boat.Builder boatBuilder = new Boat.Builder();
							
							for (int n = 0; n < boatAttributes.getLength(); n++) {
								Node boatAttribute = boatAttributes.item(n);
								
								if ("type".equals(boatAttribute.getNodeName())) {
									boatBuilder.type(BoatType.valueOf(boatAttribute.getTextContent()));
								} else if("length".equals(boatAttribute.getNodeName())) {
									boatBuilder.length(Integer.parseInt(boatAttribute.getTextContent()));
								}
							}
							
							// Create boat
							boats.add(boatBuilder.build());
						}
						
						// Add boats to member
						memberBuilder.boats(boats);
					}
				}
				
				// Create member
				members.add(memberBuilder.build());
			}
		} catch (SAXException | IOException e) {
			throw new DAOException(e);
		}

		// Sort members on member ID
		Collections.sort(members);
		
		// Return an unmodifiable list
		return Collections.unmodifiableList(members);
	}

	@Override
	public void delete(Member m, Authorization a) throws AuthException, DAOException {
		// Validate auth
		if (a == null || !a.isAuthorized())
			throw new AuthException("Must be authorized to create");
		
		try {
			// Create XML database file if it doesn't exist
			createXml();
			
			// Parse the XML database file
			Document document = documentBuilder.parse(DATABASE);
			
			// Get the first child, i.e. members
			Node root = document.getFirstChild();
			
			// Look up member based on member ID
			NodeList idAttributes = document.getElementsByTagName("id");
			
			// Iterate over all member's IDs
			for (int i = 0; i < idAttributes.getLength(); i++)
				// If a match is found, delete the member (parent node of the member ID attribute)
				if (m.getId() == Integer.parseInt(idAttributes.item(i).getTextContent()))
					root.removeChild(idAttributes.item(i).getParentNode());

			// Write to disk
			writeXml(document);
		} catch (SAXException | IOException | TransformerException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(Member m, Authorization a) throws AuthException, DAOException {
		// Delete member
		delete(m, a);
		
		// Write new member info
		create(m, a);
	}

	@Override
	public int getNextId() throws DAOException {
		// Get all members
		List<Member> members = getMembers();
		
		// Get the last object, if there is one
		if (members.size() > 0)
			return members.get(members.size() - 1).getId() + 1;
		
		// If no members exist, 1 is returned
		return 1;
	}

}
