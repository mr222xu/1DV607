package model;

/**
 * Boat class
 * 
 * @author mr222xu
 *
 */
public class Boat {
	
	// Members
	private BoatType type;
	private int length;
	
	/**
	 * Constructor
	 * 
	 * @param type - Boat type
	 * @param length - Boat length in feet
	 */
	public Boat(BoatType type, int length) {
		setType(type);
		setLength(length);
	}

	/**
	 * To get the type of the boat
	 * 
	 * @return - BoatType object
	 */
	public BoatType getType() {
		return type;
	}

	/**
	 * To set the type of the boat
	 * 
	 * @param type - A BoatType object
	 */
	public void setType(BoatType type) {
		if (type == null)
			throw new IllegalArgumentException("Boat type cannot be null.");
		
		this.type = type;
	}

	/**
	 * To get the length of the boat
	 * 
	 * @return - Boat's length in feet
	 */
	public int getLength() {		
		return length;
	}

	/**
	 * To set the length of the boat
	 * 
	 * @param length - Boat's length in feet
	 */
	public void setLength(int length) {
		if (length < 1)
			throw new IllegalArgumentException("Boat length cannot be less than 1 foot.");
		
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Boat other = (Boat) obj;
		if (length != other.length)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Type: ")
				.append(type.toString())
				.append(", Length: ")
				.append(length)
				.append(" feet")
				.toString();
	}

	/**
	 * Builder pattern
	 * 
	 * @author mr222xu
	 *
	 */
	public static class Builder {
		
		private BoatType type;
		private int length;
		
		public Builder() {
			// Empty constructor
		}
		
		public Builder type(BoatType type) {
			this.type = type;
			return this;
		}
		
		public Builder length(int length) {
			this.length = length;
			return this;
		}
		
		public Boat build() {
			return new Boat(type, length);
		}
	}
}
