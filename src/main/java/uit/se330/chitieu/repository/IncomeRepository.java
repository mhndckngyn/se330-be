package uit.se330.chitieu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.se330.chitieu.entity.Income;

import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {

}
