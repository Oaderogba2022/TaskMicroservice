package ie.atu.taskserviceapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        taskDTO = new TaskDTO("1", "Test Task", "Test Description", "Open");
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(TaskMapper.toEntity(taskDTO));

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content("{ \"id\": \"1\", \"title\": \"Wrong Title\", \"description\": \"Updated Description\", \"status\": \"Closed\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("Open"));
    }

    @Test
    public void testGetTask() throws Exception {
        when(taskService.getTaskById("1")).thenReturn(TaskMapper.toEntity(taskDTO));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("Open"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskDTO updatedTaskDTO = new TaskDTO("1", "Updated Task", "Updated Description", "Closed");
        when(taskService.updateTask(eq("1"), any(Task.class))).thenReturn(TaskMapper.toEntity(updatedTaskDTO));

        mockMvc.perform(put("/tasks/1")
                        .contentType("application/json")
                        .content("{ \"id\": \"1\", \"title\": \"Updated Task\", \"description\": \"Updated Description\", \"status\": \"Closed\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("Closed"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        when(taskService.deleteTask("1")).thenReturn(true);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(List.of(TaskMapper.toEntity(taskDTO)));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].status").value("Open"));
    }
}
