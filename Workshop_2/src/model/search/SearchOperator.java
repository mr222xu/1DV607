package model.search;

public enum SearchOperator {

	EQUALS("="),
	NOT_EQUALS("!="),
	SMALLER_THAN("<"),
	GREATER_THAN(">"),
	SMALLER_EQUALS("<="),
	GREATER_EQUALS(">=");
	
	private String value;
	
	private SearchOperator(String value) {
		this.value = value;
	}
	
    public static SearchOperator getEnum(String value) {
        for(SearchOperator op : values())
            if(op.value.equals(value))
            	return op;
        
        throw new IllegalArgumentException("\"" + value + "\" is invalid.");
    }
    
    @Override
    public String toString() {
    	return value;
    }
}
