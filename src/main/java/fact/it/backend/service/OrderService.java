package fact.it.backend.service;

import fact.it.backend.dto.UpdateDonationDTO;
import fact.it.backend.dto.UpdateOrderDetailDTO;
import fact.it.backend.model.Donation;
import fact.it.backend.model.Order;
import fact.it.backend.model.OrderDetail;
import fact.it.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    ProductRepository productRepository;
    ColorRepository colorRepository;
    SizeRepository sizeRepository;
    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    OrderDetailRepository orderDetailRepository;
    DonationRepository donationRepository;
    OrganizationRepository organizationRepository;

    public OrderService(ProductRepository productRepository, ColorRepository colorRepository, SizeRepository sizeRepository, OrderRepository orderRepository, CustomerRepository customerRepository, OrderDetailRepository orderDetailRepository, DonationRepository donationRepository, OrganizationRepository organizationRepository) {
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.donationRepository = donationRepository;
        this.organizationRepository = organizationRepository;
    }

    public void addProductToOrder(UpdateOrderDetailDTO updateOrderDetailDTO, long userId){
        if(orderRepository.findOrdersByCustomerId(userId).size() == 0){
            Order newOrder = new Order(new Date(), false, customerRepository.findById(userId));
            orderRepository.save(newOrder);
        }

        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsBySizeIdAndColorIdAndProductIdAndOrderId(updateOrderDetailDTO.getSizeId(), updateOrderDetailDTO.getColorId(), updateOrderDetailDTO.getProductId(), order.getId());

        if(orderDetails.size() == 1){
            OrderDetail orderDetail = orderDetailRepository.findOrderDetailBySizeIdAndColorIdAndProductIdAndOrderId(updateOrderDetailDTO.getSizeId(), updateOrderDetailDTO.getColorId(), updateOrderDetailDTO.getProductId(), order.getId());
            orderDetail.setAmount(orderDetail.getAmount() + updateOrderDetailDTO.getAmount());
            orderDetailRepository.save(orderDetail);
        } else {
            OrderDetail newOrderDetail = new OrderDetail(updateOrderDetailDTO.getAmount(), productRepository.findProductById(updateOrderDetailDTO.getProductId()), orderRepository.findOrderById(order.getId()), sizeRepository.findSizeById(updateOrderDetailDTO.getSizeId()), colorRepository.findColorById(updateOrderDetailDTO.getColorId()));
            orderDetailRepository.save(newOrderDetail);
        }
    }

    public void updateOrderDetail(UpdateOrderDetailDTO updateOrderDetailDTO, long userId){
        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailBySizeIdAndColorIdAndProductIdAndOrderId(updateOrderDetailDTO.getSizeId(), updateOrderDetailDTO.getColorId(), updateOrderDetailDTO.getProductId(), order.getId());

        if(updateOrderDetailDTO.getAmount() == 0){
            orderDetailRepository.delete(orderDetail);
        } else {
            orderDetail.setAmount(updateOrderDetailDTO.getAmount());
            orderDetailRepository.save(orderDetail);
        }
    }

    public void addDonationToOrder(UpdateDonationDTO updateDonationDTO, long userId){
        if(orderRepository.findOrdersByCustomerId(userId).size() == 0){
            Order newOrder = new Order(new Date(), false, customerRepository.findById(userId));
            orderRepository.save(newOrder);
        }

        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
        Donation donation = new Donation(updateDonationDTO.getAmount(), order, organizationRepository.findOrganizationById(updateDonationDTO.getOrganizationId()));
        donationRepository.save(donation);

    }

    public void updateDonation(UpdateDonationDTO updateDonationDTO, long userId){
        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
        Donation donation = donationRepository.findDonationByOrderIdAndOrganizationId(order.getId(), updateDonationDTO.getOrganizationId());

        if(updateDonationDTO.getAmount() == 0){
            donationRepository.delete(donation);
        } else {
            donation.setAmount(updateDonationDTO.getAmount());
            donationRepository.save(donation);
        }
    }
}
