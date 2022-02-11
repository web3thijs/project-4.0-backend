package fact.it.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CompleteOrderDTO {
    private Long id;
    private @NotNull String country;
    private @NotNull String postal;
    private @NotNull String address;

    public CompleteOrderDTO() {
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
