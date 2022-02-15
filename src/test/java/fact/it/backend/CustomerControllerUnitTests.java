
package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Category;
import fact.it.backend.model.Customer;
import fact.it.backend.model.Role;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.service.TokenGetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/customers")
@WithMockUser(username="admin", roles="ADMIN")
public class CustomerControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private CustomerRepository customerRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("elision@gmail.com")
    private String emailOrganizationAdmin;
    @Value("info@damiaanactie.be")
    private String emailOrganization;
    @Value("Password123")
    private String password;


    @Test
    public void whenGetAllCustomers_thenReturnJsonCustomer() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").ascending());

        Page<Customer> allCustomers = customerRepository.findByRole(Role.CUSTOMER, requestedPage);

        given(customerRepository.findByRole(Role.CUSTOMER, requestedPage)).willReturn(allCustomers);

        mockMvc.perform(get("/api/customers").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganizationAdmin, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllCustomersDesc_thenReturnJsonCustomer() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Customer> allCustomers = customerRepository.findByRole(Role.CUSTOMER, requestedPage);

        given(customerRepository.findByRole(Role.CUSTOMER, requestedPage)).willReturn(allCustomers);

        mockMvc.perform(get("/api/customers").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganizationAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllCustomersUnauthorized_thenReturnJsonCustomer() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Customer> allCustomers = customerRepository.findByRole(Role.CUSTOMER, requestedPage);

        given(customerRepository.findByRole(Role.CUSTOMER, requestedPage)).willReturn(allCustomers);

        mockMvc.perform(get("/api/customers").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }




    @Test
    public void whenGetCustomerById_thenReturnJsonCustomer() throws Exception{
        Customer customerTest = new Customer("jolienfoets@gmail.com", password, "0479994786", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");

        given(customerRepository.findByRoleAndId(Role.CUSTOMER,0)).willReturn(customerTest);

        mockMvc.perform(get("/api/customers/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganizationAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCustomerByIdUnauthorized_thenReturnForbidden() throws Exception{
        Customer customerTest = new Customer("jolienfoets@gmail.com", password, "0479994786", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");

        given(customerRepository.findByRoleAndId(Role.CUSTOMER,0)).willReturn(customerTest);

        mockMvc.perform(get("/api/customers/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    public void givenCustomer_whenPutCustomer_thenReturnJsonCustomer() throws Exception{
        Customer customerPut = new Customer(0,"jolienfoets@gmail.com", password, "0479994786", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");

        given(customerRepository.findByRoleAndId(Role.CUSTOMER, 0)).willReturn(customerPut);

        Customer updatedCustomer = new Customer(0,"jolienfoets@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");
        mockMvc.perform(put("/api/customers").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganizationAdmin, password))
                .content(mapper.writeValueAsString(updatedCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("jolienfoets@gmail.com")))
                .andExpect(jsonPath("$.phoneNr", is("0479994529")))
                .andExpect(jsonPath("$.address", is("Kersstraat 17")))
                .andExpect(jsonPath("$.postalCode", is("2200")))
                .andExpect(jsonPath("$.country", is("Belgium")))
                .andExpect(jsonPath("$.role", is("CUSTOMER")))
                .andExpect(jsonPath("$.firstName", is("Gianni")))
                .andExpect(jsonPath("$.lastName", is("De Herdt")));
    }

    @Test
    public void whenPutCustomerNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerPut = new Customer("jolienfoets@gmail.com", password, "0479994786", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, "Gianni" , "De Herdt");

        given(customerRepository.findByRoleAndId(Role.CUSTOMER, 0)).willReturn(customerPut);

        Customer updatedCustomer = new Customer("jolienfoets@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");


        mockMvc.perform(put("/api/customers").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .content(mapper.writeValueAsString(updatedCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenCustomer_whenDeleteCustomer_thenStatusOk() throws Exception {
        Customer customerToBeDeleted = new Customer(0,"jolienfoets@gmail.com", password, "0479994786", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");

        given(customerRepository.findByRoleAndId(Role.CUSTOMER, 0)).willReturn(customerToBeDeleted);

        mockMvc.perform(delete("/api/customers/{id}", customerToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganizationAdmin, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCustomer_whenDeleteCustomer_thenStatusNotFound() throws Exception {
        given(customerRepository.findByRoleAndId(Role.CUSTOMER, 12345)).willReturn(null);

        mockMvc.perform(delete("/api/customers/{id}", 12345789).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganizationAdmin, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteCustomerNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerToBeDeleted = new Customer(0,"jolienfoets@gmail.com", password, "0479994786", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni" , "De Herdt");
        given(customerRepository.findByRoleAndId(Role.CUSTOMER, 0)).willReturn(null);

        mockMvc.perform(delete("/api/customers/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}

