package fact.it.backend.dto;

import javax.validation.constraints.NotNull;

public class AddToInteractionDTO {
    private Long id;
    private @NotNull Long customerId;
    private @NotNull Long productId;

    public AddToInteractionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
