package fact.it.backend.service;

import fact.it.backend.dto.UpdateDonationDTO;
import fact.it.backend.dto.UpdateOrderDetailDTO;
import fact.it.backend.model.Order;
import fact.it.backend.model.OrderDetail;
import fact.it.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    ProductRepository productRepository;
    ColorRepository colorRepository;
    SizeRepository sizeRepository;
    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    OrderDetailRepository orderDetailRepository;
    DonationRepository donationRepository;

    public OrderService(ProductRepository productRepository, ColorRepository colorRepository, SizeRepository sizeRepository, OrderRepository orderRepository, CustomerRepository customerRepository, OrderDetailRepository orderDetailRepository, DonationRepository donationRepository) {
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.donationRepository = donationRepository;
    }

    public void addProductToOrder(UpdateOrderDetailDTO updateOrderDetailDTO, long userId){
        if(orderRepository.findOrdersByCustomerId(userId).size() == 0){
            Order newOrder = new Order(new Date(), false, customerRepository.findById(userId));
            orderRepository.save(newOrder);
        }

        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
        OrderDetail orderDetail = new OrderDetail(updateOrderDetailDTO.getAmount(), productRepository.findProductById(updateOrderDetailDTO.getProductId()), orderRepository.findOrderById(order.getId()), sizeRepository.findSizeById(updateOrderDetailDTO.getSizeId()), colorRepository.findColorById(updateOrderDetailDTO.getColorId()));
        orderDetailRepository.save(orderDetail);

    }

    public void updateOrderDetail(UpdateOrderDetailDTO updateOrderDetailDTO, long userId){
        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailsBySizeIdAndColorIdAndProductIdAndOrderId(updateOrderDetailDTO.getSizeId(), updateOrderDetailDTO.getColorId(), updateOrderDetailDTO.getProductId(), order.getId());

        if(updateOrderDetailDTO.getAmount() == 0){
            orderDetailRepository.delete(orderDetail);
        } else {
            orderDetail.setAmount(updateOrderDetailDTO.getAmount());
            orderDetailRepository.save(orderDetail);
        }
    }

//    public void addDonationToOrder(UpdateDonationDTO updateDonationDTO, long userId){
//        if(donationRepository.findOrdersByCustomerId(userId).size() == 0){
//            Order newOrder = new Order(new Date(), false, customerRepository.findById(userId));
//            orderRepository.save(newOrder);
//        }
//
//        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
//        OrderDetail orderDetail = new OrderDetail(updateOrderDetailDTO.getAmount(), productRepository.findProductById(updateOrderDetailDTO.getProductId()), orderRepository.findOrderById(order.getId()), sizeRepository.findSizeById(updateOrderDetailDTO.getSizeId()), colorRepository.findColorById(updateOrderDetailDTO.getColorId()));
//        orderDetailRepository.save(orderDetail);
//
//    }
//
//    public void updateDonation(UpdateDonationDTO updateDonationDTO, long userId){
//        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
//        OrderDetail orderDetail = orderDetailRepository.findOrderDetailsBySizeIdAndColorIdAndProductIdAndOrderId(updateOrderDetailDTO.getSizeId(), updateOrderDetailDTO.getColorId(), updateOrderDetailDTO.getProductId(), order.getId());
//
//        if(updateOrderDetailDTO.getAmount() == 0){
//            orderDetailRepository.delete(orderDetail);
//        } else {
//            orderDetail.setAmount(updateOrderDetailDTO.getAmount());
//            orderDetailRepository.save(orderDetail);
//        }
//    }
}
