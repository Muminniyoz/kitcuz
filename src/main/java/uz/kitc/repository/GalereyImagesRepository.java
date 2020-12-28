package uz.kitc.repository;

import uz.kitc.domain.GalereyImages;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GalereyImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GalereyImagesRepository extends JpaRepository<GalereyImages, Long>, JpaSpecificationExecutor<GalereyImages> {
}
