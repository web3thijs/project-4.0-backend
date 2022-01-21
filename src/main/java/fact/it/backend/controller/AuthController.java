package fact.it.backend.controller;

import fact.it.backend.model.AuthRequest;
import fact.it.backend.model.AuthResponse;
import fact.it.backend.model.Category;
import fact.it.backend.model.User;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.repository.UserRepository;
import fact.it.backend.service.UserService;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;

@RequestMapping(path = "/api")
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    private ResponseEntity<?> subscribeClient(@RequestBody AuthRequest authRequest){
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();
        User user = new User();

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        try{
            userRepository.save(user);
        } catch (Exception e){
            return ResponseEntity.ok(new AuthResponse("Error during registration for client " + email));
        }

        return ResponseEntity.ok(new AuthResponse("Succesful registration for client " + email));
    }

    @PostMapping("/authenticate")
    private ResponseEntity<?> authenticateClient(@RequestBody AuthRequest authRequest){
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AuthResponse("Error during authentication for client " + email));
        }

        UserDetails retrievedUser = userService.loadUserByUsername(email);
        User retrievedUser2 = userRepository.findUserByEmail(email);

        String generatedToken = jwtUtils.generateToken(retrievedUser, retrievedUser2.getRole());

        return ResponseEntity.ok(new AuthResponse(generatedToken));
    }
}
