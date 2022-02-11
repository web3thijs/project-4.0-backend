package fact.it.backend.dto;

import javax.validation.constraints.NotNull;

public class UpdateDonationDTO {
    private Long id;
    private @NotNull Long organizationId;
    private @NotNull int amount;

    public UpdateDonationDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}