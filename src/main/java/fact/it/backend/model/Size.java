package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "sizes")
public class Size {

    @Id
    private String id;
    private String name;

    @DBRef
    private Collection<OrderDetail> orderDetails;

    @DBRef
    private Collection<Stock> stocks;

    public Size(){

    }

    public Size(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
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
