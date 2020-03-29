package org.sang.resources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="com.hrserve.resources")
@PropertySource(value="classpath:resource.properties")
public class Resource {

    private String serverFilePath;

    private String winFilePath;

    public String getWinFilePath() {
        return winFilePath;
    }

    public void setWinFilePath(String winFilePath) {
        this.winFilePath = winFilePath;
    }

    public String getServerFilePath() {
        return serverFilePath;
    }

    public void setServerFilePath(String serverFilePath) {
        this.serverFilePath = serverFilePath;
    }
}
