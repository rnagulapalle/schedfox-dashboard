package com.schedfox.dashboard.bootstrapper.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.schedfox.dashboard.service.BranchService;

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
	private BranchService branchService;
	
    @RequestMapping
    public String home() {
        return "/WEB-INF/views/angular-index.jsp";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api")
	@ResponseBody
	ResponseEntity<Resource> test() {
    	logger.info("XXXXXXXXXXXXXXXXXXXXXXX");
    	branchService.getBranch();
    	return new ResponseEntity<Resource>(HttpStatus.OK);
    }
}
