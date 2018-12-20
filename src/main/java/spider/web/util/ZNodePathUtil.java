package spider.web.util;

import org.springframework.stereotype.Component;
import spider.web.base.WEB_ZNODE_PATH;
import spider.web.entity.Task;

@Component
public class ZNodePathUtil {
    public String getTaskPath(){
        return WEB_ZNODE_PATH.TASK.getUrl();
    }

    public String getSpecifiedTaskPath(Task task){
        return WEB_ZNODE_PATH.TASK.getUrl() + "/" + task.getUuId();
    }

    public String getSpecifiedTaskPath(String uuid){
        return WEB_ZNODE_PATH.TASK.getUrl() + "/" + uuid;
    }

    public String getSpecifiedTaskOwnerPath(Task task){
        return WEB_ZNODE_PATH.TASK.getUrl() + "/" + task.getUuId() + "/" + "owner";
    }
    public String getSpecifiedTaskOwnerPath(String uuid){
        return WEB_ZNODE_PATH.TASK.getUrl() + "/" + uuid + "/" + "owner";
    }

    public String getSpecifiedTaskInfoPath(Task task){
        return WEB_ZNODE_PATH.TASK.getUrl() + "/" + task.getUuId() + "/" + "info";
    }

    public String getSpecifiedTaskInfoPath(String uuid){
        return WEB_ZNODE_PATH.TASK.getUrl() + "/" + uuid + "/" + "info";
    }

    public String getSpiderWebNodePath(){
        return WEB_ZNODE_PATH.NODE.getUrl();
    }

    public String getSpecifiedSpiderWebNodePath(String nodeName){
        return WEB_ZNODE_PATH.NODE.getUrl() + "/" + nodeName;
    }


}
