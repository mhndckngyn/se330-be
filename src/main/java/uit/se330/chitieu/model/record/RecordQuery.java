package uit.se330.chitieu.model.record;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class RecordQuery {
    public UUID userId;
    public List<UUID> accountIds;
    public List<UUID> categoryIds;
    public LocalDate startDate;
    public LocalDate endDate;

    public RecordQuery(String userId,
                       List<String> accountIds,
                       List<String> categoryIds,
                       LocalDate startDate,
                       LocalDate endDate)
    {
        this.userId = userId != null ? UUID.fromString(userId) : null;

        this.accountIds = accountIds != null
                ? accountIds.stream().map(UUID::fromString).collect(Collectors.toList())
                : null;

        this.categoryIds = categoryIds != null
                ? categoryIds.stream().map(UUID::fromString).collect(Collectors.toList())
                : null;

        this.startDate = startDate;
        this.endDate = endDate;
    }
}
