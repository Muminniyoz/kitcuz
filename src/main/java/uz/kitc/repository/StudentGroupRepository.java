package uz.kitc.repository;

import uz.kitc.domain.StudentGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StudentGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long>, JpaSpecificationExecutor<StudentGroup> {
}
