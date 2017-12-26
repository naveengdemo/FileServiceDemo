package com.service.search.bean;
 


/**
 * 
 * @version 1.0
 * @author AppProperties is created to read properties from the configured property file.
 *       
 */
public class AppProperties {

	      private int threadPool;

	     private String basePath;

		public int getThreadPool() {
			return threadPool;
		}

		public void setThreadPool(int threadPool) {
			this.threadPool = threadPool;
		}

		public String getBasePath() {
			return basePath;
		}

		public void setBasePath(String basePath) {
			this.basePath = basePath;
		}

	  

	} 