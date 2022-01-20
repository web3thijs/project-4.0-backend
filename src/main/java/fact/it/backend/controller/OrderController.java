package fact.it.backend.controller;

import fact.it.backend.model.Customer;
import fact.it.backend.model.Order;
import fact.it.backend.model.Product;
import fact.it.backend.model.Role;
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

    @PostConstruct
    public void fillDB(){
        if(orderRepository.count()==0){
            Customer customer1 = new Customer("giannidh@gmail.com", "password123", "0479994529", "Kersstraat 17", "2200", "België", Role.CUSTOMER,"Gianni" , "De Herdt", false);
            Customer customer2 = new Customer("thijswouters@gmail.com", "password123", "0479954719", "Hoekstraat 165", "1680", "België", Role.CUSTOMER,"Thijs" , "Wouters", true);
            orderRepository.save(new Order("61e80b759212ed04521a94b0",  customer1, new Date()));
            orderRepository.save(new Order("61e80b759212ed04521a94b2", customer2, new Date()));
            orderRepository.save(new Order("61e80b759212ed04521a94b3", customer1, new Date()));
        }
        System.out.println("DB test orders: " + orderRepository.findAll().size() + " orders.");
    }

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

        if(order != null){
            orderRepository.delete(order);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
