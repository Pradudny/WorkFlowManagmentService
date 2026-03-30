package com.company.Incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.AuthorizationRules;

@Repository
public interface AuthorizationRulesRepo extends JpaRepository<AuthorizationRules, Integer> {

    List<AuthorizationRules> findByActionAndResource(String action, String resource);
}
