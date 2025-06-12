package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.se330.chitieu.model.statistic.service.daily.DailyFinancialReport;
import uit.se330.chitieu.model.statistic.service.monthly.MonthlyFinancialReport;
import uit.se330.chitieu.service.StatisticService;
import uit.se330.chitieu.util.SecurityUtil;

import java.util.UUID;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public ResponseEntity<DailyFinancialReport> getStatistic(@RequestParam Integer duration) {
        UUID userId = UUID.fromString(SecurityUtil.getCurrentUserId());
        DailyFinancialReport result = statisticService.getFinancialReport(userId, duration);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyFinancialReport> getMonthlyFinancialReport() {
        UUID userId = UUID.fromString(SecurityUtil.getCurrentUserId());
        MonthlyFinancialReport report = statisticService.getLastTwelveMonthsFinancialReport(userId);
        return ResponseEntity.ok(report);
    }
}
