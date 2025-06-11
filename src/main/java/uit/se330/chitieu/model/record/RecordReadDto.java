package uit.se330.chitieu.model.record;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
public class RecordReadDto {
    public UUID id;
    public String title;
    public String description;
    public BigDecimal amount;
    public Instant createdAt;
    public Instant updatedAt;
    public UUID accountId;
    public String accountName;
    public UUID categoryId;
    public String categoryName;
}
