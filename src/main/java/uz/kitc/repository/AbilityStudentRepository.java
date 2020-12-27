package uz.kitc.repository;

import uz.kitc.domain.AbilityStudent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AbilityStudent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbilityStudentRepository extends JpaRepository<AbilityStudent, Long>, JpaSpecificationExecutor<AbilityStudent> {
}
