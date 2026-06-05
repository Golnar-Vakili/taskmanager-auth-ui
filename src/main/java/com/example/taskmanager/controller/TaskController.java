package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    // Hilfsmethode: aktuellen User laden
    private User currentUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername());
    }

    // ---- Task-Liste ----
    @GetMapping
    public String taskList(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam(required = false) String status,
                           Model model) {
        User user = currentUser(userDetails);

        if (status != null && !status.isEmpty()) {
            try {
                Task.Status s = Task.Status.valueOf(status.toUpperCase());
                model.addAttribute("tasks", taskService.findByUserAndStatus(user, s));
                model.addAttribute("activeFilter", status.toUpperCase());
            } catch (IllegalArgumentException e) {
                model.addAttribute("tasks", taskService.findAllByUser(user));
            }
        } else {
            model.addAttribute("tasks", taskService.findAllByUser(user));
            model.addAttribute("activeFilter", "ALL");
        }

        model.addAttribute("statuses", Task.Status.values());
        model.addAttribute("priorities", Task.Priority.values());
        model.addAttribute("newTask", new Task());
        return "tasks/list";
    }

    // ---- Neuen Task erstellen ----
    @PostMapping("/create")
    public String createTask(@AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute Task task,
                             RedirectAttributes redirectAttributes) {
        User user = currentUser(userDetails);
        task.setUser(user);
        taskService.create(task);
        redirectAttributes.addFlashAttribute("success", "Task erstellt!");
        return "redirect:/tasks";
    }

    // ---- Edit-Formular ----
    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal UserDetails userDetails,
                           @PathVariable Long id,
                           Model model) {
        User user = currentUser(userDetails);
        model.addAttribute("task", taskService.findByIdAndUser(id, user));
        model.addAttribute("statuses", Task.Status.values());
        model.addAttribute("priorities", Task.Priority.values());
        return "tasks/edit";
    }

    // ---- Task aktualisieren ----
    @PostMapping("/{id}/edit")
    public String updateTask(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable Long id,
                             @ModelAttribute Task updatedTask,
                             RedirectAttributes redirectAttributes) {
        User user = currentUser(userDetails);
        taskService.update(id, user, updatedTask);
        redirectAttributes.addFlashAttribute("success", "Task aktualisiert!");
        return "redirect:/tasks";
    }

    // ---- Task löschen ----
    @PostMapping("/{id}/delete")
    public String deleteTask(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        User user = currentUser(userDetails);
        taskService.delete(id, user);
        redirectAttributes.addFlashAttribute("success", "Task gelöscht.");
        return "redirect:/tasks";
    }
}
