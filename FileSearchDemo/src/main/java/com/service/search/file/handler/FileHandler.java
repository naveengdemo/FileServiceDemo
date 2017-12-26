package com.service.search.file.handler;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.service.search.delegate.FileSearchService;

/**
 * 
 * @version 1.0
 * @author FileHandler is created to get list of files in the given base path.
 *       
 */ 
public class FileHandler extends SimpleFileVisitor<Path> {
	
	public static final Logger logger = LogManager.getLogger(FileHandler.class);
	private  List<String> fileList = new ArrayList<>();
	
	private final PathMatcher pathMatcher;
	public FileHandler(String globPattern) {
		pathMatcher = FileSystems.getDefault().getPathMatcher(
				"glob:" + globPattern);
	}

	@Override
	public FileVisitResult visitFile(Path filePath,
			BasicFileAttributes basicFileAttrib) {
		if (pathMatcher.matches(filePath.getFileName())) {
			fileList.add(filePath.toString());
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * getFileList method will return list of files inside base path and collect list of files. 
	 * @return  list of files
	 */
	
public List<String> getFileList(String basePath, FileHandler searchFileVisitor) throws NoSuchFileException{
	Path rootPath = Paths.get(basePath);
	try {
		Files.walkFileTree(rootPath, searchFileVisitor);
	} catch (IOException e) {
		 logger.info(" Files not exists from path : "+ basePath);
	}
    return fileList;
}

	public static void main(String[] args) throws IOException {
		String globPattern = "*.txt";
		FileHandler f = new FileHandler(globPattern);
		
		System.out.println("Match Count: " + f.getFileList("/workspace/galileo/FileSearchDeo",f));
		
	}
}
