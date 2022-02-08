package fact.it.backend.service;

import fact.it.backend.dto.CartDTO;
import fact.it.backend.dto.CartDonationDTO;
import fact.it.backend.dto.CartProductDTO;
import fact.it.backend.model.Donation;
import fact.it.backend.model.Order;
import fact.it.backend.model.OrderDetail;
import fact.it.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    OrderRepository orderRepository;

    public CartService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CartDTO getCart(Long userId){
        Order order = orderRepository.findOrdersByCustomerIdAndCompleted(userId, false);
        CartDTO cartDTO = new CartDTO();
        List<CartProductDTO> cartProductDTOS = new ArrayList<>();
        List<CartDonationDTO> cartDonationDTOS = new ArrayList<>();

        for(OrderDetail orderDetail : order.getOrderDetails()){
            cartProductDTOS.add(new CartProductDTO(orderDetail.getProduct().getId(), orderDetail.getProduct().getName(), orderDetail.getProduct().getPrice(), orderDetail.getAmount(), orderDetail.getSize().getName(), orderDetail.getProduct().getImageUrl()));
        }

        for(Donation donation : order.getDonations()){
            cartDonationDTOS.add(new CartDonationDTO(donation.getId(), donation.getOrganization().getOrganizationName(), donation.getAmount()));
        }

        cartDTO.setCartProductDTOS(cartProductDTOS);
        cartDTO.setCartDonationDTOS(cartDonationDTOS);
        return cartDTO;
    }
}
