package fact.it.backend.controller;

import fact.it.backend.model.AuthRequest;
import fact.it.backend.model.AuthResponse;
import fact.it.backend.model.User;
import fact.it.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api")
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/subscription")
    private ResponseEntity<?> subscribeClient(@RequestBody AuthRequest authRequest){
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();
        User user = new User();

        user.setEmail(email);
        user.setPassword(password);

        try{
            userRepository.save(user);
        } catch (Exception e){
            return ResponseEntity.ok(new AuthResponse("Error during subscription for client " + email));
        }

        return ResponseEntity.ok(new AuthResponse("Succesful subscription for client " + email));
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

        return ResponseEntity.ok(new AuthResponse("Succesful authentication for client " + email));
    }
}
