package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrderDetailRepository;
import fact.it.backend.repository.OrderRepository;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RequestMapping(path = "/api/orders")
@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping("")
    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable String id){
        return orderRepository.findOrderById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> findOrdersByCustomerId(@PathVariable String customerId){
        return orderRepository.findOrdersByCustomerId(customerId);
    }

    @PostMapping("")
    public Order addProduct(@RequestBody Order order){
        orderRepository.save(order);
        return order;
    }

    @PutMapping("")
    public Order updateOrder(@RequestBody Order updatedOrder){
        Order retrievedOrder = orderRepository.findOrderById(updatedOrder.getId());

        retrievedOrder.setCustomer(updatedOrder.getCustomer());
        retrievedOrder.setDate(updatedOrder.getDate());

        orderRepository.save(retrievedOrder);

        return retrievedOrder;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable String id){
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
    }
}
