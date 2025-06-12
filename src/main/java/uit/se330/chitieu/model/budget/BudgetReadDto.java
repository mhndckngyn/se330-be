package uit.se330.chitieu.model.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import uit.se330.chitieu.entity.Budget;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BudgetReadDto {
    public UUID id;
    public String name;
    public LocalDate startDate;
    public LocalDate endDate;
    public BigDecimal current;
    public BigDecimal budgetLimit;
    public Integer period;
    public UUID categoryId;

    public BudgetReadDto(Budget budget) {
        this.id = budget.getId();
        this.name = budget.getName();
        this.startDate = budget.getStartdate();
        this.endDate = budget.getEnddate();
        this.current = budget.getCurrent();
        this.budgetLimit = budget.getBudgetlimit();
        this.period = budget.getPeriod();
        if (budget.getCategoryid() != null) {
            this.categoryId = budget.getCategoryid().getId();
        }
    }
}
