package uit.se330.chitieu.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.repository.ExpenseRepository;
import uit.se330.chitieu.repository.IncomeRepository;

@Service
public class StatisticService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

}
