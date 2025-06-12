package uit.se330.chitieu.model.statistic.service.monthly;

import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.model.statistic.CategorySpending;

import java.util.List;

@Setter
@Getter
public class MonthlyFinancialReport {
    private List<MonthlyFinancialSummary> monthlySummaries;
    private List<CategorySpending> categorySpending;

    public MonthlyFinancialReport(List<MonthlyFinancialSummary> monthlySummaries, List<CategorySpending> categorySpending) {
        this.monthlySummaries = monthlySummaries;
        this.categorySpending = categorySpending;
    }
}
