package uit.se330.chitieu.model.statistic.service;

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

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
        updateNetAmount();
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
        updateNetAmount();
    }

    private void updateNetAmount() {
        this.netAmount = (totalIncome != null ? totalIncome : BigDecimal.ZERO)
                .subtract(totalExpense != null ? totalExpense : BigDecimal.ZERO);
    }
}