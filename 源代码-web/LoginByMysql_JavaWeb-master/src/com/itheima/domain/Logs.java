package com.itheima.domain;

public class Logs {
	private String path;
	private String operation;
	private String operation_time;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}

    public Logs() {
    	setOperation("");
    	setOperation_time("");
    	setPath("");
    }
}
