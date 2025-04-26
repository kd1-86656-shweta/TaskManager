package com.taskmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Title must not contain special characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Description must not contain special characters")
    private String description;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime expectedStartDateTime;

    private LocalDateTime expectedEndDateTime;

    private boolean isDeleted = false;

    @PrePersist
    @PreUpdate
    public void validateDates() {
        if (this.status == TaskStatus.IN_PROGRESS) {
            if (this.expectedStartDateTime == null || this.expectedEndDateTime == null) {
                throw new IllegalArgumentException("Start and end times are required when status is IN_PROGRESS");
            }
        }
    }
}
