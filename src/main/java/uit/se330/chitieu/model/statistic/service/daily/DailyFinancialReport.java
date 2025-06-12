package uit.se330.chitieu.model.statistic.service.daily;

import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.model.statistic.CategorySpending;

import java.util.List;

@Setter
@Getter
public class DailyFinancialReport {

    private List<DailyFinancialSummary> dailySummaries;
    private List<CategorySpending> categorySpending;

    public DailyFinancialReport(List<DailyFinancialSummary> dailySummaries,
                                List<CategorySpending> categorySpending)
    {
        this.dailySummaries = dailySummaries;
        this.categorySpending = categorySpending;
    }
}
