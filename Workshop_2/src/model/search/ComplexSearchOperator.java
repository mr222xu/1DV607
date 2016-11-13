package model.search;

public enum ComplexSearchOperator {

	AND("&&"),
	OR("||");
	
	private String value;
	
	private ComplexSearchOperator(String value) {
		this.value = value;
	}
	
    public static ComplexSearchOperator getEnum(String value) {
        for(ComplexSearchOperator op : values())
            if(op.value.equals(value))
            	return op;
        
        throw new IllegalArgumentException("\"" + value + "\" is invalid.");
    }
    
    @Override
    public String toString() {
    	return value;
    }
}