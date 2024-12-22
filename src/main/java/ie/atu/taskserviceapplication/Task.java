package ie.atu.taskserviceapplication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    @NotEmpty
    private String title;

    @NotBlank(message = "Message is required")
    private String description;

    private LocalDate dueDate;

    @NotBlank(message = "Message is required")
    private String status;
}