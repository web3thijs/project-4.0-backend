package fact.it.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartDonationDTO {
    private Long id;
    private Long organizationId;
    private @NotNull String organizationName;
    private @NotNull double amount;

    public CartDonationDTO() {
    }

    public CartDonationDTO(Long organizationId, String organizationName, double amount) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.amount = amount;
    }

    public Long getorganizationId() {
        return organizationId;
    }

    public void setorganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
