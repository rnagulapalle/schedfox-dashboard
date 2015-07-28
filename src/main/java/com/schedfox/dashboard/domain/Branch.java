package com.schedfox.dashboard.domain;

import com.schedfox.dashboard.response.Location;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Branch {

    private Integer branchId;
    private String branchName;

    private ArrayList<Location> locations;

    public Branch() {
        locations = new ArrayList<Location>();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return the branchId
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the ratios
     */
    public ArrayList<Location> getRatios() {
        return locations;
    }

    /**
     * @param ratios the ratios to set
     */
    public void setRatios(ArrayList<Location> ratios) {
        this.locations = ratios;
    }

    public Map getBranchMetrics() {
        HashMap<String, Object> branchMetrics = new HashMap<String, Object>();

        double paidamt = 0;
        double billamt = 0;
        if (locations != null && !locations.isEmpty()) {
            for (int l = 0; l < this.locations.size(); l++) {
                try {
                    paidamt += locations.get(l).getPaidAmount().doubleValue();
                } catch (Exception exe) {
                }
                try {
                    billamt += locations.get(l).getBillAmount().doubleValue();
                } catch (Exception exe) {}
            }
        }

        branchMetrics.put("branch_id", this.getBranchId());
        try {
            branchMetrics.put("percent", new BigDecimal(paidamt / billamt));
        } catch (Exception exe) {
            branchMetrics.put("percent", new BigDecimal(0));
        }
        branchMetrics.put("paidamt", new BigDecimal(paidamt));
        branchMetrics.put("billamt", new BigDecimal(billamt));
        return branchMetrics;
    }

}
