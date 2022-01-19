package fact.it.backend.controller;

import fact.it.backend.model.Review;
import fact.it.backend.repository.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

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
        System.out.println("DB test reviews: " + reviewRepository.findAll().size() + " reviews.");

    }

    @GetMapping("")
    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    @GetMapping("/reviews/product/{productId}")
    public List<Review> findReviewsByProductId(@PathVariable String productId){
        return reviewRepository.findReviewsByProductId(productId);
    }

    @GetMapping("/reviews/customer/{customerId}")
    public List<Review> findReviewsByCustomerId(@PathVariable String customerId){
        return reviewRepository.findReviewsByCustomerId(customerId);
    }

    @PostMapping("")
    public Review addReview(@RequestBody Review review){
        reviewRepository.save(review);
        return review;
    }

    @PutMapping("")
    public Review updateReview(@RequestBody Review updatedReview){
        Review retrievedReview = reviewRepository.findReviewById(updatedReview.getId());

        retrievedReview.setProductId(updatedReview.getProductId());
        retrievedReview.setCustomerId(updatedReview.getCustomerId());
        retrievedReview.setScore(updatedReview.getScore());
        retrievedReview.setTitle(updatedReview.getTitle());
        retrievedReview.setText(updatedReview.getText());

        reviewRepository.save(retrievedReview);

        return retrievedReview;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable String id){
        Review review = reviewRepository.findReviewById(id);

        if(review != null){
            reviewRepository.delete(review);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
