package spider.web.base;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("singleton-curator-client-facotory")
public class SingletonCuratorClient {
    private SingletonCuratorClient() {

    }

    @Bean("singleton-curator-client")
    @Lazy
    public static CuratorFramework getCuratorClient() {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
                new RetryNTimes(10, 5000));
        client.start();
        init(client);
        return client;
    }

    public static void init(CuratorFramework client) {
        try {
            if (client.checkExists().forPath("/spider-nest") == null) {
                client.create().forPath("/spider-nest");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
