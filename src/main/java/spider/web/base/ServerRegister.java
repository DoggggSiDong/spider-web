package spider.web.base;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spider.web.util.ZNodePathUtil;

import javax.annotation.PostConstruct;

@Component
public class ServerRegister {
    private CuratorFramework client;
    private ZNodePathUtil zNodePathUtil;
    private String serverName;
    private ApplicationProperties applicationProperties;
    @Autowired
    public ServerRegister(CuratorFramework client, ZNodePathUtil zNodePathUtil,ApplicationProperties applicationProperties){
        this.client = client;
        this.zNodePathUtil = zNodePathUtil;
        this.applicationProperties = applicationProperties;
    }
    @PostConstruct
    public void registerServer(){
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(zNodePathUtil.getSpecifiedSpiderWebNodePath(applicationProperties.getHostName()),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
