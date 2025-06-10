package uit.se330.chitieu.model.record.transfer;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uit.se330.chitieu.model.record.RecordUpdateDto;

import java.util.UUID;

@Getter
@Setter
public class TransferUpdateDto extends RecordUpdateDto {
    @NotNull public UUID targetAccountId;
}
