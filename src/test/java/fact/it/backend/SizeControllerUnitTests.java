package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Customer;
import fact.it.backend.model.Size;
import fact.it.backend.model.Role;
import fact.it.backend.repository.SizeRepository;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/sizes")
@WithMockUser(username="admin", roles="ADMIN")
public class SizeControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private SizeRepository sizeRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("supporters@wwf.be")
    private String emailOrganization;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;


   /* @Test
    public void whenGetAllSizes_thenReturnJsonSize() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Size> allSizes = sizeRepository.findAll(requestedPage);

        given(sizeRepository.findAll(requestedPage)).willReturn(allSizes);

        mockMvc.perform(get("/api/sizes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllSizesWithParams_thenReturnJsonSize() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Size> allSizesWithParams = sizeRepository.findAll(requestedPage);

        given(sizeRepository.findAll(requestedPage)).willReturn(allSizesWithParams);

        mockMvc.perform(get("/api/sizes?page=0&sort=name&order=desc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));

    }*/

    @Test
    public void whenGetSizeById_thenReturnJsonSize() throws Exception{
        Size sizeTest = new Size(0, "Medium");

        given(sizeRepository.findSizeById(0)).willReturn(sizeTest);

        mockMvc.perform(get("/api/sizes/{id}", 0))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("Medium")));
    }


    @Test
    public void whenPostSize_thenReturnJsonSize() throws Exception{
        Size sizePost = new Size(0, "Medium");

        mockMvc.perform(post("/api/sizes").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(sizePost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("Medium")));
    }

    @Test
    public void whenPostSizeNotAuthorized_thenReturnForbidden() throws Exception{
        Size sizePost = new Size("Medium");

        mockMvc.perform(post("/api/sizes").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(sizePost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenSize_whenDeleteSize_thenStatusOk() throws Exception {
        Size sizeToBeDeleted = new Size("Medium");

        given(sizeRepository.findSizeById(0)).willReturn(sizeToBeDeleted);

        mockMvc.perform(delete("/api/sizes/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenSize_whenDeleteSize_thenStatusNotFound() throws Exception {
        given(sizeRepository.findSizeById(12345)).willReturn(null);

        mockMvc.perform(delete("/api/sizes/{id}", 12345).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteSizeNotAuthorized_thenReturnForbidden() throws Exception{
        Size sizeToBeDeleted = new Size("Medium");

        given(sizeRepository.findSizeById(0)).willReturn(sizeToBeDeleted);

        mockMvc.perform(delete("/api/sizes/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
