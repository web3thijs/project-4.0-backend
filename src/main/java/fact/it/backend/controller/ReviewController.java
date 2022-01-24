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

    @GetMapping("")
    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable String id){
        return reviewRepository.findReviewById(id);
    }

    @PostMapping("")
    public Review addReview(@RequestBody Review review){
        reviewRepository.save(review);
        return review;
    }

    @PutMapping("")
    public Review updateReview(@RequestBody Review updatedReview){
        Review retrievedReview = reviewRepository.findReviewById(updatedReview.getId());

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
