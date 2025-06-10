package uit.se330.chitieu.model.record.income;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.entity.Income;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@Setter
@Getter
public class IncomeCreateDto {
    public String title;
    public String description;
    @NotNull public String amount;
    @NotNull public UUID accountId;
    public UUID categoryId;
    public LocalDate date;

    public Income toEntity() {
        Income income = new Income();

        income.setTitle(this.title);
        income.setDescription(this.description);

        income.setAmount(new BigDecimal(this.amount));

        income.setCreatedat(this.date != null ? this.date.atStartOfDay().toInstant(ZoneOffset.UTC) : Instant.now());

        Account account = new Account();
        account.setId(this.accountId);
        income.setAccountid(account);

        if (this.categoryId != null) {
            Category category = new Category();
            category.setId(this.categoryId);
            income.setCategoryid(category);
        }

        return income;
    }
}
