package com.schedfox.dashboard.bootstrapper.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
//        return "/WEB-INF/views/angular-index.jsp";
    	return "redirect:/profit-analysis-report";
    }
    
    @RequestMapping(value="/profit-analysis-report")
    public String profitAnalysisReport() {
        return "/WEB-INF/views/profitanalysis.jsp";
    }

    //TODO get start as query param and from that date calculate 3 weeks old data
    //user should be able to switch from 3 weeks any end date he wants
    //so second date is optional value
    /**
     * Method to get the profit analysis json data
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/profitanalysis", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> test(@RequestParam(value = "startDate")
						     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate, 
						   @RequestParam(value = "endDate", required = false) 
						     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
    	logger.info("fetching profit analysis chart data");
    	List<ProfitAnalysisResponse> profitAnalysisResponse;
    	if(startDate != null && endDate != null){
    		profitAnalysisResponse = profitAnalysisService.getProfitAnaylsisData(startDate,endDate);
    	}else{
    		Date endDate1 = getDateBeforeTwoWeeks(startDate);
    		profitAnalysisResponse = profitAnalysisService.getProfitAnaylsisData(startDate,endDate1);
    	}
    	return new ResponseEntity<List>(profitAnalysisResponse, HttpStatus.OK);
    }
    
    /**
     * 
     * @param date
     * @return
     */
    public Date getDateBeforeTwoWeeks(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -21); //3 weeks
        return calendar.getTime();
    }
}
