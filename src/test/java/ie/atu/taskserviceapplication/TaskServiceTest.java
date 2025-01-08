package ie.atu.taskserviceapplication;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("1", "Test Task", "Test Description", "Open");
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), any(Object.class));

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("Test Description", createdTask.getDescription());
        assertEquals("Open", createdTask.getStatus());
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(Object.class));
    }

    @Test
    public void testGetTaskById_TaskFound() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById("1");

        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getTitle());
        assertEquals("Test Description", foundTask.getDescription());
        assertEquals("Open", foundTask.getStatus());
    }

    @Test
    public void testGetTaskById_TaskNotFound() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());

        Task foundTask = taskService.getTaskById("1");

        assertNull(foundTask);
    }

    @Test
    public void testUpdateTask_TaskFound() {
        Task updatedTask = new Task("1", "Updated Task", "Updated Description", "Closed");
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask("1", updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("Closed", result.getStatus());
    }

    @Test
    public void testUpdateTask_TaskNotFound() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());

        Task updatedTask = new Task("1", "Updated Task", "Updated Description", "Closed");
        Task result = taskService.updateTask("1", updatedTask);

        assertNull(result);
    }


    @Test
    public void testDeleteTask_TaskNotFound() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());

        boolean isDeleted = taskService.deleteTask("1");

        assertFalse(isDeleted);
        verify(taskRepository, times(0)).delete(any(Task.class));
        verify(rabbitTemplate, times(0)).convertAndSend(anyString(), any(Object.class));
    }
}