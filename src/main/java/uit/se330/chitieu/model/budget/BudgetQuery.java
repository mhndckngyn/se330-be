package uit.se330.chitieu.model.budget;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BudgetQuery extends BudgetParams {
    public String userId;

    public BudgetQuery(BudgetParams params, String userId) {
        this.categoryIds = params.getCategoryIds();
        this.userId = userId;
    }
}
