package org.sang.bean;

/**
 * @CLassName ExportCount
 * @Description TODO
 * @Author ll
 * @Date 2018/11/13 16:17
 **/
public class ExportCount {

    private String dateRange;

    private String hrName;

    private int phoneCount;

    private int phoneSuccessCount;

    private String phoneSuccessPercent;

    private int isInsideSuccessCount;

    private int insideSuccessCount;

    private String insideSuccessPercent;

    private int isCustomerSuccessCount;

    private int customerSuccessCount;

    private String customerSuccessPercent;

    private int offerSuccessCount;

    private String offerSuccessPercent;

    private int entrySuccessCount;

    private String entrySuccessPercent;


    public int getIsInsideSuccessCount() {
        return isInsideSuccessCount;
    }

    public int getIsCustomerSuccessCount() {
        return isCustomerSuccessCount;
    }

    public void setIsCustomerSuccessCount(int isCustomerSuccessCount) {
        this.isCustomerSuccessCount = isCustomerSuccessCount;
    }

    public void setIsInsideSuccessCount(int isInsideSuccessCount) {
        this.isInsideSuccessCount = isInsideSuccessCount;
    }

    public String getDateRange() {
        return dateRange;
    }


    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public int getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(int phoneCount) {
        this.phoneCount = phoneCount;
    }

    public int getPhoneSuccessCount() {
        return phoneSuccessCount;
    }

    public void setPhoneSuccessCount(int phoneSuccessCount) {
        this.phoneSuccessCount = phoneSuccessCount;
    }

    public String getPhoneSuccessPercent() {
        return phoneSuccessPercent;
    }

    public void setPhoneSuccessPercent(String phoneSuccessPercent) {
        this.phoneSuccessPercent = phoneSuccessPercent;
    }

    public int getInsideSuccessCount() {
        return insideSuccessCount;
    }

    public void setInsideSuccessCount(int insideSuccessCount) {
        this.insideSuccessCount = insideSuccessCount;
    }

    public String getInsideSuccessPercent() {
        return insideSuccessPercent;
    }

    public void setInsideSuccessPercent(String insideSuccessPercent) {
        this.insideSuccessPercent = insideSuccessPercent;
    }

    public int getCustomerSuccessCount() {
        return customerSuccessCount;
    }

    public void setCustomerSuccessCount(int customerSuccessCount) {
        this.customerSuccessCount = customerSuccessCount;
    }

    public String getCustomerSuccessPercent() {
        return customerSuccessPercent;
    }

    public void setCustomerSuccessPercent(String customerSuccessPercent) {
        this.customerSuccessPercent = customerSuccessPercent;
    }

    public int getOfferSuccessCount() {
        return offerSuccessCount;
    }

    public void setOfferSuccessCount(int offerSuccessCount) {
        this.offerSuccessCount = offerSuccessCount;
    }

    public String getOfferSuccessPercent() {
        return offerSuccessPercent;
    }

    public void setOfferSuccessPercent(String offerSuccessPercent) {
        this.offerSuccessPercent = offerSuccessPercent;
    }

    public int getEntrySuccessCount() {
        return entrySuccessCount;
    }

    public void setEntrySuccessCount(int entrySuccessCount) {
        this.entrySuccessCount = entrySuccessCount;
    }

    public String getEntrySuccessPercent() {
        return entrySuccessPercent;
    }

    public void setEntrySuccessPercent(String entrySuccessPercent) {
        this.entrySuccessPercent = entrySuccessPercent;
    }
}
