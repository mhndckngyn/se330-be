package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.entity.Currency;
import uit.se330.chitieu.repository.CurrencyRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency getCurrencyById(String id) {
        UUID uuid = UUID.fromString(id);
        return currencyRepository.findById(uuid)
                .orElse(null);
    }
}
