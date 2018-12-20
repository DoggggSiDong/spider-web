package spider.web.node;

import com.google.gson.JsonObject;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.web.entity.TASK_STATE;
import spider.web.util.ZNodePathUtil;

@Component
public class ZNodeManagement {
    private ZNodePathUtil zNodePathUtil;

    @Autowired
    public ZNodeManagement(ZNodePathUtil zNodePathUtil) {
        this.zNodePathUtil = zNodePathUtil;
    }


    public void switchTaskNodeState(CuratorFramework client, String uuid, TASK_STATE taskState) throws Exception {
        client.setData().forPath(zNodePathUtil.getSpecifiedTaskPath(uuid), taskState.getState().getBytes());
    }

    public byte[] getNodeData(CuratorFramework client, String path) throws Exception {
        return client.getData().forPath(path);
    }

    public void deleteNode(CuratorFramework client, String path) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(path);

    }
}
