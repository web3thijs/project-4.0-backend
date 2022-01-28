package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Customer;
import fact.it.backend.model.Role;
import fact.it.backend.repository.CustomerRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import static org.hamcrest.Matchers.is;
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

    @MockBean
    private CustomerRepository customerRepository;

    private ObjectMapper mapper = new ObjectMapper();

    /*@Test
    public void whenGetAllCustomers_thenReturnJsonCustomer() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Customer> allCustomers = customerRepository.findAll(requestedPage);

        given(customerRepository.findAll(requestedPage)).willReturn(allCustomers);

        mockMvc.perform(get("/api/customers"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllCustomersWithParams_thenReturnJsonCustomer() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Customer> allCustomersWithParams = customerRepository.findAll(requestedPage);

        given(customerRepository.findAll(requestedPage)).willReturn(allCustomersWithParams);

        mockMvc.perform(get("/api/customers?page=0&sort=name&order=desc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));

    }*/

    @Test
    public void whenGetCustomerById_thenReturnJsonCustomer() throws Exception{
        String password = new BCryptPasswordEncoder().encode("Password123");
        Date date = new Date();
        Customer customerTest = new Customer("giannideherdt@gmail.com", password, "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");

        given(customerRepository.findByRoleAndId(Role.CUSTOMER,customerTest.getId())).willReturn(customerTest);

        mockMvc.perform(get("/api/customers/{id}", customerTest.getId()).header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("giannideherdt@gmail.com")))
                .andExpect(jsonPath("$.phoneNr", is("0479994529")))
                .andExpect(jsonPath("$.address", is("Kersstraat 17")))
                .andExpect(jsonPath("$.postalCode", is("2200")))
                .andExpect(jsonPath("$.country", is("Belgium")))
                .andExpect(jsonPath("$.role", is("CUSTOMER")))
                .andExpect(jsonPath("$.date", is(date)))
                .andExpect(jsonPath("$.firstName", is("Gianni")))
                .andExpect(jsonPath("$.lastName", is("De Herdt")));
    }

    @Test
    public void whenPostCustomer_thenReturnJsonCustomer() throws Exception{
        String password = new BCryptPasswordEncoder().encode("Password123");
        Date date = new Date();
        Customer customerPost = new Customer("giannideherdt@gmail.com", password, "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");


        mockMvc.perform(post("/api/customers").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
                .content(mapper.writeValueAsString(customerPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("giannideherdt@gmail.com")))
                .andExpect(jsonPath("$.phoneNr", is("0479994529")))
                .andExpect(jsonPath("$.address", is("Kersstraat 17")))
                .andExpect(jsonPath("$.postalCode", is("2200")))
                .andExpect(jsonPath("$.country", is("Belgium")))
                .andExpect(jsonPath("$.role", is("CUSTOMER")))
                .andExpect(jsonPath("$.date", is(date)))
                .andExpect(jsonPath("$.firstName", is("Gianni")))
                .andExpect(jsonPath("$.lastName", is("De Herdt")));
    }

    @Test
    public void whenPostCustomerNotAuthorized_thenReturnForbidden() throws Exception{
        String password = new BCryptPasswordEncoder().encode("Password123");
        Date date = new Date();
        Customer customerPost = new Customer("giannideherdt@gmail.com", password, "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");

        mockMvc.perform(post("/api/customers").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWNjb3VudEBnbWFpbC5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJ1c2VyX2lkIjoiNjFmMjZlMzkzYTQzYWQ1NGU1MWI2MmIxIiwiZXhwIjoxNjQzNTQwMjk4LCJpYXQiOjE2NDMzNjc0OTh9.5nr_doX1g42mjzunRcqg_vg20Gjj43G79NIUGV6Nz8o")
                .content(mapper.writeValueAsString(customerPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

//    @Test
//    public void givenCustomer_whenPutCustomer_thenReturnJsonCustomer() throws Exception{
//        Date date = new Date();
//        Customer customerPut = new Customer("giannideberdt@gmail.com", "Password123", "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");
//
//        given(customerRepository.findByRoleAndId(Role.CUSTOMER, customerPut.getId())).willReturn(customerPut);
//
//        Customer updatedCustomer = new Customer("giannideherdt@gmail.com", "Password123", "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");
//
//        mockMvc.perform(put("/api/customers").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
//                .content(mapper.writeValueAsString(updatedCustomer))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is("giannideherdt@gmail.com")))
//                .andExpect(jsonPath("$.phoneNr", is("0479994529")))
//                .andExpect(jsonPath("$.address", is("Kersstraat 17")))
//                .andExpect(jsonPath("$.postalCode", is("2200")))
//                .andExpect(jsonPath("$.country", is("Belgium")))
//                .andExpect(jsonPath("$.role", is("CUSTOMER")))
//                .andExpect(jsonPath("$.date", is(date)))
//                .andExpect(jsonPath("$.firstName", is("Gianni")))
//                .andExpect(jsonPath("$.lastName", is("De Herdt")));
//    }

//    @Test
//    public void whenPutCustomerNotAuthorized_thenReturnForbidden() throws Exception{
//        String password = new BCryptPasswordEncoder().encode("Password123");
//        Date date = new Date();
//        Customer customerPut = new Customer("giannideberdt@gmail.com", password, "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");
//
//        given(customerRepository.findByRoleAndId(Role.CUSTOMER, customerPut.getId())).willReturn(customerPut);
//
//        Customer updatedCustomer = new Customer("giannideherdt@gmail.com", password, "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");
//
//
//        mockMvc.perform(put("/api/customers").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWNjb3VudEBnbWFpbC5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJ1c2VyX2lkIjoiNjFmMjZlMzkzYTQzYWQ1NGU1MWI2MmIxIiwiZXhwIjoxNjQzNTQwMjk4LCJpYXQiOjE2NDMzNjc0OTh9.5nr_doX1g42mjzunRcqg_vg20Gjj43G79NIUGV6Nz8o")
//                .content(mapper.writeValueAsString(updatedCustomer))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//    }

    @Test
    public void givenCustomer_whenDeleteCustomer_thenStatusOk() throws Exception {
        String id = new Object().toString();
        String password = new BCryptPasswordEncoder().encode("Password123");
        Date date = new Date();
        Customer customerToBeDeleted = new Customer("giannideberdt@gmail.com", password, "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER, date, "Gianni" , "De Herdt");

        given(customerRepository.findByRoleAndId(Role.CUSTOMER, customerToBeDeleted.getId())).willReturn(customerToBeDeleted);

        mockMvc.perform(delete("/api/customers/{id}", customerToBeDeleted.getId()).header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCustomer_whenDeleteCustomer_thenStatusNotFound() throws Exception {
        given(customerRepository.findByRoleAndId(Role.CUSTOMER, "XXX")).willReturn(null);

        mockMvc.perform(delete("/api/customers/{id}", Role.CUSTOMER,"XXX").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteCustomerNotAuthorized_thenReturnForbidden() throws Exception{
        given(customerRepository.findByRoleAndId(Role.CUSTOMER, "XXX")).willReturn(null);

        mockMvc.perform(delete("/api/customers/{id}", Role.CUSTOMER, "XXX").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWNjb3VudEBnbWFpbC5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJ1c2VyX2lkIjoiNjFmMjZlMzkzYTQzYWQ1NGU1MWI2MmIxIiwiZXhwIjoxNjQzNTQwMjk4LCJpYXQiOjE2NDMzNjc0OTh9.5nr_doX1g42mjzunRcqg_vg20Gjj43G79NIUGV6Nz8o")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
