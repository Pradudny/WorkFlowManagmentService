package com.company.Incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	@Query("SELECT t FROM Team t WHERE t.roleId.moduleId.module_id = :moduleId")
	List<Team> findByModuleId(@Param("moduleId") int moduleId);
}
