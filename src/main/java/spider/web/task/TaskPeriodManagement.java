package spider.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.web.entity.Task;
import spider.web.node.ZNodeManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
@Component
public class TaskPeriodManagement {
    private final Map<String,Timer> timerMap;
    private TaskPoolHandler taskPoolHandler;
    private ZNodeManagement zNodeManagement;
    @Autowired
    public TaskPeriodManagement(TaskPoolHandler taskPoolHandler,ZNodeManagement zNodeManagement){
        this.taskPoolHandler = taskPoolHandler;
        this.zNodeManagement = zNodeManagement;
        timerMap = new HashMap<>(32);
    }

    public void addPeriodTask(Task task){
        if(task.getPeriod() > 0){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("123");
                    taskPoolHandler.addTask(task);
                }
            }, 0, TimeUnit.SECONDS.toMillis(30));
            timerMap.put(task.getUuId(),timer);
        }
        else {
            taskPoolHandler.addTask(task);
        }
    }

    public void closePeriodTask(String uuid){
        timerMap.get(uuid).cancel();
        timerMap.remove(uuid);
    }
}
