package uit.se330.chitieu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.se330.chitieu.entity.Budget;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    List<Budget> findByUserid_Id(UUID useridId);
    Optional<Budget> findByIdAndUserid_Id(UUID id, UUID userId);
}
