package uit.se330.chitieu.model.statistic.service;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class DailyAmountSummary {
    private LocalDate date;
    private BigDecimal amount;

    public DailyAmountSummary(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }
}
