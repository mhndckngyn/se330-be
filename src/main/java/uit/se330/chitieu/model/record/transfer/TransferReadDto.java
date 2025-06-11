package uit.se330.chitieu.model.record.transfer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.Transfer;
import uit.se330.chitieu.model.record.RecordReadDto;

import java.util.UUID;

@Setter
@Getter
public class TransferReadDto extends RecordReadDto {
    public UUID targetAccountId;
    public String targetAccountName;

    public TransferReadDto(Transfer transfer) {
        this.id = transfer.getId();
        this.title = transfer.getTitle();
        this.description = transfer.getDescription();
        this.amount = transfer.getAmount();
        this.createdAt = transfer.getCreatedat();
        this.updatedAt = transfer.getUpdatedat();
        this.accountId = transfer.getSourceaccountid().getId();
        this.accountName = transfer.getSourceaccountid().getName();
        this.targetAccountId = transfer.getTargetaccountid().getId();
        this.targetAccountName = transfer.getTargetaccountid().getName();
        this.categoryId = transfer.getCategoryid().getId();
        this.categoryName = transfer.getCategoryid().getName();
    }
}
