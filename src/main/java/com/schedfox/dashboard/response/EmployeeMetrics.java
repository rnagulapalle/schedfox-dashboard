package com.schedfox.dashboard.response;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/**
 *
 * @author rnagulapalle
 *
 */
@Component
public class EmployeeMetrics implements Serializable {

    private static final long serialVersionUID = 1L;
    private String employeeName;
    private BigDecimal paidAmount;
    private BigDecimal billAmount;
    private BigDecimal paidHourlyRegular;
    private BigDecimal billHourlyRegular;
    
    private String rateCodeStr;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public BigDecimal getPaidAmount() {
        if (paidAmount != null) {
            return paidAmount;
        } else {
            return new BigDecimal(0);
        }
        
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getBillAmount() {
        if (billAmount != null) {
            return billAmount;
        } else {
            return new BigDecimal(0);
        }
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getPercentage() {
        try {
            return new BigDecimal(this.paidHourlyRegular.doubleValue() / this.billHourlyRegular.doubleValue());
        } catch (Exception exe) {
            return new BigDecimal(0);
        }
    }

    /**
     * @return the paidHourlyRegular
     */
    public BigDecimal getPaidHourlyRegular() {
        return paidHourlyRegular;
    }

    /**
     * @param paidHourlyRegular the paidHourlyRegular to set
     */
    public void setPaidHourlyRegular(BigDecimal paidHourlyRegular) {
        this.paidHourlyRegular = paidHourlyRegular;
    }

    /**
     * @return the billHourlyRegular
     */
    public BigDecimal getBillHourlyRegular() {
        return billHourlyRegular;
    }

    /**
     * @param billHourlyRegular the billHourlyRegular to set
     */
    public void setBillHourlyRegular(BigDecimal billHourlyRegular) {
        this.billHourlyRegular = billHourlyRegular;
    }

    /**
     * @return the rateCodeStr
     */
    public String getRateCodeStr() {
        return rateCodeStr;
    }

    /**
     * @param rateCodeStr the rateCodeStr to set
     */
    public void setRateCodeStr(String rateCodeStr) {
        this.rateCodeStr = rateCodeStr;
    }
}
