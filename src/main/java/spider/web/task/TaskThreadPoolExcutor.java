package spider.web.task;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.web.base.ApplicationProperties;
import spider.web.entity.TASK_STATE;
import spider.web.node.ZNodeManagement;
import spider.web.template.Template;
import spider.web.util.ZNodePathUtil;

import java.util.concurrent.*;
@Component
public class TaskThreadPoolExcutor extends ThreadPoolExecutor {
    private CuratorFramework client;
    private ApplicationProperties applicationProperties;
    private ZNodeManagement zNodeManagement;
    private ZNodePathUtil zNodePathUtil;
    @Autowired
    public TaskThreadPoolExcutor(CuratorFramework client, ApplicationProperties applicationProperties,
                                 ZNodeManagement zNodeManagement, ZNodePathUtil zNodePathUtil){
        super(applicationProperties.getThreadPoolSize(),applicationProperties.getThreadPoolSize()*2,0L,TimeUnit.SECONDS,new ArrayBlockingQueue<>(applicationProperties.getThreadPoolSize()));
        this.client = client;
        this.zNodeManagement = zNodeManagement;
        this.zNodePathUtil = zNodePathUtil;

    }
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        Template template = (Template)r;
        System.out.println(template.getTask().toString());
        try{
            zNodeManagement.switchTaskNodeState(client,template.getTask().getUuId(), TASK_STATE.RUNNING);
        }catch (Exception e){
            try {
                zNodeManagement.switchTaskNodeState(client,template.getTask().getUuId(), TASK_STATE.RUNNING_ERROR);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("Template type error");
        }

    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Template template = null;
        template = (Template) r;
        if(t != null){
            try{
                zNodeManagement.switchTaskNodeState(client,template.getTask().getUuId(), TASK_STATE.RUNNING_ERROR);
            }catch (Exception e){
                try {
                    zNodeManagement.switchTaskNodeState(client,template.getTask().getUuId(), TASK_STATE.RUNNING_ERROR);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        else{
            try {
                if (client.checkExists().forPath(zNodePathUtil.getSpecifiedTaskPath(template.getTask())) != null) {
                    if (template.getTask().getPeriod() > 0) {
                        zNodeManagement.switchTaskNodeState(client, template.getTask().getUuId(), TASK_STATE.SUCCESS);
                    } else {
                        zNodeManagement.switchTaskNodeState(client, template.getTask().getUuId(), TASK_STATE.FINISH);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
