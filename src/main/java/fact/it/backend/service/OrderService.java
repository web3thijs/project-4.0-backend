package fact.it.backend.service;

import fact.it.backend.dto.*;
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

        Order order = orderRepository.findOrderByCustomerIdAndCompleted(userId, false);
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
        Order order = orderRepository.findOrderByCustomerIdAndCompleted(userId, false);
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

        Order order = orderRepository.findOrderByCustomerIdAndCompleted(userId, false);
        List<Donation> donations = donationRepository.findDonationsByOrderIdAndOrganizationId(order.getId(), updateDonationDTO.getOrganizationId());

        if(donations.size() == 1){
            Donation donation = donationRepository.findDonationByOrderIdAndOrganizationId(order.getId(), updateDonationDTO.getOrganizationId());
            donation.setAmount(donation.getAmount() + updateDonationDTO.getAmount());
            donationRepository.save(donation);
        } else {
            Donation donation = new Donation(updateDonationDTO.getAmount(), order, organizationRepository.findOrganizationById(updateDonationDTO.getOrganizationId()));
            donationRepository.save(donation);
        }
    }

    public void updateDonation(UpdateDonationDTO updateDonationDTO, long userId){
        Order order = orderRepository.findOrderByCustomerIdAndCompleted(userId, false);
        Donation donation = donationRepository.findDonationByOrderIdAndOrganizationId(order.getId(), updateDonationDTO.getOrganizationId());

        if(updateDonationDTO.getAmount() == 0){
            donationRepository.delete(donation);
        } else {
            donation.setAmount(updateDonationDTO.getAmount());
            donationRepository.save(donation);
        }
    }

    public void completeOrder(CompleteOrderDTO completeOrderDTO, long userId){
        Order order = orderRepository.findOrderByCustomerIdAndCompleted(userId, false);

        order.setCountry(completeOrderDTO.getCountry());
        order.setAddress(completeOrderDTO.getAddress());
        order.setPostalCode(completeOrderDTO.getPostal());

        orderRepository.save(order);
    }

    public OrderConfirmationDTO getOrderConfirmation(Long userId){
        Order order = orderRepository.findOrderByCustomerIdAndCompleted(userId, false);
        OrderConfirmationDTO orderConfirmationDTO = new OrderConfirmationDTO();
        List<CartProductDTO> cartProductDTOS = new ArrayList<>();
        List<CartDonationDTO> cartDonationDTOS = new ArrayList<>();


        for(OrderDetail orderDetail : order.getOrderDetails()){
            cartProductDTOS.add(new CartProductDTO(orderDetail.getProduct().getId(), orderDetail.getSize().getId(), orderDetail.getProduct().getName(), orderDetail.getProduct().getPrice(), orderDetail.getAmount(), orderDetail.getSize().getName(), orderDetail.getProduct().getImageUrl()));
        }

        for(Donation donation : order.getDonations()){
            cartDonationDTOS.add(new CartDonationDTO(donation.getOrganization().getId(), donation.getOrganization().getOrganizationName(), donation.getOrganization().getImageUrl(), donation.getAmount()));
        }

        orderConfirmationDTO.setCartProductDTOS(cartProductDTOS);
        orderConfirmationDTO.setCartDonationDTOS(cartDonationDTOS);
        orderConfirmationDTO.setAddress(order.getAddress());
        orderConfirmationDTO.setCountry(order.getCountry());
        orderConfirmationDTO.setPostal(order.getPostalCode());

        order.setCompleted(true);
        orderRepository.save(order);

        Order newOrder = new Order(new Date(), false, customerRepository.findCustomerById(userId));
        orderRepository.save(newOrder);

        return orderConfirmationDTO;
    }

    public List<OrderHistoryDTO> getOrderHistory(Long userId){
        List<Order> orders = orderRepository.findOrdersByCustomerIdAndCompleted(userId, true);
        List<OrderHistoryDTO> orderHistoryDTOs = new ArrayList<>();

        if(orders.size() == 0){
            return orderHistoryDTOs;
        }

        for(Order order : orders){
            OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO();
            List<CartProductDTO> cartProductDTOS = new ArrayList<>();
            List<CartDonationDTO> cartDonationDTOS = new ArrayList<>();

            Double totalOrderDetails = 0.00;
            Double totalDonations = 0.00;

            for(OrderDetail orderDetail : order.getOrderDetails()){
                cartProductDTOS.add(new CartProductDTO(orderDetail.getProduct().getId(), orderDetail.getSize().getId(), orderDetail.getProduct().getName(), orderDetail.getProduct().getPrice(), orderDetail.getAmount(), orderDetail.getSize().getName(), orderDetail.getProduct().getImageUrl()));
                totalOrderDetails = totalOrderDetails + ( orderDetail.getProduct().getPrice() * orderDetail.getAmount());
            }

            for(Donation donation : order.getDonations()){
                cartDonationDTOS.add(new CartDonationDTO(donation.getOrganization().getId(), donation.getOrganization().getOrganizationName(), donation.getOrganization().getImageUrl(), donation.getAmount()));
                totalDonations = totalDonations + donation.getAmount();
            }

            orderHistoryDTO.setCartProductDTOS(cartProductDTOS);
            orderHistoryDTO.setCartDonationDTOS(cartDonationDTOS);
            orderHistoryDTO.setTotal(totalDonations + totalOrderDetails);
            orderHistoryDTO.setAddress(order.getAddress());
            orderHistoryDTO.setCountry(order.getCountry());
            orderHistoryDTO.setPostal(order.getPostalCode());
            orderHistoryDTO.setDate(order.getDate());

            orderHistoryDTOs.add(orderHistoryDTO);
        }

        return  orderHistoryDTOs;
    }
}
