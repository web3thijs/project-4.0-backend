package fact.it.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;
    private boolean isActive;

    @ElementCollection
    private List<String> imageUrl;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Interaction> interactions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Stock> stocks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String description, double price, boolean isActive, List<String> imageUrl, Category category, Organization organization) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.category = category;
        this.organization = organization;
    }
    public Product(long id, String name, String description, double price, boolean isActive, List<String> imageUrl, Category category, Organization organization) {
        this.id =id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.category = category;
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
