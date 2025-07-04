package uit.se330.chitieu.model.account;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Setter
@Getter
public class AccountCreateDto {
    public String name;
    public BigDecimal balance;
}
