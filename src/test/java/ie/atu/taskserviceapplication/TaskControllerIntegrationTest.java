package ie.atu.taskserviceapplication;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class TaskControllerIntegrationTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void createTaskTest() throws Exception {
        TaskDTO taskDTO = new TaskDTO("1", "Test Task", "Description", "Pending");

        when(taskService.createTask(any(Task.class))).thenReturn(new Task("1", "Test Task", "Description", "Pending"));

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content("{ \"id\": \"1\", \"title\": \"Test Task\", \"description\": \"Description\", \"status\": \"Pending\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    void getTaskByIdTest() throws Exception {
        Task task = new Task("1", "Test Task", "Description", "Pending");
        when(taskService.getTaskById("1")).thenReturn(task);

        mockMvc.perform(get("/tasks/{taskId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void deleteTaskTest() throws Exception {
        when(taskService.deleteTask("1")).thenReturn(true);
        mockMvc.perform(delete("/tasks/{taskId}", "1"))
                .andExpect(status().isNoContent());
    }
}