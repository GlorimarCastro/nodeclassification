package org.wikidata.wdkt.enums;

//This enum is a map from string class to the corresponding ID class in Wikidata
public enum Entity {
	HUMAN("Q5");
	
	private String entityId;

	private Entity(String entityId) {
		this.entityId = entityId;
	}
	
	public String toString(){
		return entityId;
	}
}