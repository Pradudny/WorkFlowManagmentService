package com.company.Incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query("SELECT r FROM Role r WHERE r.moduleId.module_id = :moduleId")
	List<Role> findByModuleId(@Param("moduleId") int moduleId);
}
