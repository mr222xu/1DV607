package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.dao.MemberDAO;
import model.dao.impl.MemberDAOImpl;

public class Member implements Comparable<Member> {
	
	private String firstname;
	private String lastname;
	private PersonalNumber personalNumber;
	private Set<Boat> boats;
	private int id;
	private final static MemberDAO DAO = new MemberDAOImpl();

	public Member(String firstname, String lastname, PersonalNumber personalNumber) {
		setFirstname(firstname);
		setLastname(lastname);
		setPersonalNumber(personalNumber);
		setBoats(new HashSet<Boat>());
		setId(DAO.getNextId());
	}
	
	private Member(Builder builder) {
		setFirstname(builder.firstname);
		setLastname(builder.lastname);
		setPersonalNumber(builder.personalNumber);
		setBoats(builder.boats);
		setId(builder.id);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		if (firstname == null)
			throw new IllegalArgumentException("Member firstname cannot be null.");
			
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		if (lastname == null)
			throw new IllegalArgumentException("Member lastname cannot be null.");
		
		this.lastname = lastname;
	}
	
	public String getName() {
		return firstname + " " + lastname;
	}

	public PersonalNumber getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(PersonalNumber personalNumber) {
		if (personalNumber == null)
			throw new IllegalArgumentException("Member personal number cannot be null.");
		
		this.personalNumber = personalNumber;
	}
	
	public void addBoat(Boat boat) {
		boats.add(boat);
	}
	
	private void setBoats(Set<Boat> boats) {
		if (boats == null)
			boats = new HashSet<>();
		
		this.boats = boats;
	}
	
	public void removeBoat(Boat boat) {
		boats.remove(boat);
	}
	
	public List<Boat> getBoats() {
		List<Boat> list = new ArrayList<Boat>();
		list.addAll(boats);

		return Collections.unmodifiableList(list);
	}
	
	public int getNumberOfBoats() {
		return boats.size();
	}
	
	private void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("Member id cannot be less than 1.");
		
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public int compareTo(Member other) {
		return Integer.compare(id, other.id);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boats == null) ? 0 : boats.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((personalNumber == null) ? 0 : personalNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (boats == null) {
			if (other.boats != null)
				return false;
		} else if (!boats.equals(other.boats))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id != other.id)
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (personalNumber == null) {
			if (other.personalNumber != null)
				return false;
		} else if (!personalNumber.equals(other.personalNumber))
			return false;
		return true;
	}

	public static class Builder {
		private String firstname;
		private String lastname;
		private PersonalNumber personalNumber;
		private Set<Boat> boats;
		private int id;
		
		public Builder() {
			// Empty Builder constructor
		}
		
		public Builder firstname(String firstname) {
			this.firstname = firstname;
			return this;
		}
		
		public Builder lastname(String lastname) {
			this.lastname = lastname;
			return this;
		}
		
		public Builder personalNumber(PersonalNumber personalNumber) {
			this.personalNumber = personalNumber;
			return this;
		}
		
		public Builder boats(Set<Boat> boats) {
			this.boats = boats;
			return this;
		}
		
		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
		public Member build() {
			return new Member(this);
		}
	}
}
