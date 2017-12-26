package com.service.search.bean;
 
import java.util.Collection;
 
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ValidationError implements Validator {

	
	@Override
	public boolean supports(Class<?> arg0) {
		return SearchRequest.class.equals(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		
		Collection collection = (Collection) obj;
		for (Object object : collection) {
			SearchValidator searchValidator = new SearchValidator();
			searchValidator.validate(object, errors);
		}
	}
}
