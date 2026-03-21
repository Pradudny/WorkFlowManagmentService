package com.company.Incident.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.RoleModuleFeatures;

@Repository
public interface RoleModuleFeatureRepository extends JpaRepository<RoleModuleFeatures, Integer> {

	@Query("SELECT rmf FROM RoleModuleFeatures rmf WHERE rmf.roleId.roleId = :roleId AND rmf.featureModuleId.featureId.featureId = :featureId")
	Optional<RoleModuleFeatures> findByRoleIdAndFeatureId(@Param("roleId") int roleId, @Param("featureId") int featureId);
}
