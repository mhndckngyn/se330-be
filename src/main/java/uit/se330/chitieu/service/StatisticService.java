package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.se330.chitieu.model.statistic.service.daily.DailyFinancialReport;
import uit.se330.chitieu.model.statistic.CategorySpending;
import uit.se330.chitieu.model.statistic.service.daily.DailyAmountSummary;
import uit.se330.chitieu.model.statistic.service.daily.DailyFinancialSummary;
import uit.se330.chitieu.model.statistic.service.monthly.MonthlyAmountSummary;
import uit.se330.chitieu.model.statistic.service.monthly.MonthlyFinancialReport;
import uit.se330.chitieu.model.statistic.service.monthly.MonthlyFinancialSummary;
import uit.se330.chitieu.repository.ExpenseRepository;
import uit.se330.chitieu.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
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

    public DailyFinancialReport getFinancialReport(UUID userId, int numberOfDays) {
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

        return new DailyFinancialReport(summaries, categorySpending);
    }

    public MonthlyFinancialReport getLastTwelveMonthsFinancialReport(UUID userId) {
        // Calculate date range for the last 12 months
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(11).withDayOfMonth(1); // Start of the month 11 months ago

        // Convert to Instant for database queries
        Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        // Get monthly income sums
        List<MonthlyAmountSummary> monthlyIncomes = getMonthlyIncomeSummary(userId, startInstant, endInstant);

        // Get monthly expense sums
        List<MonthlyAmountSummary> monthlyExpenses = getMonthlyExpenseSummary(userId, startInstant, endInstant);

        // Get category spending sums for the year
        List<CategorySpending> categorySpending = expenseRepository.findCategorySpendingByUserAndDateRange(
                userId, startInstant, endInstant);

        // Create maps for quick lookup
        Map<YearMonth, BigDecimal> incomeMap = monthlyIncomes.stream()
                .collect(Collectors.toMap(
                        MonthlyAmountSummary::getYearMonth,
                        MonthlyAmountSummary::getAmount,
                        (existing, _) -> existing
                ));

        Map<YearMonth, BigDecimal> expenseMap = monthlyExpenses.stream()
                .collect(Collectors.toMap(
                        MonthlyAmountSummary::getYearMonth,
                        MonthlyAmountSummary::getAmount,
                        (existing, _) -> existing
                ));

        // Generate monthly summaries for each month in the last 12 months
        List<MonthlyFinancialSummary> summaries = new ArrayList<>();
        YearMonth currentYearMonth = YearMonth.from(startDate);
        YearMonth endYearMonth = YearMonth.from(endDate);

        while (!currentYearMonth.isAfter(endYearMonth)) {
            BigDecimal monthlyIncome = incomeMap.getOrDefault(currentYearMonth, BigDecimal.ZERO);
            BigDecimal monthlyExpense = expenseMap.getOrDefault(currentYearMonth, BigDecimal.ZERO);

            summaries.add(new MonthlyFinancialSummary(currentYearMonth, monthlyIncome, monthlyExpense));
            currentYearMonth = currentYearMonth.plusMonths(1);
        }

        return new MonthlyFinancialReport(summaries, categorySpending);
    }

    public List<MonthlyAmountSummary> getMonthlyIncomeSummary(UUID userId, Instant startDate, Instant endDate) {
        List<Object[]> rawResults = incomeRepository.findMonthlyIncomeRaw(userId, startDate, endDate);
        return streamRawResultsToMonthlyAmountSummary(rawResults);
    }

    public List<MonthlyAmountSummary> getMonthlyExpenseSummary(UUID userId, Instant startDate, Instant endDate) {
        List<Object[]> rawResults = expenseRepository.findMonthlyExpenseRaw(userId, startDate, endDate);
        return streamRawResultsToMonthlyAmountSummary(rawResults);
    }

    private List<MonthlyAmountSummary> streamRawResultsToMonthlyAmountSummary(List<Object[]> rawResults) {
        return rawResults.stream()
                .map(row -> {
                    Instant instant = (Instant) row[0];
                    LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    YearMonth yearMonth = YearMonth.from(date);
                    BigDecimal amount = (BigDecimal) row[1];
                    return new MonthlyAmountSummary(yearMonth, amount);
                })
                .toList();
    }
}
