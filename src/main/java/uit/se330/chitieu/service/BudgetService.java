package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.entity.Budget;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.model.budget.BudgetCreateDto;
import uit.se330.chitieu.model.budget.BudgetReadDto;
import uit.se330.chitieu.model.budget.BudgetUpdateDto;
import uit.se330.chitieu.repository.BudgetRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget createBudget(String userId, BudgetCreateDto budgetCreateDto) {
        UUID userUUID = UUID.fromString(userId);
        return budgetRepository.save(budgetCreateDto.toEntity(userUUID));
    }

    public BudgetReadDto getBudget(String userId, String budgetId) {
        Budget budget = getBudgetByIdAndUserId(budgetId, userId);

        if (budget == null) {
            return null;
        }

        return new BudgetReadDto(budget);
    }

    public List<BudgetReadDto> getBudgetsByUserId(String userId) {
        UUID userUUID = UUID.fromString(userId);
        List<Budget> budgets = budgetRepository.findByUserid_Id(userUUID);

        return budgets.stream()
                .map(BudgetReadDto::new)
                .collect(Collectors.toList());
    }

    public Budget updateBudget(String userId, String budgetId, BudgetUpdateDto updateDto) {
        Budget budget = getBudgetByIdAndUserId(budgetId, userId);
        if (budget == null) {
            return null;
        }

        if (updateDto.getName() != null) {
            budget.setName(updateDto.getName());
        }
        if (updateDto.getStartDate() != null) {
            budget.setStartdate(updateDto.getStartDate());
        }
        if (updateDto.getPeriod() != null) {
            budget.setPeriod(updateDto.getPeriod());
            if (budget.getStartdate() != null) {
                budget.setEnddate(budget.getStartdate().plusDays(updateDto.getPeriod()));
            }
        }
        if (updateDto.getLimit() != null) {
            budget.setBudgetlimit(updateDto.getLimit());
        }
        if (updateDto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(updateDto.getCategoryId());
            budget.setCategoryid(category);
        }

        return budgetRepository.save(budget);
    }

    public boolean deleteBudget(String userId, String budgetId) {
        Budget budget = getBudgetByIdAndUserId(budgetId, userId);
        if (budget == null) {
            return false;
        }
        budgetRepository.delete(budget);
        return true;
    }

    private Budget getBudgetByIdAndUserId(String budgetId, String userId) {
        UUID budgetUUID = UUID.fromString(budgetId);
        UUID userUUID = UUID.fromString(userId);

        Optional<Budget> optional = budgetRepository.findByIdAndUserid_Id(budgetUUID, userUUID);

        return optional.orElse(null);
    }
}
