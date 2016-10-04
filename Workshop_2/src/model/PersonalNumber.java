package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class PersonalNumber {

	// Members
	private LocalDate date;
	private String lastFour;
	
	// Static members
	// See https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html for Java regular expressions
	private final static Pattern PERS_NUM_PATTERN = Pattern.compile("^\\d{6}-\\d{4}$");
	private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");
	
	/**
	 * Constructor
	 * 
	 * @param personalNumber a personal number as a String object in format YYMMDD-XXXX
	 * @throws IllegalArgumentException if argument doesn't adhere to format YYMMDD-XXXX
	 */
	public PersonalNumber(String personalNumber) {
		setPersonalNumber(personalNumber);
	}

	/**
	 * Set personal number
	 * 
	 * @param personalNumber a personal number as a String object in format YYMMDD-XXXX
	 * @throws IllegalArgumentException if argument doesn't adhere to format YYMMDD-XXXX
	 */
	public void setPersonalNumber(String personalNumber) {	    
	    // Validate format
	    if (!PERS_NUM_PATTERN.matcher(personalNumber).matches())
	    	throw new IllegalArgumentException("Personal number is not on format YYMMDD-XXXX.");
	    
		// Split the string
	    String[] split = personalNumber.split("-");
	    
	    // Parse last four
	    lastFour = split[1];
		
	    // Parse date
		try {
		    date = LocalDate.parse(split[0], DATE_FORMAT);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Date of personal number is not valid.");
		}
		
		// Check if future
		if (date.isAfter(LocalDate.now()))
			throw new IllegalArgumentException("Date of personal number is not valid.");
	}
	
	/**
	 * Get date in personal number
	 * 
	 * @return a LocalDate object
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Get last four digits in personal number
	 * 
	 * @return a String object
	 */
	public String getLastFourDigits() {
		return lastFour;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((lastFour == null) ? 0 : lastFour.hashCode());
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
		PersonalNumber other = (PersonalNumber) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (lastFour == null) {
			if (other.lastFour != null)
				return false;
		} else if (!lastFour.equals(other.lastFour))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder(date.format(DATE_FORMAT))
					.append("-")
					.append(lastFour)
					.toString();
	}
}
