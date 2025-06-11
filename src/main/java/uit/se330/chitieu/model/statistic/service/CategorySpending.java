package uit.se330.chitieu.model.statistic.service;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class CategorySpending {
    private UUID id;
    private String name;
    private BigDecimal expense;

    public CategorySpending(UUID id, String name, BigDecimal expense) {
        this.id = id;
        this.name = name;
        this.expense = expense != null ? expense : BigDecimal.ZERO;
    }
}
