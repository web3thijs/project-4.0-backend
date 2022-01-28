package fact.it.backend.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "donations")
public class Donation implements Persistable<String> {

    @Id
    private String id;
    private Order order;
    private Organization organization;
    private Number amount;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    public Donation() {
    }

    public Donation(Order order, Organization organization, Number amount, Date createdAt) {
        this.order = order;
        this.organization = organization;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
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

    @Override
    public boolean isNew() {
        return false;
    }

}
