package fact.it.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartProductDTO {
    private Long id;
    private @NotNull Long productId;
    private @NotNull Long sizeId;
    private @NotNull String productName;
    private @NotNull Double productPrice;
    private @NotNull int amount;
    private @NotNull String sizeName;
    private @NotNull List<String> imgUrl;

    public CartProductDTO() {
    }

    public CartProductDTO(Long productId, Long sizeId, String productName, Double productPrice, int amount, String sizeName, List<String> imgUrl) {
        this.productId = productId;
        this.sizeId = sizeId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.amount = amount;
        this.sizeName = sizeName;
        this.imgUrl = imgUrl;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }
}
