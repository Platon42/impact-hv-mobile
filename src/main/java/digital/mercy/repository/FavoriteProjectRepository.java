package digital.mercy.repository;

import digital.mercy.domain.FavoriteProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FavoriteProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoriteProjectRepository extends JpaRepository<FavoriteProject, Long> {

}
