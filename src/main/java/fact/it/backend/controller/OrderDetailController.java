package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.*;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/orderdetails")
public class OrderDetailController {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    private JwtUtils jwtUtils;

/*    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "order.date") String sort, @RequestParam(required = false) String order){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        if(role.contains("ADMIN")){
                if(order != null && order.equals("desc")){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                    Page<OrderDetail> orderDetails = orderDetailRepository.findAll(requestedPageWithSortDesc);
                    return ResponseEntity.ok(orderDetails);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                    Page<OrderDetail> orderDetails = orderDetailRepository.findAll(requestedPageWithSort);
                    return ResponseEntity.ok(orderDetails);
                }
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }*/

    @GetMapping("/organization")
    public ResponseEntity<?> findOrderDetailByOrganizationId(@RequestHeader("Authorization") String tokenWithPrefix){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        List<OrderDetail> retrievedOrderDetails = orderDetailRepository.findAll();
        List<OrderDetail> correctOrderDetails = new ArrayList();

        if(role.contains("ORGANIZATION")){
            for(int i = 0; i < retrievedOrderDetails.size(); i++){
                if(retrievedOrderDetails.get(i).getProduct().getOrganization().getId() == user_id){
                    correctOrderDetails.add(retrievedOrderDetails.get(i));
                }
            }
            return ResponseEntity.ok(correctOrderDetails);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findOrderDetailByOrderId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long orderId) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        List<OrderDetail> retrievedOrderDetails = orderDetailRepository.findOrderDetailsByOrderId(orderId);

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrderDetails.get(0) != null && retrievedOrderDetails.get(0).getOrder().getCustomer().getId() == user_id)){
            if(retrievedOrderDetails.size() != 0){
                return ResponseEntity.ok(retrievedOrderDetails);
            } else{
                throw new ResourceNotFoundException("No orderdetails found for order with id: " + orderId);
            }
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> addOrderDetail(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody OrderDetail orderDetail){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && orderDetail.getOrder().getCustomer().getId() == user_id)){
            orderDetailRepository.save(orderDetail);
            return ResponseEntity.ok(orderDetail);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrderDetail(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody OrderDetail updatedOrderDetail) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        OrderDetail retrievedOrderDetail = orderDetailRepository.findById(updatedOrderDetail.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Orderdetail not found for this id: " + updatedOrderDetail.getId()));


        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedOrderDetail.getOrder().getCustomer().getId() == user_id)){
            retrievedOrderDetail.setProduct(productRepository.findProductById(updatedOrderDetail.getProduct().getId()));
            retrievedOrderDetail.setOrder(orderRepository.findOrderById(updatedOrderDetail.getOrder().getId()));
            retrievedOrderDetail.setSize(sizeRepository.findSizeById(updatedOrderDetail.getSize().getId()));
            retrievedOrderDetail.setColor(colorRepository.findColorById(updatedOrderDetail.getColor().getId()));
            retrievedOrderDetail.setAmount(updatedOrderDetail.getAmount());

            orderDetailRepository.save(retrievedOrderDetail);

            return ResponseEntity.ok(retrievedOrderDetail);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderDetail(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            OrderDetail orderDetail = orderDetailRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Orderdetail not found for this id: " + id));

                orderDetailRepository.delete(orderDetail);
                return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

}
