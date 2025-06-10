package uit.se330.chitieu.model.budget;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class BudgetUpdateDto {
    public String name;
    public LocalDate startDate;
    public Integer period;
    public BigDecimal limit;
    public UUID categoryId;
}
