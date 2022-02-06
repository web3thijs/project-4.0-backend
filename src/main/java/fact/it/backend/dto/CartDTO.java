package fact.it.backend.dto;

import fact.it.backend.model.OrderDetail;
import fact.it.backend.model.Product;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartDTO {
    private Long id;
    private @NotNull List<CartProductDTO> cartProductDTOS;

    public CartDTO() {
    }

    public List<CartProductDTO> getCartProductDTOS() {
        return cartProductDTOS;
    }

    public void setCartProductDTOS(List<CartProductDTO> cartProductDTOS) {
        this.cartProductDTOS = cartProductDTOS;
    }
}

