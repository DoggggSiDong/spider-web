package spider.web.task;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spder.task.Task;
import spider.web.base.ApplicationProperties;
import spider.web.entity.TASK_STATE;
import spider.web.node.ZNodeManagement;
import spider.web.util.ZNodePathUtil;

import javax.annotation.PostConstruct;

@Component
public class TaskListWatcher {
    private CuratorFramework client;
    private ZNodePathUtil zNodePathUtil;
    private TaskPeriodManagement taskPeriodManagement;
    private final PathChildrenCache childrenCache;
    private ZNodeManagement zNodeManagement;
    private ApplicationProperties applicationProperties;
    @Autowired
    public TaskListWatcher(CuratorFramework client,ZNodePathUtil zNodePathUtil,
                           TaskPeriodManagement taskPeriodManagement, ZNodeManagement zNodeManagement,
                           ApplicationProperties applicationProperties) throws Exception {
        this.client = client;
        this.zNodePathUtil = zNodePathUtil;
        this.zNodeManagement = zNodeManagement;
        this.taskPeriodManagement = taskPeriodManagement;
        this.applicationProperties = applicationProperties;
        childrenCache = new PathChildrenCache(client, zNodePathUtil.getTaskPath(), true);


    }
    @PostConstruct
    private void initTaskListWatcher() throws Exception {
        addNodeWatcher(childrenCache);
    }
    private void addTask(CuratorFramework client, String uuid, String owner) throws Exception {
        Task task = new Task();
        task.setUuId(uuid);
        task.setOwner(owner);
        JsonObject info = new JsonParser().parse(new String(client.getData().forPath(zNodePathUtil.getSpecifiedTaskInfoPath(uuid)))).getAsJsonObject();
        task.setCompany(info.get("company").toString().replace("\"",""));
        task.setTemplateName(info.get("templateName").toString().replace("\"",""));
        task.setPeriod(info.get("period").getAsInt());
        taskPeriodManagement.addPeriodTask(task);
    }
    private void addNodeWatcher(final PathChildrenCache childrenCache) throws Exception {
        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        childrenCache.getListenable().addListener(
                new PathChildrenCacheListener() {
                    @Override
                    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                        String path = event.getData().getPath();
                        String uuid = path.split("/")[3];
                        String owner = null;
                        switch (event.getType()) {
                            case CHILD_ADDED:
                                owner = new String(client.getData().forPath(zNodePathUtil.getSpecifiedTaskOwnerPath(uuid)));
                                if(owner.equals(applicationProperties.getHostName())){
                                    addTask(client,uuid,owner);
                                }
                                break;
                            case CHILD_UPDATED:
                                owner = new String(client.getData().forPath(zNodePathUtil.getSpecifiedTaskOwnerPath(uuid)));
                                if(owner.equals(applicationProperties.getHostName())){
                                    String state = new String(event.getData().getData());
                                    switch (state){
                                        case "wait_for_run":
                                            addTask(client,uuid,owner);
                                            break;
                                        case "close":
                                            taskPeriodManagement.closePeriodTask(uuid);
                                            zNodeManagement.switchTaskNodeState(client,uuid, TASK_STATE.CLOSE_SUCCESS);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            default:
                                break;
                        }
                    }
                }
        );
    }


}
