package fact.it.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartDonationDTO {
    private Long id;
    private Long donationId;
    private @NotNull String organizationName;
    private @NotNull double amount;

    public CartDonationDTO() {
    }

    public CartDonationDTO(Long donationId, String organizationName, double amount) {
        this.donationId = donationId;
        this.organizationName = organizationName;
        this.amount = amount;
    }

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
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
