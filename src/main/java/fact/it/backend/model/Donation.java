package fact.it.backend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double amount;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    public Donation() {
    }

    public Donation(double amount, Order order, Organization organization) {
        this.amount = amount;
        this.order = order;
        this.organization = organization;
    }



    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
