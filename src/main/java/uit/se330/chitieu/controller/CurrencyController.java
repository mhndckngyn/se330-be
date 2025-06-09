package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Currency;
import uit.se330.chitieu.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<Currency>> getCurrency() {
        List<Currency> currencies = currencyService.getCurrencies();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable String id) {
        Currency currency = currencyService.getCurrencyById(id);
        if (currency == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(currency);
    }
}
