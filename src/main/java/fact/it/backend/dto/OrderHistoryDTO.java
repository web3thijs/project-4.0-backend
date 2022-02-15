package fact.it.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class OrderHistoryDTO {
    private Long Id;
    private @NotNull List<CartProductDTO> cartProductDTOS;
    private @NotNull List<CartDonationDTO> cartDonationDTOS;
    private @NotNull double total;
    private @NotNull String country;
    private @NotNull String postal;
    private @NotNull String address;
    private @NotNull Date date;

    public OrderHistoryDTO() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
