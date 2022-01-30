package fact.it.backend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;

@Document(collection = "colors")
public class Color implements Persistable<String> {

    @Id
    private String id;
    private String name;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @DBRef
    private Collection<OrderDetail> orderDetails;

    @DBRef
    private Collection<Stock> stocks;

    public Color(){

    }

    public Color(String name, Date createdAt){
        this.name = name;
        this.createdAt = createdAt;
    }

    public Color(String id, String name, Date createdAt){
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
