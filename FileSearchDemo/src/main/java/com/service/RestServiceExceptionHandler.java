package com.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.service.search.bean.SearchErrorResponse;
import com.service.search.constant.SearchRestConstants;
import com.service.search.exception.FileNotExistException;
import com.service.search.exception.InvalidRequestException;
import com.service.search.exception.WordNotFoundException;

 
/**
 * 
 * @version 1.0
 * @author RestServiceExceptionHandler is created to handle micro service exception handler.
 *       
 */ 
@RestControllerAdvice   
public class RestServiceExceptionHandler {
	  
    
	/**
	 * handleInvalidRequestException method return if there is invalid request. 
	 * @return  HTTP Status invalid request error message
	 */
	@ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<SearchErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
    	SearchErrorResponse errorResponse = new SearchErrorResponse();
        errorResponse.setErrorCode(400);
        errorResponse.setErrorMessage(SearchRestConstants.INVALID_USER_REQ);
        return new ResponseEntity<SearchErrorResponse>(errorResponse, HttpStatus.OK);
    }
	/**
	 * handleWordNotFoundException method return if there is no word match against files. 
	 * @return  HTTP Status not found error message
	 */
	@ExceptionHandler(WordNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<SearchErrorResponse> handleWordNotFoundException(WordNotFoundException ex) {
    	SearchErrorResponse errorResponse = new SearchErrorResponse();
        errorResponse.setErrorCode(404);
        errorResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<SearchErrorResponse>(errorResponse, HttpStatus.OK);
    }
	/**
	 * handleFileNotExistException method return if there is no word match against files. 
	 * @return  HTTP Status not found error message
	 */
	@ExceptionHandler(FileNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<SearchErrorResponse> handleFileNotExistException(FileNotExistException ex) {
    	SearchErrorResponse errorResponse = new SearchErrorResponse();
        errorResponse.setErrorCode(404);
        errorResponse.setErrorMessage(SearchRestConstants.FILE_NOT_EXIST);
        return new ResponseEntity<SearchErrorResponse>(errorResponse, HttpStatus.OK);
    }
	  
	/**
	 * handleGenericException method return if there is any network issue or general exception. 
	 * @return  HTTP Status internal server error message
	 */	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SearchErrorResponse> handleGenericException(Exception ex) {
    	SearchErrorResponse errorResponse = new SearchErrorResponse();
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setErrorMessage("There is some internal server issue");
        return new ResponseEntity<SearchErrorResponse>(errorResponse, HttpStatus.OK);
    }
 
}
