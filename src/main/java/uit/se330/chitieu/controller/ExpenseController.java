package uit.se330.chitieu.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Expense;
import uit.se330.chitieu.model.record.RecordParams;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.expense.ExpenseCreateDto;
import uit.se330.chitieu.model.record.expense.ExpenseReadDto;
import uit.se330.chitieu.model.record.expense.ExpenseUpdateDto;
import uit.se330.chitieu.service.ExpenseService;
import uit.se330.chitieu.util.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseReadDto> getExpense(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        ExpenseReadDto result = expenseService.getExpense(userId, id);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseReadDto>> getExpensesWithParams(RecordParams params)
    {
        String userId = SecurityUtil.getCurrentUserId();

        RecordQuery query = new RecordQuery(params, userId);
        List<ExpenseReadDto> expenses = expenseService.getExpensesWithQuery(query);

        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<ExpenseReadDto> createExpense(@Valid @RequestBody ExpenseCreateDto dto) {
        String userId = SecurityUtil.getCurrentUserId();
        Expense expense = expenseService.createExpense(userId, dto);

        if (expense == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new ExpenseReadDto(expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable("id") String id,
            @Valid @RequestBody ExpenseUpdateDto dto)
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
