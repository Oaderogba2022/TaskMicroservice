package ie.atu.taskserviceapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
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
            task.setDueDate(taskDetails.getDueDate());
            task.setStatus(taskDetails.getStatus());
            return taskRepository.save(task);
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
}
