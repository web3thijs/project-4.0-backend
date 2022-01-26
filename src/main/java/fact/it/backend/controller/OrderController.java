package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrderDetailRepository;
import fact.it.backend.repository.OrderRepository;
import fact.it.backend.util.JwtUtils;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/orders")
@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    OrderDetailRepository orderDetailRepository;


    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix, @RequestParam int page, @RequestParam(required = false) String sort, @RequestParam(required = false)String order){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            if(sort != null){
                if(order != null){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                    Page<Order> orders = orderRepository.findAll(requestedPageWithSortDesc);
                    return ResponseEntity.ok(orders);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                    Page<Order> orders = orderRepository.findAll(requestedPageWithSort);
                    return ResponseEntity.ok(orders);
                }
            }else{
                Pageable requestedPage = PageRequest.of(page, 8, Sort.by("date").descending());
                Page<Order> orders = orderRepository.findAll(requestedPage);
                return ResponseEntity.ok(orders);
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderById(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();
        Order retrievedOrder = orderRepository.findOrderById(id);

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrder.getCustomer().getId().contains(user_id))){
            return ResponseEntity.ok(retrievedOrder);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findOrdersByCustomerId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String customerId, @RequestParam int page, @RequestParam(required = false) String sort, @RequestParam(required = false)String order){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && customerId.contains(user_id))){
            if(sort != null){
                if(order != null && order.equals("desc")){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                    Page<Order> orders = orderRepository.findOrdersByCustomerId(customerId, requestedPageWithSortDesc);
                    return ResponseEntity.ok(orders);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                    Page<Order> orders = orderRepository.findOrdersByCustomerId(customerId, requestedPageWithSort);
                    return ResponseEntity.ok(orders);
                }
            }else{
                Pageable requestedPage = PageRequest.of(page, 8, Sort.by("name").ascending());
                Page<Order> orders = orderRepository.findOrdersByCustomerId(customerId, requestedPage);
                return ResponseEntity.ok(orders);
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Order order){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && order.getCustomer().getId().contains(user_id))){
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateOrder(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Order updatedOrder){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();
        Order retrievedOrder = orderRepository.findOrderById(updatedOrder.getId());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrder.getCustomer().getId().contains(user_id))){
            retrievedOrder.setCustomer(updatedOrder.getCustomer());
            retrievedOrder.setDate(updatedOrder.getDate());

            orderRepository.save(retrievedOrder);

            return ResponseEntity.ok(retrievedOrder);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Order order = orderRepository.findOrderById(id);
            List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(id);

            if(order != null){
                orderRepository.delete(order);
                if(orderDetails != null) {
                  for (int i = 0; i < orderDetails.size(); i++) {
                      orderDetailRepository.delete(orderDetails.get(i));
                  }
                }
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
