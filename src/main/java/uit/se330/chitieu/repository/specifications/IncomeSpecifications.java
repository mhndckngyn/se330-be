package uit.se330.chitieu.repository.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.Category;
import uit.se330.chitieu.entity.Income;
import uit.se330.chitieu.model.record.RecordQuery;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class IncomeSpecifications {
    public static Specification<Income> withFilters(RecordQuery query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join with Account and Category
            Join<Income, Account> accountJoin = root.join("accountid");
            Join<Income, Category> categoryJoin = root.join("categoryid", JoinType.LEFT);

            predicates.add(cb.equal(accountJoin.get("userid").get("id"), query.userId));

            if (query.accountIds != null && !query.accountIds.isEmpty()) {
                predicates.add(accountJoin.get("id").in(query.accountIds));
            }

            // Filter by categoryIds
            if (query.categoryIds != null && !query.categoryIds.isEmpty()) {
                predicates.add(categoryJoin.get("id").in(query.categoryIds));
            }

            // Filter by startDate
            if (query.startDate != null) {
                Instant start = query.startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdat"), start));
            }

            // Filter by endDate
            if (query.endDate != null) {
                Instant end = query.endDate.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
                predicates.add(cb.lessThanOrEqualTo(root.get("createdat"), end));
            }

            if (cq != null) {
                cq.orderBy(cb.desc(root.get("createdat")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}