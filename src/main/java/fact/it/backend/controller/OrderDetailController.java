package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrderDetailRepository;
import fact.it.backend.util.JwtUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/orderdetails")
@RestController
public class OrderDetailController {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix, @RequestParam int page){
        Pageable requestedPage = PageRequest.of(page, 8);
        Page<OrderDetail> orderDetails = orderDetailRepository.findAll(requestedPage);
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            return ResponseEntity.ok(orderDetails);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findOrderDetailByOrderId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String orderId){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();
        List<OrderDetail> retrievedOrderDetails = orderDetailRepository.findOrderDetailsByOrderId(orderId);

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrderDetails.get(0) != null && retrievedOrderDetails.get(0).getOrder().getCustomer().getId().contains(user_id))){
            return ResponseEntity.ok(retrievedOrderDetails);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addOrderDetail(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody OrderDetail orderDetail){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && orderDetail.getOrder().getCustomer().getId().contains(user_id))){
            orderDetailRepository.save(orderDetail);
            return ResponseEntity.ok(orderDetail);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateOrderDetail(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody OrderDetail updatedOrderDetail){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();
        OrderDetail retrievedOrderDetail = orderDetailRepository.findOrderDetailById(updatedOrderDetail.getId());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrderDetail.getOrder().getCustomer().getId().contains(user_id))){
            retrievedOrderDetail.setProduct(updatedOrderDetail.getProduct());
            retrievedOrderDetail.setOrder(updatedOrderDetail.getOrder());
            retrievedOrderDetail.setSize(updatedOrderDetail.getSize());
            retrievedOrderDetail.setColor(updatedOrderDetail.getColor());
            retrievedOrderDetail.setAmount(updatedOrderDetail.getAmount());

            orderDetailRepository.save(retrievedOrderDetail);

            return ResponseEntity.ok(retrievedOrderDetail);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderDetail(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(id);

            if(orderDetail != null){
                orderDetailRepository.delete(orderDetail);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

}
