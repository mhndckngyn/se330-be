package uit.se330.chitieu.model.budget;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.Budget;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class BudgetCreateDto {
    @NotNull public String name;
    @NotNull public LocalDate startDate;
    @NotNull public Integer period;
    @NotNull BigDecimal budgetLimit;
    public UUID categoryId;

    public Budget toEntity(UUID userId) {
        Budget budget = new Budget();
        budget.setName(this.name);
        budget.setStartdate(this.startDate);
        budget.setPeriod(this.period);
        budget.setEnddate(this.startDate.plusDays(this.period));
        budget.setBudgetlimit(this.budgetLimit);

        User user = new User();
        user.setId(userId);
        budget.setUserid(user);

        if (this.categoryId != null) {
            Category category = new Category();
            category.setId(this.categoryId);
            budget.setCategoryid(category);
        }

        return budget;
    }
}
