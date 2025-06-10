package uit.se330.chitieu.model.record;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public abstract class RecordUpdateDto {
    public String title;
    public String description;
    @NotNull public BigDecimal amount;
    @NotNull public UUID accountId;
    public UUID categoryId;
    public LocalDate date;
}
