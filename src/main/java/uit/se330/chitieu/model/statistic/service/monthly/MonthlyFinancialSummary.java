package uit.se330.chitieu.model.statistic.service.monthly;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
public class MonthlyFinancialSummary {
    private YearMonth yearMonth;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netAmount; // income - expense

    public MonthlyFinancialSummary(YearMonth yearMonth, BigDecimal totalIncome, BigDecimal totalExpense) {
        this.yearMonth = yearMonth;
        this.totalIncome = totalIncome != null ? totalIncome : BigDecimal.ZERO;
        this.totalExpense = totalExpense != null ? totalExpense : BigDecimal.ZERO;
        this.netAmount = this.totalIncome.subtract(this.totalExpense);
    }
}
