package fact.it.backend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String categoryId;
    private String organizationId;
    private String name;
    private Number price;
    private String description;
    private Boolean isActive;
    private String imageUrl;

    @DBRef
    private Collection<Stock> stock;

    @DBRef
    private Collection<OrderDetail> orderDetails;

    @DBRef
    private Collection<Interaction> interactions;

    public Product(){

    }

    public Product(String id, String categoryId, String organizationId, String name, Number price, String description, Boolean isActive, String imageUrl) {
        this.id = id;
        this.categoryId = categoryId;
        this.organizationId = organizationId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
