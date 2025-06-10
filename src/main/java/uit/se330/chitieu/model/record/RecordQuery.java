package uit.se330.chitieu.model.record;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecordQuery extends RecordParams {
    public UUID userId;

    public RecordQuery(RecordParams params, String userId) {
        this.setAccountIds(params.getAccountIds());
        this.setCategoryIds(params.getCategoryIds());
        this.setStartDate(params.getStartDate());
        this.setEndDate(params.getEndDate());

        UUID userUUID = UUID.fromString(userId);
        this.setUserId(userUUID);
    }
}
