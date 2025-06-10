package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.entity.Income;
import uit.se330.chitieu.model.record.RecordQuery;
import uit.se330.chitieu.model.record.income.IncomeCreateDto;
import uit.se330.chitieu.model.record.income.IncomeUpdateDto;
import uit.se330.chitieu.repository.IncomeRepository;
import uit.se330.chitieu.repository.specifications.IncomeSpecifications;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private AccountService accountService;

    public Income createIncome(String userId, IncomeCreateDto dto) {
        Account account = accountService.getAccountByIdAndUserId(dto.getAccountId().toString(), userId);
        if (account == null) {
            return null;
        }

        Income income = dto.toEntity();
        return incomeRepository.save(income);
    }

    public Income getIncome(String userId, String incomeId) {
        // TODO: use ReadDto
        return getIncomeByIdAndUserId(incomeId, userId);
    }

    public List<Income> getIncomesWithQuery(RecordQuery query) {
        return incomeRepository.findAll(IncomeSpecifications.withFilters(query));
    }

    public Income updateIncome(String userId, String incomeId, IncomeUpdateDto dto) {
        Income income = getIncomeByIdAndUserId(incomeId, userId);
        if (income == null) {
            return null;
        }

        income.setTitle(dto.getTitle());
        income.setDescription(dto.getDescription());
        income.setAmount(dto.getAmount());

        if (dto.getDate() != null) {
            income.setCreatedat(dto.getDate().atStartOfDay().toInstant(ZoneOffset.UTC));
        }

        Account account = new Account();
        account.setId(dto.getAccountId());
        income.setAccountid(account);

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            income.setCategoryid(category);
        } else {
            income.setCategoryid(null);
        }

        return incomeRepository.save(income);
    }

    public boolean deleteIncome(String userId, String incomeId) {
        Income income = getIncomeByIdAndUserId(incomeId, userId);
        if (income == null) {
            return false;
        }
        incomeRepository.delete(income);
        return true;
    }

    private Income getIncomeByIdAndUserId(String incomeId, String userId) {
        UUID incomeUUID = UUID.fromString(incomeId);
        UUID userUUID = UUID.fromString(userId);

        Optional<Income> optional = incomeRepository.findByIdAndAccountid_Userid_Id(incomeUUID, userUUID);

        return optional.orElse(null);
    }
}
