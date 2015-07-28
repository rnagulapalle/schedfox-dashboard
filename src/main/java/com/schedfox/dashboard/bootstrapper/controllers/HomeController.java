package com.schedfox.dashboard.bootstrapper.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.schedfox.dashboard.response.ProfitAnalysisResponse;
import com.schedfox.dashboard.service.ProfitAnalysisService;

/**
 * Created with IntelliJ IDEA.
 * User: will
 * Date: 11/23/13
 * Time: 12:37 PM
 */
@Controller
@RequestMapping("/")
public class HomeController {
	

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProfitAnalysisService profitAnalysisService;
	
    @RequestMapping
    public String home() {
        return "/WEB-INF/views/angular-index.jsp";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profitanalysis", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> test() {
    	logger.info("fetching profit analysis chart data");
    	List<ProfitAnalysisResponse> profitAnalysisResponse = profitAnalysisService.getProfitAnaylsisData();
    	return new ResponseEntity<List>(profitAnalysisResponse, HttpStatus.OK);
    }
}
