package uit.se330.chitieu.model.record.income;

import uit.se330.chitieu.entity.Income;
import uit.se330.chitieu.model.record.RecordReadDto;

public class IncomeReadDto extends RecordReadDto {
    public IncomeReadDto(Income income) {
        this.id = income.getId();
        this.title = income.getTitle();
        this.description = income.getDescription();
        this.amount = income.getAmount();
        this.createdAt = income.getCreatedat();
        this.updatedAt = income.getUpdatedat();
        this.accountId = income.getAccountid().getId();
        this.accountName = income.getAccountid().getName();
        this.categoryId = income.getCategoryid().getId();
        this.categoryName = income.getCategoryid().getName();
    }
}
