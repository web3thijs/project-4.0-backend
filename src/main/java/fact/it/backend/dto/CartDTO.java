package fact.it.backend.dto;

import fact.it.backend.model.OrderDetail;
import fact.it.backend.model.Product;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartDTO {
    private Long id;
    private @NotNull List<CartProductDTO> cartProductDTOS;
    private @NotNull List<CartDonationDTO> cartDonationDTOS;
    private @NotNull double total;

    public CartDTO() {
    }

    public List<CartProductDTO> getCartProductDTOS() {
        return cartProductDTOS;
    }

    public void setCartProductDTOS(List<CartProductDTO> cartProductDTOS) {
        this.cartProductDTOS = cartProductDTOS;
    }

    public List<CartDonationDTO> getCartDonationDTOS() {
        return cartDonationDTOS;
    }

    public void setCartDonationDTOS(List<CartDonationDTO> cartDonationDTOS) {
        this.cartDonationDTOS = cartDonationDTOS;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

