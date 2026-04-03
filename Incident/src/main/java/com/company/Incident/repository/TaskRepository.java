package com.company.Incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByIncidentIncidentId(int incidentId);
}
