package fact.it.backend.controller;

import fact.it.backend.dto.UpdateDonationDTO;
import fact.it.backend.dto.UpdateOrderDetailDTO;
import fact.it.backend.model.Role;
import fact.it.backend.service.CartService;
import fact.it.backend.service.OrderService;
import fact.it.backend.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/cart")
public class CartController {

    JwtUtils jwtUtils;
    CartService cartService;
    OrderService orderService;

    public CartController(JwtUtils jwtUtils, CartService cartService, OrderService orderService) {
        this.jwtUtils = jwtUtils;
        this.cartService = cartService;
        this.orderService = orderService;
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

    @PostMapping("/addProduct")
    public ResponseEntity addProductToOrder(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody UpdateOrderDetailDTO updateOrderDetailDTO){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            orderService.addProductToOrder(updateOrderDetailDTO, user_id);
            return new ResponseEntity<String>("Added", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/updateProduct")
    public ResponseEntity updateProductFromOrder(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody UpdateOrderDetailDTO updateOrderDetailDTO){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            orderService.updateOrderDetail(updateOrderDetailDTO, user_id);
            return new ResponseEntity<String>("Added", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addDonation")
    public ResponseEntity addDonationToOrder(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody UpdateDonationDTO updateDonationDTO){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            orderService.addDonationToOrder(updateDonationDTO, user_id);
            return new ResponseEntity<String>("Added", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/updateDonation")
    public ResponseEntity updateDonationFromOrder(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody UpdateDonationDTO updateDonationDTO){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            orderService.updateDonation(updateDonationDTO, user_id);
            return new ResponseEntity<String>("Added", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}