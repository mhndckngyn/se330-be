package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.se330.chitieu.model.statistic.StatisticReadDto;
import uit.se330.chitieu.model.statistic.service.CategorySpending;
import uit.se330.chitieu.model.statistic.service.DailyAmountSummary;
import uit.se330.chitieu.model.statistic.service.DailyFinancialSummary;
import uit.se330.chitieu.repository.ExpenseRepository;
import uit.se330.chitieu.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StatisticService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    public StatisticReadDto getFinancialReport(UUID userId, int numberOfDays) {
        // Calculate date range
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(numberOfDays - 1);

        // Convert to Instant for database queries (start of day and end of day)
        Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        // Get daily income sums
        List<DailyAmountSummary> dailyIncomes = incomeRepository.findDailyIncomeByUserAndDateRange(
                userId, startInstant, endInstant);

        // Get daily expense sums
        List<DailyAmountSummary> dailyExpenses = expenseRepository.findDailyExpenseByUserAndDateRange(
                userId, startInstant, endInstant);

        // Get category spending sums
        List<CategorySpending> categorySpending = expenseRepository.findCategorySpendingByUserAndDateRange(
                userId, startInstant, endInstant);

        // Create a map for quick lookup
        Map<LocalDate, BigDecimal> incomeMap = dailyIncomes.stream()
                .collect(Collectors.toMap(
                        DailyAmountSummary::getDate,
                        DailyAmountSummary::getAmount,
                        (existing, _) -> existing
                ));

        Map<LocalDate, BigDecimal> expenseMap = dailyExpenses.stream()
                .collect(Collectors.toMap(
                        DailyAmountSummary::getDate,
                        DailyAmountSummary::getAmount,
                        (existing, _) -> existing
                ));

        // Generate daily summaries for each day in the range
        List<DailyFinancialSummary> summaries = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            BigDecimal dailyIncome = incomeMap.getOrDefault(date, BigDecimal.ZERO);
            BigDecimal dailyExpense = expenseMap.getOrDefault(date, BigDecimal.ZERO);

            summaries.add(new DailyFinancialSummary(date, dailyIncome, dailyExpense));
        }

        return new StatisticReadDto(summaries, categorySpending);
    }
}
