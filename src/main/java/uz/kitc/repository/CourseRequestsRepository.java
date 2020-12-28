package uz.kitc.repository;

import uz.kitc.domain.CourseRequests;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CourseRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRequestsRepository extends JpaRepository<CourseRequests, Long>, JpaSpecificationExecutor<CourseRequests> {
}
