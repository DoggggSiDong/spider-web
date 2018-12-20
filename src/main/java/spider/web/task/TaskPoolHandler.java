package spider.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spder.task.Task;
import spder.task.Template;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
public class TaskPoolHandler {
    private TaskThreadPoolExcutor taskThreadPoolExcutor;

    @Autowired
    public TaskPoolHandler(TaskThreadPoolExcutor taskThreadPoolExcutor) {
        this.taskThreadPoolExcutor = taskThreadPoolExcutor;
    }

    public void addTask(Task task) {
        try {
            Class clazz = Class.forName(task.getTemplateName());
            try {
                Constructor constructor = clazz.getConstructor(Task.class);
                Template taskTemplate = (Template) constructor.newInstance(task);
                taskThreadPoolExcutor.execute(taskTemplate);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
