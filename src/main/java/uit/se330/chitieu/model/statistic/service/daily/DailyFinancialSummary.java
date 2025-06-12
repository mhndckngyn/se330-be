package uit.se330.chitieu.model.statistic.service.daily;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class DailyFinancialSummary {
    private LocalDate date;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netAmount;

    public DailyFinancialSummary(LocalDate date, BigDecimal totalIncome, BigDecimal totalExpense) {
        this.date = date;
        this.totalIncome = totalIncome != null ? totalIncome : BigDecimal.ZERO;
        this.totalExpense = totalExpense != null ? totalExpense : BigDecimal.ZERO;
        this.netAmount = this.totalIncome.subtract(this.totalExpense);
    }
}