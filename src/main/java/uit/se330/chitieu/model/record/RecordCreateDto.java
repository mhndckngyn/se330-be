package uit.se330.chitieu.model.record;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@Setter
@Getter
public abstract class RecordCreateDto {
    public String title;
    public String description;
    @NotNull public BigDecimal amount;
    @NotNull public UUID accountId;
    public UUID categoryId;
    public LocalDate date;

    protected void populateCommonFields(Expense expense) {
        expense.setTitle(this.title);
        expense.setDescription(this.description);
        expense.setAmount(this.amount);
        expense.setCreatedat(this.date != null
                ? this.date.atStartOfDay().toInstant(ZoneOffset.UTC)
                : Instant.now());

        Account account = new Account();
        account.setId(this.accountId);
        expense.setAccountid(account);

        if (this.categoryId != null) {
            Category category = new Category();
            category.setId(this.categoryId);
            expense.setCategoryid(category);
        }
    }

    protected void populateCommonFields(Income income) {
        income.setTitle(this.title);
        income.setDescription(this.description);
        income.setAmount(this.amount);
        income.setCreatedat(this.date != null
                ? this.date.atStartOfDay().toInstant(ZoneOffset.UTC)
                : Instant.now());

        Account account = new Account();
        account.setId(this.accountId);
        income.setAccountid(account);

        if (this.categoryId != null) {
            Category category = new Category();
            category.setId(this.categoryId);
            income.setCategoryid(category);
        }
    }

    protected void populateCommonFields(Transfer transfer) {
        transfer.setTitle(this.title);
        transfer.setDescription(this.description);
        transfer.setAmount(this.amount);
        transfer.setCreatedat(this.date != null
                ? this.date.atStartOfDay().toInstant(ZoneOffset.UTC)
                : Instant.now());

        Account account = new Account();
        account.setId(this.accountId);
        transfer.setSourceaccountid(account);

        if (this.categoryId != null) {
            Category category = new Category();
            category.setId(this.categoryId);
            transfer.setCategoryid(category);
        }
    }
}
