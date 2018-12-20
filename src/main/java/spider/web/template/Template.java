package spider.web.template;

import spider.web.entity.Task;
import us.codecraft.webmagic.Spider;

public abstract class Template implements Runnable {
    private Task task;
    private Spider spider;
    public Template(Task task){
        this.task = task;
    }

    public Task getTask(){
        return task;
    }

    public void runTask(){
        spider.run();
    }
}
