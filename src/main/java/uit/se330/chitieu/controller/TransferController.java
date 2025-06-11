package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Transfer;
import uit.se330.chitieu.model.record.RecordParams;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.transfer.TransferCreateDto;
import uit.se330.chitieu.model.record.transfer.TransferReadDto;
import uit.se330.chitieu.model.record.transfer.TransferUpdateDto;
import uit.se330.chitieu.service.TransferService;
import uit.se330.chitieu.util.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping("/{id}")
    public ResponseEntity<TransferReadDto> getTransfer(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();

        TransferReadDto result = transferService.getTransfer(userId, id);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<TransferReadDto>> getTransfersWithParams(RecordParams params) {
        String userId = SecurityUtil.getCurrentUserId();

        RecordQuery query = new RecordQuery(params, userId);
        List<TransferReadDto> transfers = transferService.getTransfersWithQuery(query);

        return ResponseEntity.ok(transfers);
    }

    @PostMapping
    public ResponseEntity<TransferReadDto> createTransfer(@RequestBody TransferCreateDto dto) {
        String userId = SecurityUtil.getCurrentUserId();
        Transfer transfer = transferService.createTransfer(userId, dto);

        if (transfer == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new TransferReadDto(transfer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transfer> updateTransfer(
            @PathVariable("id") String id,
            @RequestBody TransferUpdateDto dto
    ) {
        String userId = SecurityUtil.getCurrentUserId();

        Transfer transfer = transferService.updateTransfer(userId, id, dto);
        if (transfer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transfer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransfer(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        boolean deleted = transferService.deleteTransfer(userId, id);

        if (!deleted) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
    }
}
