package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "donations")
public class Donation {

    @Id
    private String id;
    private Product product;
    private Organization organization;
    private Number amount;

    public Donation() {
    }

    public Donation(Product product, Organization organization, Number amount) {
        this.product = product;
        this.organization = organization;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }
}
