package com.service.search.delegate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.service.search.bean.AppProperties;
import com.service.search.bean.SearchRequest;
import com.service.search.bean.SearchResponse;
import com.service.search.constant.SearchRestConstants;
import com.service.search.exception.FileNotExistException;
import com.service.search.exception.WordNotFoundException;
import com.service.search.file.handler.FileHandler;


/**
 * 
 * @version 1.0
 * @author FileSearchService is created to process input and search files in given words.
 *       
 */
@Service
public class FileSearchService {
	public static final Logger logger = LogManager.getLogger(FileSearchService.class);	
    
	/**
	 * getMatchWordFiles method will search words against list of files in directory and collect matching files. 
	 * @return  list of files
	 */

	public List<SearchResponse> getMatchWordFiles(AppProperties appProperties, List<SearchRequest> srchRequest) throws Exception{
		List<String> wordList= new ArrayList<>();
		List<SearchResponse> matchFileList = new ArrayList<>();
		try {
			
			srchRequest.forEach(word->{
				wordList.add(word.getSearchword());
			});
			logger.info("List of words to match in files : " + wordList);
			FileHandler fileHandler = new FileHandler(SearchRestConstants.GLOB_PATTERN);
			List<String> fileList = fileHandler.getFileList(appProperties.getBasePath(), fileHandler);
			if(fileList == null || fileList.size() ==0){
				throw new FileNotExistException("File not exist Exception occured");
			}
			List<String> temList = new ArrayList<>();
			for(String file : fileList){
				FileReader fileIn = new FileReader(file);
		        BufferedReader reader = new BufferedReader(fileIn);
		        String line;
		        boolean isWordsMatch = false;
		        List<String> tempWordList = (List<String>) ((ArrayList)wordList).clone();
	        	while((line = reader.readLine()) != null) {
		        	isWordsMatch = false;
		        	for(String word : tempWordList){
		        		if(line.contains(word)) {
		        			String[] lineArr = line.split(" ");
		        			for(String lineWord : lineArr)
		        				if(lineWord.contains(word)){
		        					temList.add(word);
		        					break;
		        			}
			            }	        		
		        	}
		        	tempWordList.removeAll(temList);
		        	temList.clear();
		        	if(tempWordList.size()==0){
	    				isWordsMatch = true;
	            		break;
	            	 }	
		        }
	        	// if all words matches then add the response to list
		        if(isWordsMatch){
		      	  SearchResponse matchFile = new SearchResponse();
		      	  matchFile.setFilePath(file.substring(0,file.lastIndexOf("\\")));
		      	  matchFile.setFileName(file.substring(file.lastIndexOf("\\")+1));
		  	  	  matchFileList.add(matchFile);
		      	    
		        }
			}
			
	    }catch (NoSuchFileException ne){
	    	throw new FileNotExistException("File not exist Exception occured");
	    }catch (IOException e ){
			throw new Exception("IO Exception occured");
		}
	 logger.info("Total files matched against words : "+  matchFileList.size());
	return matchFileList;
}

}

