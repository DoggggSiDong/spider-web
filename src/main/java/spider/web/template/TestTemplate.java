package spider.web.template;

import spder.task.Task;
import spder.task.Template;

public class TestTemplate extends Template {
    public TestTemplate(Task task) {
        super(task);
    }

    @Override
    public void run() {
        System.out.println("task in");
        try {
            Thread.sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task out");
    }
}
