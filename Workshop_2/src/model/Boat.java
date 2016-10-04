package model;

public class Boat {
	
	private BoatType type;
	private int length;
	
	public Boat(BoatType type, int length) {
		setType(type);
		setLength(length);
	}

	public BoatType getType() {
		return type;
	}

	public void setType(BoatType type) {
		if (type == null)
			throw new IllegalArgumentException("Boat type cannot be null.");
		
		this.type = type;
	}

	public int getLength() {		
		return length;
	}

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
