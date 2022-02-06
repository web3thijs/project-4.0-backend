package fact.it.backend.service;

import fact.it.backend.dto.CartDTO;
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

        for(OrderDetail orderDetail : order.getOrderDetails()){
            cartProductDTOS.add(new CartProductDTO(orderDetail.getProduct().getName(), orderDetail.getProduct().getPrice(), orderDetail.getAmount(), orderDetail.getSize().getName(), orderDetail.getProduct().getImageUrl()));
        }

        cartDTO.setCartProductDTOS(cartProductDTOS);
        return cartDTO;
    }
}
