package com.company.Incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.entity.Task;
import com.company.Incident.payload.TaskDto;
import com.company.Incident.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/add-task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/update-task")
    public ResponseEntity<TaskDto> updateTask(@RequestParam(value = "taskId") int taskId,
            @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskId, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<Void> deleteTask(@RequestParam(value = "taskId") int taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/task-list")
    public ResponseEntity<List<Task>> getAllTaskByIncidentId(@RequestParam(value = "incidentId") int incidentId) {
        List<Task> tasks = taskService.getAllTaskByIncidentId(incidentId);
        if (tasks == null || tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task-details")
    public ResponseEntity<Task> taskDetails(@RequestParam(value = "taskId") int taskId) {
        Task task = taskService.taskDetails(taskId);
        return ResponseEntity.ok(task);
    }
}
