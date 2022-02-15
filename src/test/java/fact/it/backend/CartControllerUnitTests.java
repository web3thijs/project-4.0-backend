package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.dto.CartDTO;
import fact.it.backend.dto.CartProductDTO;
import fact.it.backend.model.Customer;
import fact.it.backend.model.Interaction;
import fact.it.backend.model.Order;
import fact.it.backend.model.Role;
import fact.it.backend.service.CartService;
import fact.it.backend.service.TokenGetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/donations")
@WithMockUser(username = "admin", roles = "ADMIN")
public class CartControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    CartService cartService;

    @Autowired
    private TokenGetService tokenGetService;

    private ObjectMapper mapper = new ObjectMapper();
    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("info@bkks.be")
    private String emailOrganization;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;

/*    @Test
    public void whenGetCart_thenReturnJsonCart() throws Exception {
        Customer customerTest = new Customer(0,"jolienfoets@gmail.com", password, "0479994786", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");
        Order order = new Order(new Date(), false, customerTest);
        CartDTO cart = cartService.getCart(0L);

        assertThat(cart.getCartProductDTOS().isEmpty());

    }*/

    @Test
    public void whenGetCartUnauthorized_thenReturnForbidden() throws Exception {
        CartDTO cart = new CartDTO();

        given(cartService.getCart(0L)).willReturn(cart);

        mockMvc.perform(get("/api/cart").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenAddProduct_thenReturnOk() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("productId", 1);
        input.put("sizeId", 1);
        input.put("colorId", 1);
        input.put("amount", 2);
        mockMvc.perform(post("/api/cart/addProduct").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddProductUnauthorized_thenReturnForbidden() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("productId", 1);
        input.put("sizeId", 1);
        input.put("colorId", 1);
        input.put("amount", 2);
        mockMvc.perform(post("/api/cart/addProduct").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenUpdateProduct_thenReturnOk() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("productId", 1);
        input.put("sizeId", 1);
        input.put("colorId", 1);
        input.put("amount", 2);
        mockMvc.perform(post("/api/cart/updateProduct").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateProductUnauthorized_thenReturnForbidden() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("productId", 1);
        input.put("sizeId", 1);
        input.put("colorId", 1);
        input.put("amount", 2);
        mockMvc.perform(post("/api/cart/updateProduct").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenAddDonation_thenReturnOk() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("organizationId", 1);
        input.put("amount", 10);
        mockMvc.perform(post("/api/cart/addDonation").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddDonationUnauthorized_thenReturnForbidden() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("organizationId", 1);
        input.put("amount", 10);
        mockMvc.perform(post("/api/cart/addDonation").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

/*    @Test
    public void whenUpdateDonation_thenReturnOk() throws Exception{
        Map<String,Object> input=new HashMap<>();
        input.put("organizationId", 1);
        input.put("amount", 15L);
        mockMvc.perform(post("/api/cart/updateDonation").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }*/

    @Test
    public void whenUpdateDonationUnauthorized_thenReturnForbidden() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("organizationId", 1);
        input.put("amount", 15);
        mockMvc.perform(post("/api/cart/updateDonation").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenCompleteOrder_thenReturnOk() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("country", "Belgium");
        input.put("postal", "2260");
        input.put("address", "Test");
        mockMvc.perform(post("/api/cart/completeOrder").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCompleteOrderUnauthorized_thenReturnForbidden() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("country", "Belgium");
        input.put("postal", "2260");
        input.put("address", "Test");
        mockMvc.perform(post("/api/cart/completeOrder").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetCompleted_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/cart/completed").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCompletedUnauthorized_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/cart/completed").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
