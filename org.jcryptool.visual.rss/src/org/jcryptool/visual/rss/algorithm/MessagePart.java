package org.jcryptool.visual.rss.algorithm;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.Identifier;

public class MessagePart {
	
	private String message;
	private int position;
	private boolean isRedactable;
	
	private static final boolean DEFAULT_IS_REDACTABLE = true;
	private static final int DEFAULT_POSITION = -1;

	
	public MessagePart(String message, int position, boolean isRedactable) {
		this.message = message;
		this.position = position;
		this.isRedactable = isRedactable;
	}
	
	public MessagePart(String message, int position) {
		this(message, position, DEFAULT_IS_REDACTABLE);
	}
	
	public MessagePart(Identifier identifier, boolean isRedactable) {
		this(new String(identifier.getBytes()), identifier.getPosition(), isRedactable);
	}

	public MessagePart(Identifier identifier) {
		this(identifier, DEFAULT_IS_REDACTABLE);
	}
	
	public MessagePart(String message) {
		this(message, DEFAULT_POSITION);
	}
	
	public Identifier toIdentifier() {
		return new Identifier(message.getBytes(), position);
	}
	
	public byte[] getMessageBytes(){
		return message.getBytes();
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isRedactable() {
		return isRedactable;
	}
	public void setRedactable(boolean isRedactable) {
		this.isRedactable = isRedactable;
	}
	
	
	
}