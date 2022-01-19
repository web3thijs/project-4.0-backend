package fact.it.backend.controller;

import fact.it.backend.model.Order;
import fact.it.backend.model.OrderDetail;
import fact.it.backend.repository.OrderDetailRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/orderdetails")
@RestController
public class OrderDetailController {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @PostConstruct
    public void fillDB(){
        String order1 = new ObjectId().toString();
        String order2 = new ObjectId().toString();
        if(orderDetailRepository.count()==0){
            orderDetailRepository.save(new OrderDetail(new ObjectId().toString(), order1, new ObjectId().toString(), new ObjectId().toString(), 2));
            orderDetailRepository.save(new OrderDetail(new ObjectId().toString(), order1, new ObjectId().toString(), new ObjectId().toString(), 4));
            orderDetailRepository.save(new OrderDetail(new ObjectId().toString(), order2, new ObjectId().toString(), new ObjectId().toString(), 3));

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

        retrievedOrderDetail.setProductId(updatedOrderDetail.getProductId());
        retrievedOrderDetail.setOrderId(updatedOrderDetail.getOrderId());
        retrievedOrderDetail.setSizeId(updatedOrderDetail.getSizeId());
        retrievedOrderDetail.setColorId(updatedOrderDetail.getColorId());
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
