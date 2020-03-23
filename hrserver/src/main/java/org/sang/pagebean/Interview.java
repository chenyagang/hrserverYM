package org.sang.pagebean;

public class Interview {

    private Integer id;

    private String name;

    private String userName;

    private String recommendClient;

    private String recommendTime;

    private String job;

    private Integer workAge;

    private String progress;

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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


    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
