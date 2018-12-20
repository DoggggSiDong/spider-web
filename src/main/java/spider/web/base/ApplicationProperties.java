package spider.web.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
public class ApplicationProperties {
    private String port;
    private int threadPoolSize;
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getThreadPoolSize(){
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize){
        this.threadPoolSize = threadPoolSize;
    }
}
