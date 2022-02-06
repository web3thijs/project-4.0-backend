package fact.it.backend.controller;

import fact.it.backend.model.Role;
import fact.it.backend.service.CartService;
import fact.it.backend.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "api/cart")
public class CartController {

    JwtUtils jwtUtils;
    CartService cartService;

    public CartController(JwtUtils jwtUtils, CartService cartService) {
        this.jwtUtils = jwtUtils;
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(@RequestHeader("authorization") String tokenWithPrefix){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            return ResponseEntity.ok(cartService.getCart(user_id));
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
