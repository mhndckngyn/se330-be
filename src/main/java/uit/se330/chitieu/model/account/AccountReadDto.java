package uit.se330.chitieu.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountReadDto {
    public UUID id;
    public String name;
    public BigDecimal balance;
    public BigDecimal totalIncome;
    public BigDecimal totalExpense;
}
