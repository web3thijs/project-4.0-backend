package fact.it.backend.controller;

import fact.it.backend.dto.UpdateDonationDTO;
import fact.it.backend.dto.UpdateOrderDetailDTO;
import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.DonationRepository;
import fact.it.backend.repository.OrderDetailRepository;
import fact.it.backend.repository.OrderRepository;
import fact.it.backend.service.OrderService;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    DonationRepository donationRepository;


    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            return ResponseEntity.ok(orderService.getOrderHistory(user_id));
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderById(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Order retrievedOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id: " + id));

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrder.getCustomer().getId() == user_id)){
            return ResponseEntity.ok(retrievedOrder);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findOrdersByCustomerId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long customerId) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && customerId == user_id)){
            List<Order> orders = orderRepository.findOrdersByCustomerId(customerId);
            if(orders.size() != 0){
                return ResponseEntity.ok(orders);
            }
            else{
                throw new ResourceNotFoundException("No orders found for customer with id: " + customerId);
            }
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Order order){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && order.getCustomer().getId() == user_id)){
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Order updatedOrder) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Order retrievedOrder = orderRepository.findById(updatedOrder.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Order not found for this id: " + updatedOrder.getId()));

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrder.getCustomer().getId() == user_id)){
            retrievedOrder.setCustomer(customerRepository.findCustomerById(updatedOrder.getCustomer().getId()));
            retrievedOrder.setDate(updatedOrder.getDate());
            retrievedOrder.setCompleted(updatedOrder.isCompleted());

            orderRepository.save(retrievedOrder);

            return ResponseEntity.ok(retrievedOrder);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Order not found for this id: " + id));
            List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(id);

            if(order != null){
                if(orderDetails != null) {
                  for (int i = 0; i < orderDetails.size(); i++) {
                      orderDetailRepository.delete(orderDetails.get(i));
                  }
                }
                if(order.isCompleted() == false){
                    List<Donation> donations = donationRepository.findDonationsByOrderId(order.getId());
                    if(donations != null){
                        for (int i = 0; i < donations.size(); i++) {
                            donationRepository.delete(donations.get(i));
                        }
                    }
                }
                orderRepository.delete(order);
                return ResponseEntity.ok().build();
            }else{
                throw new ResourceNotFoundException("Cannot delete. No orders found with id: " + id);
            }
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
