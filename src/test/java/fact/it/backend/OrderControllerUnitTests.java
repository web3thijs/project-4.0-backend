package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Customer;
import fact.it.backend.model.Order;
import fact.it.backend.model.Role;
import fact.it.backend.repository.OrderRepository;
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

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/orders")
@WithMockUser(username="admin", roles="ADMIN")
public class OrderControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private OrderRepository orderRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("supporters@wwf.be")
    private String emailOrganization;
    @Value("Password123")
    private String password;

     @Test
    public void whenGetAllOrders_thenReturnJsonOrder() throws Exception{
         List<Order> orderHistory = orderRepository.findAll();

        given(orderRepository.findAll()).willReturn(orderHistory);

        mockMvc.perform(get("/api/orders").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllOrdersUnauthorized_thenReturnJsonOrder() throws Exception{
        List<Order> orderHistory = orderRepository.findAll();

        given(orderRepository.findAll()).willReturn(orderHistory);

        mockMvc.perform(get("/api/orders").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetOrderById_thenReturnJsonOrder() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderTest = new Order(new Date(), true, customerThijsWouters);

        given(orderRepository.findById(orderTest.getId())).willReturn(Optional.of(orderTest));

        mockMvc.perform(get("/api/orders/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.completed", is(true)))
                .andExpect(jsonPath("$.customer.email", is("thijswouters@gmail.com")));
    }

    @Test
    public void whenGetOrderByIdUnauthorized_thenReturnForbidden() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderTest = new Order(new Date(), true, customerThijsWouters);

        given(orderRepository.findById(orderTest.getId())).willReturn(Optional.of(orderTest));

        mockMvc.perform(get("/api/orders/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetOrderByCustomerId_thenReturnIsOk() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderTest = new Order(new Date(), true, customerThijsWouters);

        given(orderRepository.findOrdersByCustomerId(0)).willReturn(Arrays.asList(orderTest));

        mockMvc.perform(get("/api/orders/customer/{customerId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

/*    @Test
    public void whenGetOrderByCustomerIdNone_thenReturnNotFound() throws Exception{
        given(orderRepository.findOrdersByCustomerId(0)).willReturn(null);

        mockMvc.perform(get("/api/orders/customer/{customerId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }*/


    @Test
    public void whenGetOrderByCustomerIdUnauthorized_thenReturnForbidden() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderTest = new Order(new Date(), true, customerThijsWouters);

        given(orderRepository.findOrdersByCustomerId(0)).willReturn(Arrays.asList(orderTest));

        mockMvc.perform(get("/api/orders/customer/{customerId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    public void whenPostOrder_thenReturnJsonOrder() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderPost = new Order(new Date(), true, customerThijsWouters);


        mockMvc.perform(post("/api/orders").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(orderPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.completed", is(true)))
                .andExpect(jsonPath("$.customer.email", is("thijswouters@gmail.com")));
    }

    @Test
    public void whenPostOrderNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderPost = new Order(new Date(), true, customerThijsWouters);

        mockMvc.perform(post("/api/orders").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(orderPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenOrder_whenPutOrder_thenReturnJsonOrder() throws Exception{
        Date date = new Date();
        Customer customerThijsWouters = new Customer(2,"thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderPut = new Order(date, false, customerThijsWouters);

        given(orderRepository.findById(orderPut.getId())).willReturn(Optional.of(orderPut));

        Order updatedOrder = new Order(date, true, customerThijsWouters);

        mockMvc.perform(put("/api/orders").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(updatedOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPutOrderNotAuthorized_thenReturnForbidden() throws Exception{
        Date date = new Date();
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderPut = new Order(date, false, customerThijsWouters);

        given(orderRepository.findById(orderPut.getId())).willReturn(Optional.of(orderPut));

        Order updatedOrder = new Order(date, true, customerThijsWouters);

        mockMvc.perform(put("/api/orders").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(updatedOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenOrder_whenDeleteOrder_thenStatusOk() throws Exception {
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderToBeDeleted = new Order(new Date(), false, customerThijsWouters);

        given(orderRepository.findById(orderToBeDeleted.getId())).willReturn(Optional.of(orderToBeDeleted));

        mockMvc.perform(delete("/api/orders/{id}", orderToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrder_whenDeleteOrder_thenStatusNotFound() throws Exception {
        given(orderRepository.findOrderById(12345)).willReturn(null);

        mockMvc.perform(delete("/api/orders/{id}", 123456789).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteOrderNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Order orderToBeDeleted = new Order(new Date(), false, customerThijsWouters);


        given(orderRepository.findOrderById(orderToBeDeleted.getId())).willReturn(null);

        mockMvc.perform(delete("/api/orders/{id}", orderToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
