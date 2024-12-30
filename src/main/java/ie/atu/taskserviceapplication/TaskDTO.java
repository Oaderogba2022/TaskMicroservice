package ie.atu.taskserviceapplication;

import org.springframework.data.annotation.Id;

public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private String status;

    public TaskDTO() {}

    public TaskDTO(String id,String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
