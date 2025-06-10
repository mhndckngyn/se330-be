package uit.se330.chitieu.model.record;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class RecordParams {
    public List<String> accountIds;
    public List<String> categoryIds;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate endDate;
}
