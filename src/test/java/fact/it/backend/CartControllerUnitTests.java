package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.dto.CartDTO;
import fact.it.backend.model.Interaction;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void whenGetCart_thenReturnJsonCart() throws Exception {
//        CartDTO cart = new CartDTO();
//
//        given(cartService.getCart(0L)).willReturn(cart);

        mockMvc.perform(get("/api/cart").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

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
        input.put("id", 0);
        input.put("organizationId", 1);
        input.put("amount", 15);
        mockMvc.perform(post("/api/cart/updateDonation").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
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
}
