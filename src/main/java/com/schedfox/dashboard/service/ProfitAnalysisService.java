package com.schedfox.dashboard.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Branch> getBranchDetails() {
        List<Branch> list = branchRepo.getBranchDetails();
        return list;
    }

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
    public List<ProfitAnalysisResponse> getProfitAnaylsisData(Date startDate, Date endDate) {
        logger.info("inside getting API response");
        List<ProfitAnalysisResponse> profitAnalysislist = new ArrayList<>();

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

            List<Location> locationList = branch.getRatios();
            
            //iterate through each location and get employees and their metrics
            for (Location location : locationList) {
                logger.info("inside getting employees metrics of location " + location.getLocationId() + " and branch " + location.getBranchId());
                List employees = branchRepo.getLocationEmplyeeMetrics(location.getBranchId(), String.valueOf(location.getLocationId()), startDate, endDate);
                List<EmployeeMetrics> employeeMetricsList = new ArrayList<>();
                for (Object employee : employees) {
                    Map empRow = (Map) employee;
                    String eName = (String) empRow.get("employee_name");
                    BigDecimal ePaidAmount = (BigDecimal) empRow.get("paidamt");
                    BigDecimal eBillAmount = (BigDecimal) empRow.get("billamt");
                    BigDecimal ePercent = (BigDecimal) empRow.get("percent");

                    EmployeeMetrics employeeMetrics = new EmployeeMetrics();
                    employeeMetrics.setBillAmount(eBillAmount);
                    employeeMetrics.setPaidAmount(ePaidAmount);
                    employeeMetrics.setPercent(ePercent);
                    employeeMetrics.setEmployeeName(eName);

                    employeeMetricsList.add(employeeMetrics);

                }
                location.setEmployeeMetricsList(employeeMetricsList);
            }
            profitAnalysisResponse.setLocations(locationList);
            profitAnalysislist.add(profitAnalysisResponse);

        }

        return profitAnalysislist;
    }
}