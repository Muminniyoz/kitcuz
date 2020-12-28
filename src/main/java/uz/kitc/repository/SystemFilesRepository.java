package uz.kitc.repository;

import uz.kitc.domain.SystemFiles;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SystemFiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemFilesRepository extends JpaRepository<SystemFiles, Long>, JpaSpecificationExecutor<SystemFiles> {
}
