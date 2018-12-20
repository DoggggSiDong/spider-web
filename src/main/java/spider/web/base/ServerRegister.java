package spider.web.base;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.web.util.IpAddressUtil;
import spider.web.util.ZNodePathUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServerRegister {
    private CuratorFramework client;
    private ZNodePathUtil zNodePathUtil;
    private ApplicationProperties applicationProperties;
    private IpAddressUtil ipAddressUtil;
    @Autowired
    public ServerRegister(CuratorFramework client, ZNodePathUtil zNodePathUtil,
                          ApplicationProperties applicationProperties, IpAddressUtil ipAddressUtil){
        this.client = client;
        this.zNodePathUtil = zNodePathUtil;
        this.applicationProperties = applicationProperties;
        this.ipAddressUtil = ipAddressUtil;
        InetAddress inetAddress = null;
        try {
             inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String host = inetAddress.getHostName();
        String ip = inetAddress.getHostAddress();
        String port = applicationProperties.getPort();
        System.out.println();
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(zNodePathUtil.getSpecifiedSpiderWebNodePath("01"),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
