package com.company.Incident.service;

import java.util.List;

import com.company.Incident.entity.Task;
import com.company.Incident.payload.TaskDto;

public interface TaskService {

    public TaskDto createTask(TaskDto taskDTO);

    public TaskDto updateTask(int taskId, TaskDto taskDTO);

    public void deleteTask(int taskId);

    public List<Task> getAllTaskByIncidentId(int incidentId);

    public Task taskDetails(int taskId);
}
