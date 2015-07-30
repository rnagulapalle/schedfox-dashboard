package com.schedfox.dashboard.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ProfitAnalysisResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String branchName;
    private int branchId;
    private BigDecimal paidamt;
    private BigDecimal billamt;
    private BigDecimal percent;

    private List<Location> locations;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    /**
     * @return the paidamt
     */
    public BigDecimal getPaidamt() {
        return paidamt;
    }

    /**
     * @param paidamt the paidamt to set
     */
    public void setPaidamt(BigDecimal paidamt) {
        this.paidamt = paidamt;
    }

    /**
     * @return the billamt
     */
    public BigDecimal getBillamt() {
        return billamt;
    }

    /**
     * @param billamt the billamt to set
     */
    public void setBillamt(BigDecimal billamt) {
        this.billamt = billamt;
    }

    /**
     * @return the percent
     */
    public BigDecimal getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

}
