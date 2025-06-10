package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Expense;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.expense.ExpenseCreateDto;
import uit.se330.chitieu.model.record.expense.ExpenseUpdateDto;
import uit.se330.chitieu.service.ExpenseService;
import uit.se330.chitieu.util.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        Expense expense = expenseService.getExpense(userId, id);

        if (expense == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpensesWithParams(
            @RequestParam(required = false) List<String> accountIds,
            @RequestParam(required = false) List<String> categoryIds,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        String userId = SecurityUtil.getCurrentUserId();

        RecordQuery query = new RecordQuery(userId, accountIds, categoryIds, startDate, endDate);
        List<Expense> expenses = expenseService.getExpensesWithQuery(query);

        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseCreateDto dto) {
        String userId = SecurityUtil.getCurrentUserId();
        Expense expense = expenseService.createExpense(userId, dto);

        if (expense == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable("id") String id,
            @RequestBody ExpenseUpdateDto dto)
    {
        String userId = SecurityUtil.getCurrentUserId();
        Expense expense = expenseService.updateExpense(userId, id, dto);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        boolean deleted = expenseService.deleteExpense(userId, id);
        if (!deleted) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
    }
}
