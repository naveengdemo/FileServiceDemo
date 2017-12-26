package com.service.search.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchErrorResponse {
	private int errorCode;
	private String errorMessage;
	
	public SearchErrorResponse(){
		super();
	}
	public SearchErrorResponse(int errorCode, String errorMessage){
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
