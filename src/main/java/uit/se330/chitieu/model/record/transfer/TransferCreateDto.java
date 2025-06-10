package uit.se330.chitieu.model.record.transfer;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.Transfer;
import uit.se330.chitieu.model.record.RecordCreateDto;

import java.util.UUID;

@Getter
@Setter
public class TransferCreateDto extends RecordCreateDto {
    @NotNull public UUID targetAccountId;

    public Transfer toEntity() {
        Transfer transfer = new Transfer();
        super.populateCommonFields(transfer);

        Account targetAccount = new Account();
        targetAccount.setId(targetAccountId);
        transfer.setTargetaccountid(targetAccount);

        return transfer;
    }
}
