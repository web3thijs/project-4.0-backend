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
