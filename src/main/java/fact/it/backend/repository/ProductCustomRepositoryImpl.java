package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    public Page<Product> findProductsByProperties(String categorie, String vzw, Double prijsgt, Double prijslt, Pageable page){
        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        if(categorie != null && !categorie.isEmpty())
            criteria.add(Criteria.where("category.name").is(categorie));
        if(vzw != null && !vzw.isEmpty())
            criteria.add(Criteria.where("organization.organizationName").is(vzw));
        if(prijsgt != null)
            criteria.add(Criteria.where("price").gt(prijsgt));
        if(prijslt != null)
            criteria.add(Criteria.where("price").lt(prijslt));

        if(!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return PageableExecutionUtils.getPage(
                products,
                page,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));
    }
}
