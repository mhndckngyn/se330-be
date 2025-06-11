package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.se330.chitieu.model.statistic.StatisticReadDto;
import uit.se330.chitieu.service.StatisticService;
import uit.se330.chitieu.util.SecurityUtil;

import java.util.UUID;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public ResponseEntity<StatisticReadDto> getStatistic(@RequestParam Integer duration) {
        String userId = SecurityUtil.getCurrentUserId();
        StatisticReadDto result = statisticService.getFinancialReport(UUID.fromString(userId), duration);
        return ResponseEntity.ok(result);
    }
}
