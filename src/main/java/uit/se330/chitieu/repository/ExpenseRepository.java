package uit.se330.chitieu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uit.se330.chitieu.entity.Expense;
import uit.se330.chitieu.model.statistic.service.CategorySpending;
import uit.se330.chitieu.model.statistic.service.DailyAmountSummary;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID>, JpaSpecificationExecutor<Expense> {
    Optional<Expense> findByIdAndAccountid_Userid_Id(UUID id, UUID accountidUseridId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.accountid.id = :accountId")
    BigDecimal sumExpenseByAccountId(@Param("accountId") UUID accountId);

    @Query("SELECT new uit.se330.chitieu.model.statistic.service.DailyAmountSummary(" +
            "CAST(e.createdat AS LocalDate), SUM(e.amount)) " +
            "FROM Expense e " +
            "JOIN e.accountid a " +
            "WHERE a.userid.id = :userId " +
            "AND e.createdat >= :startDate " +
            "AND e.createdat < :endDate " +
            "GROUP BY CAST(e.createdat AS LocalDate) " +
            "ORDER BY CAST(e.createdat AS LocalDate)")
    List<DailyAmountSummary> findDailyExpenseByUserAndDateRange(
            @Param("userId") UUID userId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );

    @Query("SELECT new uit.se330.chitieu.model.statistic.service.CategorySpending(" +
            "c.id, c.name, SUM(e.amount)) " +
            "FROM Expense e " +
            "JOIN e.accountid a " +
            "LEFT JOIN e.categoryid c " +
            "WHERE a.userid.id = :userId " +
            "AND e.createdat >= :startDate " +
            "AND e.createdat < :endDate " +
            "GROUP BY c.id, c.name " +
            "ORDER BY SUM(e.amount) DESC")
    List<CategorySpending> findCategorySpendingByUserAndDateRange(
            @Param("userId") UUID userId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );
}
