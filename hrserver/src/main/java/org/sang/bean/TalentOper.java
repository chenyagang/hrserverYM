package org.sang.bean;

public class TalentOper {

    private Long operId;

    private Integer hrId;

    private Integer talentId;

    private Long operTime;

    private Integer status;

    private String hrName;

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOperId(){
        return this.operId;
    }

    public void setOperId(Long operId){
        this.operId = operId;
    }

    public Integer getHrId(){
        return this.hrId;
    }

    public void setHrId(Integer hrId){
        this.hrId = hrId;
    }

    public Integer getTalentId(){
        return this.talentId;
    }

    public void setTalentId(Integer talentId){
        this.talentId = talentId;
    }

    public Long getOperTime(){
        return this.operTime;
    }

    public void setOperTime(Long operTime){
        this.operTime = operTime;
    }

}