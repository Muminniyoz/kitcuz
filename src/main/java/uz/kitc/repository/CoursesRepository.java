package uz.kitc.repository;

import uz.kitc.domain.Courses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Courses entity.
 */
@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long>, JpaSpecificationExecutor<Courses> {

    @Query(value = "select distinct courses from Courses courses left join fetch courses.skills",
        countQuery = "select count(distinct courses) from Courses courses")
    Page<Courses> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct courses from Courses courses left join fetch courses.skills")
    List<Courses> findAllWithEagerRelationships();

    @Query("select courses from Courses courses left join fetch courses.skills where courses.id =:id")
    Optional<Courses> findOneWithEagerRelationships(@Param("id") Long id);
}
