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

		List branches = branchRepo.getBranchList();
		logger.info("Total branches received ....." + branches.size());

		for (Object branch : branches) {
			ProfitAnalysisResponse profitAnalysisResponse = new ProfitAnalysisResponse();
			Map row = (Map) branch;
			int branchId = (int) row.get("branch_id");
			String branchName = (String) row.get("branch_name");
			logger.info("branchId :" + branchId);
			logger.info("branch_name:" + branchName);
			profitAnalysisResponse.setBranchId(branchId);
			profitAnalysisResponse.setBranchName(branchName);
			Map branchMetrics = (Map) branchRepo.getBranchMetrics(String.valueOf(branchId), startDate, endDate);
			profitAnalysisResponse.setBranchMetrics(branchMetrics);
			
			logger.info("Metrics for branch " + String.valueOf(branchMetrics));

			// get location and metrics for each branch
			List locations = branchRepo.getBranchLocationsAndMetrics(String.valueOf(branchId), startDate, endDate);
			List<Location> locationList = new ArrayList<>();
			Map<Location, Metrics> duplicateMap = new HashMap<>();
			for( Object location : locations) {
				Location locRes = new Location();
				Metrics metrics = new Metrics();
				Map locationRow = (Map)location;
				String locationName = (String) locationRow.get("client_name");
				int locationId = (int) locationRow.get("client_id");
				BigDecimal paidAmount = (BigDecimal) locationRow.get("paidamt");
				BigDecimal billAmount = (BigDecimal) locationRow.get("billamt");
				BigDecimal percentage = (BigDecimal) locationRow.get("percent");
				locRes.setLocationId(locationId);
				locRes.setLocationName(locationName);
				locRes.setBranchId(String.valueOf(branchId));
				locRes.setPaidAmount(paidAmount);
				locRes.setBillAmount(billAmount);
				locRes.setPercentage(percentage);
				
				metrics.setBillAmount(billAmount);
				metrics.setPaidAmount(paidAmount);
				metrics.setPercent(percentage);
				
				if(duplicateMap.isEmpty() || !duplicateMap.containsKey(locRes)) {
					locationList.add(locRes);
					duplicateMap.put(locRes, metrics);
				}else{
					
					Metrics existsMetrics = duplicateMap.get(locRes);
					BigDecimal newPaidAmt = new BigDecimal(0);
					BigDecimal newBillAmount = new BigDecimal(0);
					BigDecimal oldPaidAmt = locRes.getPaidAmount();
					
					BigDecimal newPercent = locRes.getPaidAmount();
					if(oldPaidAmt != null && existsMetrics.getPaidAmount() != null && existsMetrics.getPaidAmount().longValue() > 0) {
						newPaidAmt = oldPaidAmt.add(existsMetrics.getPaidAmount());
					}
					
					BigDecimal oldBillAmt = locRes.getBillAmount();
					if(oldBillAmt != null && existsMetrics.getBillAmount() != null && existsMetrics.getBillAmount().longValue() > 0) {
						newBillAmount = oldBillAmt.add(existsMetrics.getBillAmount());
					}
					
					if(newBillAmount.longValue() > 0 && newPaidAmt.longValue() > 0) {
						newPercent = newPaidAmt.divide(newBillAmount, 4, RoundingMode.CEILING);
						newPercent = newPercent.multiply(new BigDecimal(100));
					}
					
					locationList.remove(locRes);
					locRes.setBillAmount(newBillAmount);
					locRes.setPaidAmount(newPaidAmt);
					locRes.setPercentage(newPercent);
					locationList.add(locRes);
					
					//update existing object for next time use
					existsMetrics.setBillAmount(newBillAmount);
					existsMetrics.setPaidAmount(newPaidAmt);
					existsMetrics.setPercent(newPercent);
					duplicateMap.put(locRes, existsMetrics);
				}
			}
			
			
			//iterate through each location and get employees and their metrics
			for(Location location: locationList) {
				logger.info("inside getting employees metrics of location " + location.getLocationId() + " and branch " + branchId);
				List employees = branchRepo.getLocationEmplyeeMetrics(location.getBranchId(), String.valueOf(location.getLocationId()), startDate, endDate);
				List<EmployeeMetrics> employeeMetricsList = new ArrayList<>();
				for(Object employee: employees) {
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
		// logger.info("data receieved for banch list");
		// logger.info(String.valueOf(branches));
		//
		// //TODO iterate through branches and call getBranchMetrics for each
		// branch ID
		// logger.info(String.valueOf(branchRepo.getBranchMetrics("2")));
		//
		//
		// logger.info(String.valueOf(branchRepo.getLocationEmplyeeMetrics("2",
		// "3078")));
		return profitAnalysislist;
	}
}