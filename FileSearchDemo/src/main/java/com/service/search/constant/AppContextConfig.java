package com.service.search.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.service.search.bean.AppProperties;

@Configuration
@PropertySource("classpath:application.properties")
public class AppContextConfig {
	@Autowired
	Environment environment;

	@Bean
	AppProperties appProperties() {
		AppProperties appPrty = new AppProperties();
		appPrty.setBasePath(environment.getProperty("app.basepath"));
		appPrty.setThreadPool(2);
		return appPrty;
	}
}


	