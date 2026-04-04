package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.Incident;
import com.company.Incident.entity.Task;
import com.company.Incident.enums.Status;
import com.company.Incident.exception.ResourceNotFoundException;
import com.company.Incident.payload.TaskDto;
import com.company.Incident.repository.IncidentRepository;
import com.company.Incident.repository.TaskRepository;
import com.company.Incident.service.TaskService;
import com.company.Incident.util.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Override
    public TaskDto createTask(TaskDto taskDTO) {
        Incident incident = incidentRepository.findById(taskDTO.getIncidentId())
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found: " + taskDTO.getIncidentId()));

        Task task = new Task();
        task.setIncident(incident);
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDesc(taskDTO.getTaskDesc());
        task.setAssignedTo(taskDTO.getAssignedTo());
        task.setStatus(Status.OPEN);
        String currentUser = UserUtil.getCurrentUserEmail();
        task.setCreatedBy(currentUser);
        task.setCreatedDate(LocalDate.now());

        taskRepository.save(task);
        return taskDTO;
    }

    @Override
    public TaskDto updateTask(int taskId, TaskDto taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDesc(taskDTO.getTaskDesc());
        task.setAssignedTo(taskDTO.getAssignedTo());
        if (taskDTO.getStatus() != null && !taskDTO.getStatus().isBlank()) {
            task.setStatus(Status.valueOf(taskDTO.getStatus().trim()));
        }
        task.setModifiedBy(UserUtil.getCurrentUserEmail());
        task.setModifiedDate(LocalDate.now());
        taskRepository.save(task);

        return taskDTO;
    }

    @Override
    public void deleteTask(int taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            taskRepository.delete(task);
        } else {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
    }

    @Override
    public List<Task> getAllTaskByIncidentId(int incidentId) {

        List<Task> tasks = taskRepository.findByIncidentIncidentId(incidentId);

        if (tasks == null || tasks.isEmpty()) {
            log.info("No tasks found for incident id: {}", incidentId);
            // throw new ResourceNotFoundException("No tasks found for incident id: " +
            // incidentId);
        }

        return tasks;
    }

    @Override
    public Task taskDetails(int taskId) {

        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with id: " + taskId));
        return task;
    }

}
