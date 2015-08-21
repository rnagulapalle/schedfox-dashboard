package com.schedfox.dashboard.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

@Component
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    private int locationId;
    private String locationName;
    private String branchId;
    private List<EmployeeMetrics> employeeMetricsList;
    
    private BigDecimal paidAmount;
    private BigDecimal billAmount;
    private BigDecimal percent;

    public Location() {
        employeeMetricsList = new ArrayList<EmployeeMetrics>();
    }
    
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public BigDecimal getPaidAmount() {
        paidAmount = new BigDecimal(0);
        try {
            for (int e = 0; e < this.employeeMetricsList.size(); e++) {
                try {
                    EmployeeMetrics currMetric = this.employeeMetricsList.get(e);
                    paidAmount = new BigDecimal(paidAmount.doubleValue() + currMetric.getPaidAmount().doubleValue());
                } catch (Exception exe) {}
            }
        } catch (Exception exe) {
            exe.printStackTrace();
        } finally {
            return paidAmount;
        }
    }

    public BigDecimal getBillAmount() {
        billAmount = new BigDecimal(0);
        try {
            if (employeeMetricsList != null) {
                for (int e = 0; e < this.employeeMetricsList.size(); e++) {
                    try {
                        EmployeeMetrics currMetric = this.employeeMetricsList.get(e);
                        billAmount = new BigDecimal(billAmount.doubleValue() + currMetric.getBillAmount().doubleValue());
                    } catch (Exception exe) {}
                }
            }
        } catch (Exception exe) {
            exe.printStackTrace();
        } finally {
            return billAmount;
        }
    }

    public BigDecimal getPercentage() {
        try {
            percent = new BigDecimal(this.getPaidAmount().doubleValue() / this.getBillAmount().doubleValue());
        } catch (Exception exe) {
            percent = new BigDecimal(0);
        }
        return percent;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(locationId).
                append(locationName).
                append(branchId).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Location rhs = (Location) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(this.getLocationId(), rhs.getLocationId()).
                append(this.getLocationName(), rhs.getLocationName()).
                append(this.getBranchId(), rhs.getBranchId()).
                isEquals();
    }

    @Override
    public String toString() {
        return "Location [locationId=" + locationId + ", locationName=" + locationName + ", branchId="
                + branchId + "]";
    }

    public List<EmployeeMetrics> getEmployeeMetricsList() {
        return employeeMetricsList;
    }

    public void setEmployeeMetricsList(List<EmployeeMetrics> employeeMetricsList) {
        this.employeeMetricsList = employeeMetricsList;
    }
}
