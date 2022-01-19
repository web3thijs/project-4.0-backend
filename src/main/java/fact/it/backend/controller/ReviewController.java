package fact.it.backend.controller;

import fact.it.backend.model.Review;
import fact.it.backend.repository.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RequestMapping(path = "api/reviews")
@RestController
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @PostConstruct
    public void fillDB(){
        if(reviewRepository.count() == 0){
            String customer1 = new ObjectId().toString();
            String customer2 = new ObjectId().toString();
            String product1 = new ObjectId().toString();
            String product2 = new ObjectId().toString();
            reviewRepository.save(new Review(product1, customer1, 4, "Good", ""));
            reviewRepository.save(new Review(product2, customer1, 4.5, "Nice product", "I liked it."));
            reviewRepository.save(new Review(product1, customer2, 3, "Nice product", "I liked it."));
        }
    }
}
