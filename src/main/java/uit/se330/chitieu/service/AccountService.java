package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.entity.Account;
import uit.se330.chitieu.entity.User;
import uit.se330.chitieu.model.account.AccountCreateDto;
import uit.se330.chitieu.model.account.AccountReadDto;
import uit.se330.chitieu.model.account.AccountUpdateDto;
import uit.se330.chitieu.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(String userId, AccountCreateDto accountCreateDto) {
        Account account = new Account();
        account.setName(accountCreateDto.getName());
        account.setBalance(accountCreateDto.getBalance());

        User user = new User();
        user.setId(UUID.fromString(userId));
        account.setUserid(user);

        return accountRepository.save(account);
    }

    public AccountReadDto getAccount(String userId, String accountId) {
        Account account = getAccountByIdAndUserId(userId, accountId);
        if (account == null) {
            return null;
        }
        return new AccountReadDto(account.getId(), account.getName(), account.getBalance());
    }

    public List<AccountReadDto> getAccountsByUserId(String userId) {
        UUID uuid = UUID.fromString(userId);
        List<Account> accounts = accountRepository.findByUserid_Id(uuid);

        return accounts.stream()
                .map(account -> new AccountReadDto(
                        account.getId(),
                        account.getName(),
                        account.getBalance()))
                .collect(Collectors.toList());
    }

    public Account updateAccount(String userId, String accountId, AccountUpdateDto accountUpdateDto) {
        Account account = getAccountByIdAndUserId(userId, accountId);
        if (account == null) {
            return null;
        }

        account.setName(accountUpdateDto.getName());
        return accountRepository.save(account);
    }

    public boolean deleteAccount(String userId, String accountId) {
        Account account = getAccountByIdAndUserId(userId, accountId);
        if (account == null) {
            return false;
        }

        accountRepository.delete(account);
        return true;
    }

    private Account getAccountByIdAndUserId(String accountId, String userId) {
        UUID userUUID = UUID.fromString(userId);
        UUID accountUUID = UUID.fromString(accountId);
        Optional<Account> optional = accountRepository.findByIdAndUserid_Id(accountUUID, userUUID);

        return optional.orElse(null);
    }
}
