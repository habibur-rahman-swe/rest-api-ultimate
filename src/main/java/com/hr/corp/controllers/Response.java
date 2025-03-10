package com.hr.corp.controllers;

import java.util.Date;

public class Response {
	private String message;
	private String time;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Response() {
		super();
	}

	public Response(String message) {
		super(); 
		this.message = message;
		this.time = new Date().toString();
	}
	
}
