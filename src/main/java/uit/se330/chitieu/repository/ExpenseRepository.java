package uit.se330.chitieu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uit.se330.chitieu.entity.Expense;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID>, JpaSpecificationExecutor<Expense> {
    Optional<Expense> findByIdAndAccountid_Userid_Id(UUID id, UUID accountidUseridId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.accountid.id = :accountId")
    BigDecimal sumExpenseByAccountId(@Param("accountId") UUID accountId);
}
