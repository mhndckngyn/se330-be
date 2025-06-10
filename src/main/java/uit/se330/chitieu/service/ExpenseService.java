package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.entity.Expense;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.expense.ExpenseCreateDto;
import uit.se330.chitieu.model.record.expense.ExpenseUpdateDto;
import uit.se330.chitieu.repository.ExpenseRepository;
import uit.se330.chitieu.repository.specifications.ExpenseSpecifications;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AccountService accountService;

    public Expense createExpense(String userId, ExpenseCreateDto dto) {
        Account account = accountService.getAccountByIdAndUserId(
                dto.getAccountId().toString(),
                userId);
        if (account == null) {
            return null;
        }

        Expense expense = dto.toEntity();
        return expenseRepository.save(expense);
    }

    public Expense getExpense(String userId, String expenseId) {
        // TODO: use ReadDto
        return getExpenseByIdAndUserId(expenseId, userId);
    }

    public List<Expense> getExpensesWithQuery(RecordQuery query) {
        return expenseRepository.findAll(ExpenseSpecifications.withFilters(query));
    }

    public Expense updateExpense(String userId, String expenseId, ExpenseUpdateDto dto) {
        Expense expense = getExpenseByIdAndUserId(expenseId, userId);
        if (expense == null) {
            return null;
        }

        expense.setTitle(dto.getTitle());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());

        if (dto.getDate() != null) {
            expense.setCreatedat(dto.getDate().atStartOfDay().toInstant(ZoneOffset.UTC));
        }

        Account account = new Account();
        account.setId(dto.getAccountId());
        expense.setAccountid(account);

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            expense.setCategoryid(category);
        } else {
            expense.setCategoryid(null);
        }

        return expenseRepository.save(expense);
    }

    public boolean deleteExpense(String userId, String expenseId) {
        Expense expense = getExpenseByIdAndUserId(expenseId, userId);
        if (expense == null) {
            return false;
        }
        expenseRepository.delete(expense);
        return true;
    }

    private Expense getExpenseByIdAndUserId(String expenseId, String userId) {
        UUID expenseUUID = UUID.fromString(expenseId);
        UUID userUUID = UUID.fromString(userId);

        Optional<Expense> optional = expenseRepository.findByIdAndAccountid_Userid_Id(expenseUUID, userUUID);

        return optional.orElse(null);
    }
}
