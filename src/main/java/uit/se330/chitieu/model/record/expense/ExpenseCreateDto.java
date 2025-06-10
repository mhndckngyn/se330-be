package uit.se330.chitieu.model.record.expense;

import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.Expense;
import uit.se330.chitieu.model.record.RecordCreateDto;

public class ExpenseCreateDto extends RecordCreateDto {
    public Expense toEntity() {
        Expense expense = new Expense();
        super.populateCommonFields(expense);
        return expense;
    }
}
