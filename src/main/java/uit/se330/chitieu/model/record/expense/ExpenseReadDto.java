package uit.se330.chitieu.model.record.expense;

import uit.se330.chitieu.entity.Expense;
import uit.se330.chitieu.model.record.RecordReadDto;

public class ExpenseReadDto extends RecordReadDto {
    public ExpenseReadDto(Expense expense) {
        this.id = expense.getId();
        this.title = expense.getTitle();
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.createdAt = expense.getCreatedat();
        this.updatedAt = expense.getUpdatedat();
        this.accountId = expense.getAccountid().getId();
        this.accountName = expense.getAccountid().getName();

        var categoryId = expense.getCategoryid();
        if (categoryId != null) {
            this.categoryId = categoryId.getId();
            this.categoryName = categoryId.getName();
        }
    }
}
