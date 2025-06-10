package uit.se330.chitieu.model.record.expense;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.entity.Expense;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@Getter
@Setter
public class ExpenseCreateDto {
    public String title;
    public String description;
    @NotNull public String amount;
    @NotNull public UUID accountId;
    public UUID categoryId;
    public LocalDate date;

    public Expense toEntity() {
        Expense expense = new Expense();

        expense.setTitle(this.title);
        expense.setDescription(this.description);

        expense.setAmount(new BigDecimal(this.amount));

        expense.setCreatedat(this.date != null ? this.date.atStartOfDay().toInstant(ZoneOffset.UTC) : Instant.now());

        Account account = new Account();
        account.setId(this.accountId);
        expense.setAccountid(account);

        if (this.categoryId != null) {
            Category category = new Category();
            category.setId(this.categoryId);
            expense.setCategoryid(category);
        }

        return expense;
    }
}
