package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Income;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.income.IncomeCreateDto;
import uit.se330.chitieu.model.record.income.IncomeUpdateDto;
import uit.se330.chitieu.service.IncomeService;
import uit.se330.chitieu.util.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncome(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        Income income = incomeService.getIncome(userId, id);

        if (income == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(income);
    }

    @GetMapping
    public ResponseEntity<List<Income>> getIncomesWithParams(
            @RequestParam(required = false) List<String> accountIds,
            @RequestParam(required = false) List<String> categoryIds,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        String userId = SecurityUtil.getCurrentUserId();

        RecordQuery query = new RecordQuery(userId, accountIds, categoryIds, startDate, endDate);
        List<Income> incomes = incomeService.getIncomesWithQuery(query);

        return ResponseEntity.ok(incomes);
    }

    @PostMapping
    public ResponseEntity<Income> createExpense(@RequestBody IncomeCreateDto dto) {
        String userId = SecurityUtil.getCurrentUserId();
        Income income = incomeService.createIncome(userId, dto);

        if (income == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(income);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(
            @PathVariable("id") String id,
            @RequestBody IncomeUpdateDto dto)
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
