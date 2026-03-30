package com.company.Incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.Incident.entity.ModuleFeature;

@Repository
public interface ModuleFeatureRepository extends JpaRepository<ModuleFeature, Integer> {

	@Query("SELECT fm FROM ModuleFeature fm WHERE fm.moduleId.module_id =:moduleId")
	List<ModuleFeature> findByModuleId(@Param("moduleId") int moduleId);

	@Query(value = """
			    SELECT mf.*
			    FROM users u
			    JOIN team_members tm ON u.user_id = tm.user_id
			    JOIN teams t ON tm.team_id = t.team_id
			    JOIN roles r ON t.role_id = r.role_id
			    JOIN role_module_features rmf ON r.role_id = rmf.role_id
			    JOIN module_features mf ON rmf.feature_module_id = mf.module_features_id
			    WHERE u.email = :email
			""", nativeQuery = true)
	List<ModuleFeature> findByUserEmailId(@Param("email") String email);
}
