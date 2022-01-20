package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrderDetailRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RequestMapping(path = "api/orderdetails")
@RestController
public class OrderDetailController {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @PostConstruct
    public void fillDB(){
        Customer customer1 = new Customer("giannidh@gmail.com", "password123", "0479994529", "Kersstraat 17", "2200", "België", Role.CUSTOMER,"Gianni" , "De Herdt", false);
        Customer customer2 = new Customer("thijswouters@gmail.com", "password123", "0479954719", "Hoekstraat 165", "1680", "België", Role.CUSTOMER,"Thijs" , "Wouters", true);
        Order order1 = new Order("61e80b759212ed04521a94b0", customer1, new Date());
        Order order2 = new Order("61e80b759212ed04521a94b2", customer2, new Date());
        Product product1 = new Product("61e6c2c183f852129f4ffff3", new ObjectId().toString(), new ObjectId().toString(), "T-shirt", 13.99, "Plain T-shirt", true, "Google.com");
        Product product2 = new Product("61e6c2c183f852129f4ffff4", new ObjectId().toString(), new ObjectId().toString(), "Jeans", 23.99, "Plain Jeans", true, "Google.com");
        Size size1 = new Size("61e7ca11710259397a88e7cf", "S");
        Size size2 = new Size("61e7ca11710259397a88e7d0", "M");
        Color color1 = new Color("61e7c6f1abd83a51b5208b01", "red");
        Color color2 = new Color("61e7c6f1abd83a51b5208b02", "green");
        if(orderDetailRepository.count()==0){
            orderDetailRepository.save(new OrderDetail(product1, order1, size1, color1, 2));
            orderDetailRepository.save(new OrderDetail(product2, order2, size2, color2, 4));
            orderDetailRepository.save(new OrderDetail(product1, order1, size2, color2, 6));

        }
        System.out.println("DB test orderdetails " + orderDetailRepository.findAll().size() + " orderdetails.");
    }

    @GetMapping("")
    public List<OrderDetail> findAll(){
        return orderDetailRepository.findAll();
    }

    @GetMapping("/order/{orderId}")
    public List<OrderDetail> findOrderDetailByOrderId(@PathVariable String orderId){
        return orderDetailRepository.findOrderDetailsByOrderId(orderId);
    }

    @PostMapping("")
    public OrderDetail addOrderDetail(@RequestBody OrderDetail orderDetail){
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

    @PutMapping("")
    public OrderDetail updateOrderDetail(@RequestBody OrderDetail updatedOrderDetail){
        OrderDetail retrievedOrderDetail = orderDetailRepository.findOrderDetailById(updatedOrderDetail.getId());

        retrievedOrderDetail.setProduct(updatedOrderDetail.getProduct());
        retrievedOrderDetail.setOrder(updatedOrderDetail.getOrder());
        retrievedOrderDetail.setSize(updatedOrderDetail.getSize());
        retrievedOrderDetail.setColor(updatedOrderDetail.getColor());
        retrievedOrderDetail.setAmount(updatedOrderDetail.getAmount());

        orderDetailRepository.save(retrievedOrderDetail);

        return retrievedOrderDetail;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderDetail(@PathVariable String id){
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(id);

        if(orderDetail != null){
            orderDetailRepository.delete(orderDetail);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
