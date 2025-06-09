package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.model.account.AccountCreateDto;
import uit.se330.chitieu.model.account.AccountReadDto;
import uit.se330.chitieu.model.account.AccountUpdateDto;
import uit.se330.chitieu.service.AccountService;
import uit.se330.chitieu.util.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountReadDto>> getAccountsByUser() {
        String userId = SecurityUtil.getCurrentUserId();
        List<AccountReadDto> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountReadDto> getAccountById(@PathVariable String id) {
        String userId = SecurityUtil.getCurrentUserId();
        AccountReadDto account = accountService.getAccount(userId, id);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        String userId = SecurityUtil.getCurrentUserId();
        Account account = accountService.createAccount(userId, accountCreateDto);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable String id, @RequestBody AccountUpdateDto accountUpdateDto) {
        String userId = SecurityUtil.getCurrentUserId();
        Account updated = accountService.updateAccount(userId, id, accountUpdateDto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable String id) {
        String userId = SecurityUtil.getCurrentUserId();
        boolean deleted = accountService.deleteAccount(userId, id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.internalServerError().build();
    }
}
