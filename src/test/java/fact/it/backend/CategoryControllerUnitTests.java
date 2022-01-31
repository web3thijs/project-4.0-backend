package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Category;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.service.TokenGetService;
import org.bson.types.ObjectId;
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

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/categories")
@WithMockUser(username="admin", roles="ADMIN")
public class CategoryControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private CategoryRepository categoryRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;


   /* @Test
    public void whenGetAllCategories_thenReturnJsonCategory() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Category> allCategories = categoryRepository.findAll(requestedPage);

        given(categoryRepository.findAll(requestedPage)).willReturn(allCategories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllCategoriesWithParams_thenReturnJsonCategory() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Category> allCategoriesWithParams = categoryRepository.findAll(requestedPage);

        given(categoryRepository.findAll(requestedPage)).willReturn(allCategoriesWithParams);

        mockMvc.perform(get("/api/categories?page=0&sort=name&order=desc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));

    }*/

    @Test
    public void whenGetCategoryById_thenReturnJsonCategory() throws Exception{
        Category categoryTest = new Category(new ObjectId().toString(),"kleren", new Date());

        given(categoryRepository.findCategoryById(categoryTest.getId())).willReturn(categoryTest);

        mockMvc.perform(get("/api/categories/{id}", categoryTest.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("kleren")));
    }

    @Test
    public void whenPostCategory_thenReturnJsonCategory() throws Exception{
        Category categoryPost = new Category( new ObjectId().toString(),"schoenen", new Date());

        mockMvc.perform(post("/api/categories").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .content(mapper.writeValueAsString(categoryPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("schoenen")));
    }

    @Test
    public void whenPostCategoryNotAuthorized_thenReturnForbidden() throws Exception{
        Category categoryPost = new Category( new ObjectId().toString(),"schoenen", new Date());

        mockMvc.perform(post("/api/categories").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                .content(mapper.writeValueAsString(categoryPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenCategory_whenPutCategory_thenReturnJsonCategory() throws Exception{
        Date date = new Date();
        Category categoryPut = new Category(new ObjectId().toString(), "penen", date);

        given(categoryRepository.findCategoryById(categoryPut.getId())).willReturn(categoryPut);

        Category updatedCategory = new Category(categoryPut.getId(),"pennen", date);

        mockMvc.perform(put("/api/categories").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .content(mapper.writeValueAsString(updatedCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("pennen")));
    }

    @Test
    public void whenPutCategoryNotAuthorized_thenReturnForbidden() throws Exception{
        Date date = new Date();
        Category categoryPut = new Category(new ObjectId().toString(), "penen", date);

        given(categoryRepository.findCategoryById(categoryPut.getId())).willReturn(categoryPut);

        Category updatedCategory = new Category(categoryPut.getId(),"pennen", date);

        mockMvc.perform(put("/api/categories").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                .content(mapper.writeValueAsString(updatedCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        }

    @Test
    public void givenCategory_whenDeleteCategory_thenStatusOk() throws Exception {
        String id = new Object().toString();
        Category categoryToBeDeleted = new Category(id,"kettingen", new Date());

        given(categoryRepository.findCategoryById(id)).willReturn(categoryToBeDeleted);

        mockMvc.perform(delete("/api/categories/{id}", id).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCategory_whenDeleteCategory_thenStatusNotFound() throws Exception {
        given(categoryRepository.findCategoryById("XXX")).willReturn(null);

        mockMvc.perform(delete("/api/categories/{id}", "XXX").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteCategoryNotAuthorized_thenReturnForbidden() throws Exception{
        given(categoryRepository.findCategoryById("XXX")).willReturn(null);

        mockMvc.perform(delete("/api/categories/{id}", "XXX").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}