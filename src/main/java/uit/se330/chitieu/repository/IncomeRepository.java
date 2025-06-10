package uit.se330.chitieu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uit.se330.chitieu.entity.Income;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID>, JpaSpecificationExecutor<Income> {
    Optional<Income> findByIdAndAccountid_Userid_Id(UUID id, UUID accountidUseridId);
}
