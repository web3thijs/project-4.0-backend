package fact.it.backend.model;

import javax.persistence.*;

@Entity
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int amountClicks;
    private int amountCart;
    private int amountBought;

    @OneToOne
    private Review review;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @OneToOne
    private Customer customer;

    public Interaction() {
    }

    public Interaction(int amountClicks, int amountCart, int amountBought, Review review, Product product, Customer customer) {
        this.amountClicks = amountClicks;
        this.amountCart = amountCart;
        this.amountBought = amountBought;
        this.review = review;
        this.product = product;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public int getAmountClicks() {
        return amountClicks;
    }

    public void setAmountClicks(int amountClicks) {
        this.amountClicks = amountClicks;
    }

    public int getAmountCart() {
        return amountCart;
    }

    public void setAmountCart(int amountCart) {
        this.amountCart = amountCart;
    }

    public int getAmountBought() {
        return amountBought;
    }

    public void setAmountBought(int amountBought) {
        this.amountBought = amountBought;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
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
}
