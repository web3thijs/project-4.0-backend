package fact.it.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderConfirmationDTO {
    private Long Id;
    private @NotNull List<CartProductDTO> cartProductDTOS;
    private @NotNull List<CartDonationDTO> cartDonationDTOS;
    private @NotNull String country;
    private @NotNull String postal;
    private @NotNull String address;

    public OrderConfirmationDTO() {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
