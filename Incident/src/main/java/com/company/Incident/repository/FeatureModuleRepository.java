package com.company.Incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.FeatureModule;

@Repository
public interface FeatureModuleRepository extends JpaRepository<FeatureModule, Integer> {

	@Query("SELECT fm FROM FeatureModule fm WHERE fm.moduleId.module_id = :moduleId")
	List<FeatureModule> findByModuleId(@Param("moduleId") int moduleId);
}
