package uit.se330.chitieu.model.record.income;

import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.Income;
import uit.se330.chitieu.model.record.RecordCreateDto;

public class IncomeCreateDto extends RecordCreateDto {
    public Income toEntity() {
        Income income = new Income();
        super.populateCommonFields(income);
        return income;
    }
}
