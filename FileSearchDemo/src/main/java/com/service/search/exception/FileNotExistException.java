package com.service.search.exception;

	/**
	 * FileNotExistException class handle error response of file not available exception
	 */
	public class FileNotExistException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		 private String errorMessage;
		
		 public FileNotExistException() {
		  super();
		 }

		public FileNotExistException(String errorMessage){
			super(errorMessage);
			this.errorMessage = errorMessage;
		}
		public String getErrorMessage() {
		    return errorMessage;
		 }
	}
