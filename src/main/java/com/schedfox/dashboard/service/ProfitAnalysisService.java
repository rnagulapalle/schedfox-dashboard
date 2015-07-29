package com.schedfox.dashboard.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedfox.dashboard.domain.Branch;
import com.schedfox.dashboard.model.BranchRepository;
import com.schedfox.dashboard.response.EmployeeMetrics;
import com.schedfox.dashboard.response.Location;
import com.schedfox.dashboard.response.Metrics;
import com.schedfox.dashboard.response.ProfitAnalysisResponse;
import java.util.Date;

/**
 *
 * @author rnagulapalle
 *
 */
@Service("profitAnalysisService")
public class ProfitAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(ProfitAnalysisService.class);
    @Autowired
    BranchRepository branchRepo;


    /**
     * this method should get all branches and its metrics paid amount, bill
     * amount and its percentage
     *
     * and for each branch get list of locations and their paid amount and bill
     * amount along with their percentage
     *
     * and for each location get list of employees and their metrics
     *
     * if any branch have over pay or over bill amount we should consider
     * summing up before calculating percentage
     *
     * @return
     */
    @Transactional
    public List<ProfitAnalysisResponse> getProfitAnaylsisData() {
        logger.info("inside getting API response");
        List<ProfitAnalysisResponse> profitAnalysislist = new ArrayList<>();

        Date startDate = new Date("07/14/2015");
        Date endDate = new Date("07/28/2015");

        List<Branch> branches = branchRepo.getBranchList(startDate, endDate);
        logger.info("Total branches received ....." + branches.size());

        for (Branch branch : branches) {
            ProfitAnalysisResponse profitAnalysisResponse = new ProfitAnalysisResponse();

            logger.info("branchId :" + branch.getBranchId());
            logger.info("branch_name:" + branch.getBranchName());
            
            profitAnalysisResponse.setBranchId(branch.getBranchId());
            profitAnalysisResponse.setBranchName(branch.getBranchName());
            profitAnalysisResponse.setBranchMetrics(branch.getBranchMetrics());

            logger.info("Metrics for branch " + String.valueOf(branch.getBranchMetrics()));

            List<Location> locationList = branch.getLocations();
            profitAnalysisResponse.setLocations(locationList);
            profitAnalysislist.add(profitAnalysisResponse);

        }

        return profitAnalysislist;
    }
}
