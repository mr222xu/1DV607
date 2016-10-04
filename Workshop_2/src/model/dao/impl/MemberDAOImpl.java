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
import model.dao.MemberDAO;

public class MemberDAOImpl implements MemberDAO {
	
	private DocumentBuilderFactory documentBuilderfactory;
	private DocumentBuilder documentBuilder;
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private final static String DATABASE = "database.xml";
	private final static String EMPTY_DATABASE_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>"
															+ "\n<members>"
															+ "\n</members>";

	public MemberDAOImpl() {
		try {
			documentBuilderfactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderfactory.newDocumentBuilder();
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
		} catch (ParserConfigurationException | TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createDatabase() throws IOException {
		File database = new File(DATABASE);
		
		if (!database.exists()) {
			FileWriter out = new FileWriter(database);
			out.write(EMPTY_DATABASE_CONTENT);
			out.close();
		}
	}
	
	private void writeDatabase(Document document) throws TransformerException {
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(DATABASE));
		transformer.transform(domSource, streamResult);
	}

	@Override
	public void create(Member m) {
		if (getMembers().contains(m))
			return;
		
		try {
			createDatabase();
			
			Document document = documentBuilder.parse(DATABASE);
			Node root = document.getFirstChild();
			Element member = document.createElement("member");
			
			Element id = document.createElement("id");
			id.appendChild(document.createTextNode(Integer.toString(m.getId())));
			member.appendChild(id);
			
			Element firstname = document.createElement("firstname");
			firstname.appendChild(document.createTextNode(m.getFirstname()));
			member.appendChild(firstname);
			
			Element lastname = document.createElement("lastname");
			lastname.appendChild(document.createTextNode(m.getLastname()));
			member.appendChild(lastname);
			
			Element personalNumber = document.createElement("personalnumber");
			personalNumber.appendChild(document.createTextNode(m.getPersonalNumber().toString()));
			member.appendChild(personalNumber);
			
			Element boats = document.createElement("boats");
			for (Boat b : m.getBoats()) {
				Element boat = document.createElement("boat");
				
				Element type = document.createElement("type");
				type.appendChild(document.createTextNode(b.getType().toString()));
				boat.appendChild(type);
				
				Element length = document.createElement("length");
				length.appendChild(document.createTextNode(Integer.toString(b.getLength())));
				boat.appendChild(length);
				
				boats.appendChild(boat);
			}
			
			member.appendChild(boats);
			root.appendChild(member);
			writeDatabase(document);
		} catch (SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Member> getMembers() {
		List<Member> members = new ArrayList<>();

		try {
			createDatabase();
			
			Document document = documentBuilder.parse(DATABASE);
			NodeList memberNodes = document.getElementsByTagName("member");

			// Iterate over members in XML
			for (int i = 0; i < memberNodes.getLength(); i++) {
				NodeList memberAttributes = memberNodes.item(i).getChildNodes();
				Member.Builder memberBuilder = new Member.Builder();
				
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(members);
		return Collections.unmodifiableList(members);
	}

	@Override
	public void delete(Member m) {
		try {
			createDatabase();
			
			Document document = documentBuilder.parse(DATABASE);
			Node root = document.getFirstChild();
			NodeList idAttributes = document.getElementsByTagName("id");
			
			for (int i = 0; i < idAttributes.getLength(); i++)
				if (m.getId() == Integer.parseInt(idAttributes.item(i).getTextContent()))
					root.removeChild(idAttributes.item(i).getParentNode());

			writeDatabase(document);
		} catch (SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Member m) {
		delete(m);
		create(m);
	}

	@Override
	public int getNextId() {
		List<Member> members = getMembers();
		int nextId = 1;
		
		if (members.size() > 0)
			nextId = members.get(members.size() - 1).getId() + 1;
		
		return nextId;
	}

}
