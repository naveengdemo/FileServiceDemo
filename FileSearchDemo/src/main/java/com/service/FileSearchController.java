package com.service;


import java.util.List;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.search.bean.AppProperties;
import com.service.search.bean.SearchRequest;
import com.service.search.bean.SearchResponse;
import com.service.search.bean.ValidationError;
import com.service.search.constant.AppContextConfig;
import com.service.search.constant.SearchRestConstants;
import com.service.search.delegate.FileSearchService;
import com.service.search.exception.WordNotFoundException;


/**
 * 
 * @version 1.0
 * @author Navaneethan Created Rest service using spring boot to process request and return response wwith Word matches file.
 *       
 */ 
@RestController
@EnableAutoConfiguration
@RequestMapping(SearchRestConstants.ROOT_CONTEXT)

public class FileSearchController {
	
	
	@Autowired
	AbstractApplicationContext context;
	@Autowired
	AppProperties appProperties;
	@Autowired
	FileSearchService fileSearchService;
	
	public static final Logger logger = LogManager.getLogger(FileSearchController.class);	
	 
	/**
	 * SearchMultiWords method receive List of Service Request and process and return Service Response if words match against files. 
	 * @return  HTTP Status 200 with List of files
	 */
	@RequestMapping(value=SearchRestConstants.SEARCH_WORDS, headers = "Accept=application/json",produces = MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<List<SearchResponse>> SearchMultiWords(@Valid @RequestBody List<SearchRequest> srchRequest, BindingResult bindResult) throws Exception {
		List<SearchResponse> searchMatchFiles = null;
		ValidationError validateError = new ValidationError();
		validateError.validate(srchRequest, bindResult);
		if(bindResult.hasErrors()){
			return new ResponseEntity<List<SearchResponse>>(searchMatchFiles, HttpStatus.BAD_REQUEST);	
		}
		
    	context = new AnnotationConfigApplicationContext(AppContextConfig.class);
		appProperties = context.getBean(AppProperties.class);
		context.close();
		long sTime = System.currentTimeMillis();
		logger.info("Calling search controller to match words in files : Start tile"+ sTime);
		//Delegate request to process in FileSearchService using getMatchWordFiles
		//fileSearchService = new FileSearchService();
		searchMatchFiles = fileSearchService.getMatchWordFiles(appProperties,srchRequest);
		
		 long eTime = System.currentTimeMillis();
		logger.info("Total time taken for search files : "+ (eTime-sTime));
		if(searchMatchFiles.size() <= 0)
			throw new WordNotFoundException(SearchRestConstants.WORD_NOT_MATCH);
		return new ResponseEntity<List<SearchResponse>>(searchMatchFiles, HttpStatus.OK);
		 
	}
    
}  