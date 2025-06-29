package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Budget;
import uit.se330.chitieu.model.budget.*;
import uit.se330.chitieu.service.BudgetService;
import uit.se330.chitieu.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/{id}")
    public ResponseEntity<BudgetReadDto> getBudgetById(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        BudgetReadDto budget = budgetService.getBudget(userId, id);

        if (budget == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(budget);
    }

    @GetMapping
    public ResponseEntity<List<BudgetReadDto>> getBudgets(@ModelAttribute BudgetParams params) {
        String userId = SecurityUtil.getCurrentUserId();
        BudgetQuery query = new BudgetQuery(params, userId);
        List<BudgetReadDto> budgets = budgetService.getBudgetsWithQuery(query);

        return ResponseEntity.ok(budgets);
    }

    @PostMapping
    public ResponseEntity<BudgetReadDto> createBudget(@RequestBody BudgetCreateDto dto) {
        String userId = SecurityUtil.getCurrentUserId();
        Budget budget = budgetService.createBudget(userId, dto);
        return ResponseEntity.ok(new BudgetReadDto(budget));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable("id") String id, @RequestBody BudgetUpdateDto dto) {
        String userId = SecurityUtil.getCurrentUserId();
        Budget updated = budgetService.updateBudget(userId, id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Budget> deleteBudget(@PathVariable("id") String id) {
        String userId = SecurityUtil.getCurrentUserId();
        boolean deleted = budgetService.deleteBudget(userId, id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.internalServerError().build();
    }
}
