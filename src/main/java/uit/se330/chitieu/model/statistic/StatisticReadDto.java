package uit.se330.chitieu.model.statistic;

import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.model.statistic.service.CategorySpending;
import uit.se330.chitieu.model.statistic.service.DailyFinancialSummary;

import java.util.List;

@Setter
@Getter
public class StatisticReadDto {

    private List<DailyFinancialSummary> dailySummaries;
    private List<CategorySpending> categorySpending;

    public StatisticReadDto(List<DailyFinancialSummary> dailySummaries,
                           List<CategorySpending> categorySpending)
    {
        this.dailySummaries = dailySummaries;
        this.categorySpending = categorySpending;
    }
}
