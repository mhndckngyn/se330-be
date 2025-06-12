package uit.se330.chitieu.model.statistic.service.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@AllArgsConstructor
public class MonthlyAmountSummary {
    private YearMonth yearMonth;
    private BigDecimal amount;
}
