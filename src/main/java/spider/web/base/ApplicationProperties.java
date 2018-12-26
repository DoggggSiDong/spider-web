package spider.web.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
public class ApplicationProperties {
    @Value("${name}")
    private String hostName;
    private int threadPoolSize;
    public String getHostName(){
        return this.hostName;
    }

    public void setHostName(String hostName){
        this.hostName = hostName;
    }
    public int getThreadPoolSize(){
        return threadPoolSize;
    }
    public void setThreadPoolSize(int threadPoolSize){
        this.threadPoolSize = threadPoolSize;
    }
}
