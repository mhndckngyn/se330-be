package uit.se330.chitieu.model.statistic;

import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
public class CategoryRecord {
    public UUID id;
    public String name;
    public BigDecimal outcome;
}
