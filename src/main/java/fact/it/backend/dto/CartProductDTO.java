package fact.it.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartProductDTO {
    private Long id;
    private @NotNull String productName;
    private @NotNull Double productPrice;
    private @NotNull int amount;
    private @NotNull String sizeName;
    private @NotNull List<String> imgUrl;

    public CartProductDTO() {
    }

    public CartProductDTO(String productName, Double productPrice, int amount, String sizeName, List<String> imgUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.amount = amount;
        this.sizeName = sizeName;
        this.imgUrl = imgUrl;
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
