package ie.atu.taskserviceapplication;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId()); // Map the ID
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(task.getStatus());
        return taskDTO;
    }

    public static Task toEntity(TaskDTO taskDTO) {
        return new Task(
                taskDTO.getId(),
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getStatus()
        );
    }
}