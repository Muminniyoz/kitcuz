package uz.kitc.repository;

import uz.kitc.domain.Galereya;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Galereya entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GalereyaRepository extends JpaRepository<Galereya, Long>, JpaSpecificationExecutor<Galereya> {
}
