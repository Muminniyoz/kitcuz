package uz.kitc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.kitc.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
