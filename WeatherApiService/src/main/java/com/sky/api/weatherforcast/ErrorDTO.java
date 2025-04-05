package com.sky.api.weatherforcast;

import java.util.Date;

public class ErrorDTO {
	private Date timastamp;
	private int status;
	private String path;
	private String error;

	public Date getTimastamp() {
		return timastamp;
	}

	public void setTimastamp(Date timastamp) {
		this.timastamp = timastamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
