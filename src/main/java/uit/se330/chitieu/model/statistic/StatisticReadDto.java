package uit.se330.chitieu.model.statistic;

import lombok.Setter;

import java.util.List;

@Setter
public class StatisticReadDto {
    public List<SpendingRecord> spendingRecords;
    public List<CategoryRecord> categoryRecords;
}
