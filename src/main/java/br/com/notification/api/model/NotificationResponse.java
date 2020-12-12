package br.com.notification.api.model;

import java.io.Serializable;

public class NotificationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8681780815107864235L;

	private String message;

	public NotificationResponse() {

	}

	public NotificationResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
