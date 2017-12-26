package com.service.FileSearchDemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.service.FileSearchController;
import com.service.search.bean.SearchRequest;
import com.service.search.delegate.FileSearchService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FileSearchControllerTest {

	@InjectMocks
	FileSearchController fileSearchController;

    @Test
    public void SearchWordsInFiles() throws Exception {
       

        SearchRequest srchReq = new SearchRequest();
        srchReq.setSearchword("india");
        srchReq.setSearchword("usa");
               

    }
}
