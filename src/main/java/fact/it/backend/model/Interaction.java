package fact.it.backend.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "interactions")
public class Interaction implements Persistable<String> {

    @Id
    private String id;
    private Product product;
    private Customer customer;
    private Review review;
    private Number amountClicks;
    private Number amountCart;
    private Number amountBought;
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public Interaction(){

    }

    public Interaction(Product product, Customer customer, Review review, Number amountClicks,  Number amountCart,  Number amountBought, Date createdAt) {
        this.product = product;
        this.customer = customer;
        this.review = review;
        this.amountClicks = amountClicks;
        this.amountCart = amountCart;
        this.amountBought = amountBought;
        this.createdAt = createdAt;
    }

    public Date getCreatedDate() {
        return createdAt;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdAt = createdDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Number getAmountClicks() {
        return amountClicks;
    }

    public void setAmountClicks(Number amountClicks) {
        this.amountClicks = amountClicks;
    }

    public Number getAmountCart() {
        return amountCart;
    }

    public void setAmountCart(Number amountCart) {
        this.amountCart = amountCart;
    }

    public Number getAmountBought() {
        return amountBought;
    }

    public void setAmountBought(Number amountBought) {
        this.amountBought = amountBought;
    }
}
