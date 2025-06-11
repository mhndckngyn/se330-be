package uit.se330.chitieu.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Income;
import uit.se330.chitieu.model.record.RecordParams;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.income.IncomeCreateDto;
import uit.se330.chitieu.model.record.income.IncomeReadDto;
import uit.se330.chitieu.model.record.income.IncomeUpdateDto;
import uit.se330.chitieu.service.IncomeService;
import uit.se330.chitieu.util.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/{id}")
    public ResponseEntity<IncomeReadDto> getIncome(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        IncomeReadDto result = incomeService.getIncome(userId, id);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<IncomeReadDto>> getIncomesWithParams(RecordParams params)
    {
        String userId = SecurityUtil.getCurrentUserId();

        RecordQuery query = new RecordQuery(params, userId);
        List<IncomeReadDto> incomes = incomeService.getIncomesWithQuery(query);

        return ResponseEntity.ok(incomes);
    }

    @PostMapping
    public ResponseEntity<IncomeReadDto> createIncome(@Valid @RequestBody IncomeCreateDto dto) {
        String userId = SecurityUtil.getCurrentUserId();
        Income income = incomeService.createIncome(userId, dto);

        if (income == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new IncomeReadDto(income));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(
            @PathVariable("id") String id,
            @Valid @RequestBody IncomeUpdateDto dto)
    {
        String userId = SecurityUtil.getCurrentUserId();
        Income income = incomeService.updateIncome(userId, id, dto);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(income);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        boolean deleted = incomeService.deleteIncome(userId, id);
        if (!deleted) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
    }
}
