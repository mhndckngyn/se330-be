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
import java.util.UUID;

public class IncomeSpecifications {
    public static Specification<Income> withFilters(RecordQuery query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join with Account and Category
            Join<Income, Account> accountJoin = root.join("accountid");
            Join<Income, Category> categoryJoin = root.join("categoryid", JoinType.LEFT);

            predicates.add(cb.equal(accountJoin.get("userid").get("id"), query.userId));

            if (query.accountIds != null && !query.accountIds.isEmpty()) {
                List<UUID> accountUUIDs = query.accountIds.stream()
                        .map(UUID::fromString)
                        .toList();

                predicates.add(accountJoin.get("id").in(accountUUIDs));
            }

            // Filter by categoryIds
            if (query.categoryIds != null && !query.categoryIds.isEmpty()) {
                List<UUID> categoryUUIDs = query.categoryIds.stream()
                        .map(UUID::fromString)
                        .toList();

                predicates.add(categoryJoin.get("id").in(categoryUUIDs));
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