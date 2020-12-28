package uz.kitc.repository;

import uz.kitc.domain.Planning;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Planning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long>, JpaSpecificationExecutor<Planning> {
}
