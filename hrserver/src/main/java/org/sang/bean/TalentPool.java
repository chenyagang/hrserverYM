package org.sang.bean;

import java.util.Date;

public class TalentPool {

    private Integer id;

    private String name;

    private String recommendClient;

    private String recommendTime;

    private String job;

    private Integer workAge;

    private String progress;




    private String technology;

    private String workCity;

    private String lastCompany;

    private String phone;

    private String school;

    private String graduation;

    private String major;

    private String education;

    private String lastSalary;

    private String hopeSalary;

    private Integer appointment;

    private String communicate;

    private String interviewDate;

    private String recommend;

    private String insideResult;

    private String customerResult;

    private String hr;

    private String remark;

    private String channel;

    private String addDate;

    private Integer hrId;

    private String tags;

    private Integer status;
    private String statusText;

    
    private Integer pageNo;
    private Integer pageSize;
    

    private String hrName;

    private String operTime;


    private String level;

    private String profits;

    private String entryDate;

    private String entryMark;

    public String getRecommendClient() {
        return recommendClient;
    }

    public void setRecommendClient(String recommendClient) {
        this.recommendClient = recommendClient;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProfits() {
        return profits;
    }

    public void setProfits(String profits) {
        this.profits = profits;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryMark() {
        return entryMark;
    }

    public void setEntryMark(String entryMark) {
        this.entryMark = entryMark;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public Integer getPageNo() {
        if(pageNo == null){
            return 1;
        }
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
        if(pageSize == null){
            return 10;
        }
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTechnology(){
        return this.technology;
    }

    public void setTechnology(String technology){
        this.technology = technology;
    }

    public String getWorkCity(){
        return this.workCity;
    }

    public void setWorkCity(String workcity){
        this.workCity = workcity;
    }

    public String getLastCompany(){
        return this.lastCompany;
    }

    public void setLastCompany(String lastcompany){
        this.lastCompany = lastcompany;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getSchool(){
        return this.school;
    }

    public void setSchool(String school){
        this.school = school;
    }

    public String getGraduation(){
        return this.graduation;
    }

    public void setGraduation(String graduation){
        this.graduation = graduation;
    }

    public String getMajor(){
        return this.major;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public String getEducation(){
        return this.education;
    }

    public void setEducation(String education){
        this.education = education;
    }

    public String getLastSalary(){
        return this.lastSalary;
    }

    public void setLastSalary(String lastsalary){
        this.lastSalary = lastsalary;
    }

    public String getHopeSalary(){
        return this.hopeSalary;
    }

    public void setHopeSalary(String hopesalary){
        this.hopeSalary = hopesalary;
    }

    public Integer getAppointment() {
        return appointment;
    }

    public void setAppointment(Integer appointment) {
        this.appointment = appointment;
    }

    public String getCommunicate(){
        return this.communicate;
    }

    public void setCommunicate(String communicate){
        this.communicate = communicate;
    }

    public String getInterviewDate(){
        return this.interviewDate;
    }

    public void setInterviewDate(String interviewdate){
        this.interviewDate = interviewdate;
    }

    public String getRecommend(){
        return this.recommend;
    }

    public void setRecommend(String recommend){
        this.recommend = recommend;
    }

    public String getInsideResult(){
        return this.insideResult;
    }

    public void setInsideResult(String insideresult){
        this.insideResult = insideresult;
    }

    public String getCustomerResult(){
        return this.customerResult;
    }

    public void setCustomerResult(String customerresult){
        this.customerResult = customerresult;
    }

    public String getHr(){
        return this.hr;
    }

    public void setHr(String hr){
        this.hr = hr;
    }

    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getChannel(){
        return this.channel;
    }

    public void setChannel(String channel){
        this.channel = channel;
    }

    public String getAddDate(){
        return this.addDate;
    }

    public void setAddDate(String adddate){
        this.addDate = adddate;
    }

    public Integer getHrId(){
        return this.hrId;
    }

    public void setHrId(Integer hrid){
        this.hrId = hrid;
    }

    public String getTags(){
        return this.tags;
    }

    public void setTags(String tags){
        this.tags = tags;
    }

    public Integer getStatus(){
        return this.status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

}