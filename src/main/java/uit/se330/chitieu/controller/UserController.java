package uit.se330.chitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.se330.chitieu.entity.User;
import uit.se330.chitieu.model.user.UserCreateDto;
import uit.se330.chitieu.service.JwtService;
import uit.se330.chitieu.service.UserService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto) {
        User existing = userService.findByEmail(userCreateDto.getEmail());

        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        User user = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot find user");
        }

        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail()
        );

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
