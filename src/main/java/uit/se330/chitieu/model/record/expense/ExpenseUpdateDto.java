package uit.se330.chitieu.model.record.expense;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class ExpenseUpdateDto {
    public String title;
    public String description;
    @NotNull public String amount;
    @NotNull public UUID accountId;
    public UUID categoryId;
    public LocalDate date;
}
