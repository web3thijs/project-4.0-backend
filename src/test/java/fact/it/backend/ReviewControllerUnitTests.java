package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.*;
import fact.it.backend.repository.ReviewRepository;
import fact.it.backend.service.TokenGetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/reviews")
@WithMockUser(username="admin", roles="ADMIN")
public class ReviewControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private ReviewRepository reviewRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("supporters@wwf.be")
    private String emailOrganization;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;


    @Test
    public void whenGetAllReviews_thenReturnJsonReview() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Review> allReviews = reviewRepository.findAll(requestedPage);

        given(reviewRepository.findAll(requestedPage)).willReturn(allReviews);

        mockMvc.perform(get("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void whenGetReviewById_thenReturnJsonReview() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewTest = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        given(reviewRepository.findById(reviewTest.getId())).willReturn(Optional.of(reviewTest));

        mockMvc.perform(get("/api/reviews/{id}", reviewTest.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.score", is(5.0)))
                .andExpect(jsonPath("$.title", is("Zeer leuke sleutelhanger")))
                .andExpect(jsonPath("$.text", is("Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!")))
                .andExpect(jsonPath("$.customer.email", is("giannideherdt@gmail.com")));
    }

    @Test
    public void whenPostReview_thenReturnJsonReview() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewPost = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        mockMvc.perform(post("/api/reviews").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(reviewPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.score", is(5.0)))
                .andExpect(jsonPath("$.title", is("Zeer leuke sleutelhanger")))
                .andExpect(jsonPath("$.text", is("Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!")))
                .andExpect(jsonPath("$.customer.email", is("giannideherdt@gmail.com")));
    }

    @Test
    public void whenPostReviewNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewPost = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        mockMvc.perform(post("/api/reviews").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(reviewPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenReview_whenPutReview_thenReturnJsonReview() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewPut = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        given(reviewRepository.findById(reviewPut.getId())).willReturn(Optional.of(reviewPut));

        Review updatedReview = new Review( 4.5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        mockMvc.perform(put("/api/reviews").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(updatedReview))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.score", is(4.5)))
                .andExpect(jsonPath("$.title", is("Zeer leuke sleutelhanger")))
                .andExpect(jsonPath("$.text", is("Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!")))
                .andExpect(jsonPath("$.customer.email", is("giannideherdt@gmail.com")));
    }

    @Test
    public void whenPutReviewNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewPut = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        given(reviewRepository.findReviewById(reviewPut.getId())).willReturn(reviewPut);

        Review updatedReview = new Review( 4.5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        mockMvc.perform(put("/api/reviews").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(updatedReview))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenReview_whenDeleteReview_thenStatusOk() throws Exception {
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewToBeDeleted = new Review( 4, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        given(reviewRepository.findById(reviewToBeDeleted.getId())).willReturn(Optional.of(reviewToBeDeleted));

        mockMvc.perform(delete("/api/reviews/{id}", reviewToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenReview_whenDeleteReview_thenStatusNotFound() throws Exception {
        given(reviewRepository.findReviewById(12345)).willReturn(null);

        mockMvc.perform(delete("/api/reviews/{id}", 12345).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteReviewNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewToBeDeleted = new Review( 4, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);

        given(reviewRepository.findReviewById(reviewToBeDeleted.getId())).willReturn(null);

        mockMvc.perform(delete("/api/reviews/{id}", reviewToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
