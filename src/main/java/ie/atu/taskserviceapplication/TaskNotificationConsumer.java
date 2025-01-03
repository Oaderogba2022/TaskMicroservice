package ie.atu.taskserviceapplication;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaskNotificationConsumer {

    @RabbitListener(queues = "taskQueue")
    public void receiveTask(Task task) {
        System.out.println("Received Task from Queue: " + task);
    }
}
