package com.rehman.elearning.rest.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
public class CourseModuleRequestDTO {

    private Long id;

    @NotBlank(message = "Heading is required")
    private String heading;

    private String description; // Optional, consider adding @NotBlank if required

    @NotNull(message = "Priority must not be null")
    @Min(value = 0, message = "Priority must be a non-negative integer")
    private Integer priority;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
