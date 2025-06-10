package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.entity.Transfer;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.transfer.TransferCreateDto;
import uit.se330.chitieu.model.record.transfer.TransferUpdateDto;
import uit.se330.chitieu.repository.TransferRepository;
import uit.se330.chitieu.repository.specifications.TransferSpecifications;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountService accountService;

    public Transfer createTransfer(String userId, TransferCreateDto dto) {
        Account sourceAccount = accountService.getAccountByIdAndUserId(
                dto.getAccountId().toString(),
                userId);

        Account targetAccount = accountService.getAccountByIdAndUserId(
                dto.getTargetAccountId().toString(),
                userId);

        if (sourceAccount == null || targetAccount == null) {
            return null;
        }

        Transfer transfer = dto.toEntity();
        return transferRepository.save(transfer);
    }

    public Transfer getTransfer(String userId, String transferId) {
        // TODO: use ReadDto
        return getTransferByIdAndUserId(transferId, userId);
    }

    public List<Transfer> getTransfersWithQuery(RecordQuery query) {
        return transferRepository.findAll(TransferSpecifications.withFilters(query));
    }

    public Transfer updateTransfer(String userId, String transferId, TransferUpdateDto dto) {
        Transfer transfer = getTransferByIdAndUserId(transferId, userId);
        if (transfer == null) {
            return null;
        }

        transfer.setTitle(dto.getTitle());
        transfer.setDescription(dto.getDescription());
        transfer.setAmount(dto.getAmount());

        Account sourceAccount = new Account();
        sourceAccount.setId(dto.getAccountId());
        transfer.setSourceaccountid(sourceAccount);

        Account targetAccount = new Account();
        targetAccount.setId(dto.getTargetAccountId());
        transfer.setTargetaccountid(targetAccount);

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            transfer.setCategoryid(category);
        } else {
            transfer.setCategoryid(null);
        }

        return transferRepository.save(transfer);
    }

    public boolean deleteTransfer(String userId, String transferId) {
        Transfer transfer = getTransferByIdAndUserId(transferId, userId);
        if (transfer == null) {
            return false;
        }
        transferRepository.delete(transfer);
        return true;
    }

    private Transfer getTransferByIdAndUserId(String transferId, String userId) {
        UUID transferUUID = UUID.fromString(transferId);
        UUID userUUID = UUID.fromString(userId);

        Optional<Transfer> transfer = transferRepository.findByIdAndUserId(transferUUID, userUUID);

        return transfer.orElse(null);
    }
}
