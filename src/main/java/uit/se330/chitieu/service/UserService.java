package uit.se330.chitieu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uit.se330.chitieu.entity.Currency;
import uit.se330.chitieu.entity.User;
import uit.se330.chitieu.model.user.UserCreateDto;
import uit.se330.chitieu.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    public User createUser(UserCreateDto userCreateDto) {
        User user = new User();

        user.setEmail(userCreateDto.getEmail());
        user.setName(userCreateDto.getName());

        Currency currency = new Currency();
        currency.setId(UUID.fromString(userCreateDto.getCurrencyId()));
        user.setCurrencyid(currency);

        return userRepository.save(user);
    }
}
