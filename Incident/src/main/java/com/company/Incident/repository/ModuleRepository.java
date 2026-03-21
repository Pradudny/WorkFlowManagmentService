package com.company.Incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

}
