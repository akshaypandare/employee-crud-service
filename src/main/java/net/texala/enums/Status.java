package net.texala.enums;

import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	
	ACTIVATED("Activated"), 
	DEACTIVATED("DeActivated"), 
	DELETED("Deleted");
	
	private String name;
	
	private Status(String name){
		this.name=name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}	
	
	/**
	 * Method for return enum based on provided name
	 * @param name
	 * @return
	 */
	@JsonCreator
	public static Status decode(final String name) {
		return  Stream.of(Status.values()).filter(x-> x.name.equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}