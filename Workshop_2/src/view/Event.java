package view;

/**
 * Event enum
 * 
 * @author mr222xu
 *
 */
public enum Event {
	LOGIN("0"),
	CREATE_MEMBER("1"),
	LIST_MEMBERS_COMPACT("2"),
	LIST_MEMBERS_VERBOSE("3"),
	DELETE_MEMBER("4"),
	UPDATE_MEMBER("5"),
	CREATE_BOAT("6"),
	DELETE_BOAT("7"),
	UPDATE_BOAT("8"),
	QUIT("q");
	
	private String value;
	
	private Event(String value) {
		this.value = value;
	}
	
    public static Event getEnum(String value) {
        for(Event event : values())
            if(event.value.equals(value))
            	return event;
        
        throw new IllegalArgumentException("\"" + value + "\" is invalid.");
    }
}
