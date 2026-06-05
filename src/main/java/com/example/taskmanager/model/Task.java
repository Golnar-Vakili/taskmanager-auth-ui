package com.example.taskmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    public enum Status   { OPEN, IN_PROGRESS, DONE }
    public enum Priority { LOW, MEDIUM, HIGH }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.MEDIUM;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ---- Konstruktoren ----
    public Task() {}

    public Task(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    // ---- Getter & Setter ----
    public Long getId()                        { return id; }
    public String getTitle()                   { return title; }
    public void setTitle(String title)         { this.title = title; }
    public String getDescription()             { return description; }
    public void setDescription(String desc)    { this.description = desc; }
    public Status getStatus()                  { return status; }
    public void setStatus(Status status)       { this.status = status; }
    public Priority getPriority()              { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDate getDueDate()              { return dueDate; }
    public void setDueDate(LocalDate dueDate)  { this.dueDate = dueDate; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }
    public User getUser()                      { return user; }
    public void setUser(User user)             { this.user = user; }
}
