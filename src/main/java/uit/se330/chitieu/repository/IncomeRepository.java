package uit.se330.chitieu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uit.se330.chitieu.entity.Income;
import uit.se330.chitieu.model.statistic.service.daily.DailyAmountSummary;
import uit.se330.chitieu.model.statistic.service.monthly.MonthlyAmountSummary;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID>, JpaSpecificationExecutor<Income> {
    Optional<Income> findByIdAndAccountid_Userid_Id(UUID id, UUID accountidUseridId);

    @Query("SELECT COALESCE(SUM(i.amount), 0) " +
            "FROM Income i " +
            "WHERE i.accountid.id = :accountId")
    BigDecimal sumIncomeByAccountId(@Param("accountId") UUID accountId);

    @Query("SELECT new uit.se330.chitieu.model.statistic.service.daily.DailyAmountSummary(" +
            "CAST(i.createdat AS LocalDate), SUM(i.amount)) " +
            "FROM Income i " +
            "JOIN i.accountid a " +
            "WHERE a.userid.id = :userId " +
            "AND i.createdat >= :startDate " +
            "AND i.createdat < :endDate " +
            "GROUP BY CAST(i.createdat AS LocalDate) " +
            "ORDER BY CAST(i.createdat AS LocalDate)")
    List<DailyAmountSummary> findDailyIncomeByUserAndDateRange(
            @Param("userId") UUID userId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );

    @Query(value = """
    SELECT DATE_TRUNC('month', i.createdat) AS month,
           SUM(i.amount) AS total
    FROM Income i
    JOIN Account a ON i.accountid = a.id
    WHERE a.userid = :userId
      AND i.createdat >= :startDate
      AND i.createdat < :endDate
    GROUP BY DATE_TRUNC('month', i.createdat)
    ORDER BY DATE_TRUNC('month', i.createdat)
    """, nativeQuery = true)
    List<Object[]> findMonthlyIncomeRaw(
            @Param("userId") UUID userId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );
}
