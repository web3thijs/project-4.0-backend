package fact.it.backend.dto;

import javax.validation.constraints.NotNull;

public class UpdateOrderDetailDTO {
    private Long id;
    private @NotNull Long productId;
    private @NotNull Long sizeId;
    private @NotNull Long colorId;
    private @NotNull int amount;

    public UpdateOrderDetailDTO() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
