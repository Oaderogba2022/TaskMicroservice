package ie.atu.taskserviceapplication;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TaskService(TaskRepository taskRepository, RabbitTemplate rabbitTemplate) {
        this.taskRepository = taskRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Task createTask(Task task) {
        Task createdTask = taskRepository.save(task);
        sendTaskNotificationToQueue(createdTask);
        return createdTask;
    }

    public Task getTaskById(String taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.orElse(null);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(String taskId, Task taskDetails) {
        Task task = getTaskById(taskId);
        if (task != null) {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            Task updatedTask = taskRepository.save(task);
            sendTaskNotificationToQueue(updatedTask);
            return updatedTask;
        }
        return null;
    }

    public boolean deleteTask(String taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            taskRepository.delete(task);
            return true;
        }
        return false;
    }

    private void sendTaskNotificationToQueue(Task task) {
        rabbitTemplate.convertAndSend("taskQueue", task);
        System.out.println("Sent Task to Queue: " + task);
    }
}