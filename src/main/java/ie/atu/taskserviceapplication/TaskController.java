package ie.atu.taskserviceapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    /*@GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable String taskId) {
        Task task = taskService.getTaskById(taskId);
        return task != null ? new ResponseEntity<>(task, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    /*@PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable String taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task);
        return updatedTask != null ? new ResponseEntity<>(updatedTask, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*@GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }*/


    //UNIT TEST

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task createdTask = taskService.createTask(TaskMapper.toEntity(taskDTO));
        return new ResponseEntity<>(TaskMapper.toDTO(createdTask), HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable String taskId) {
        Task task = taskService.getTaskById(taskId);
        return task != null ?
                new ResponseEntity<>(TaskMapper.toDTO(task), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String taskId, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(taskId, TaskMapper.toEntity(taskDTO));
        return updatedTask != null ?
                new ResponseEntity<>(TaskMapper.toDTO(updatedTask), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = tasks.stream().map(TaskMapper::toDTO).toList();
        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }



}
