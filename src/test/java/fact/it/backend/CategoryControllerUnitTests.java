package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.controller.CategoryController;
import fact.it.backend.model.Category;
import fact.it.backend.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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

    @MockBean
    private CategoryRepository categoryRepository;

    private ObjectMapper mapper = new ObjectMapper();

    /*@Test
    public void whenGetAllCategories_thenReturnJsonEvent() throws Exception{
        List<Category> allCategories = categoryRepository.findAll();

        given(categoryRepository.findAll()).willReturn(allCategories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }*/

    @Test
    public void whenGetCategoryById_thenReturnJsonEvent() throws Exception{
        Category categoryTest = new Category(new ObjectId().toString(),"kleren", new Date());

        given(categoryRepository.findCategoryById(categoryTest.getId())).willReturn(categoryTest);

        mockMvc.perform(get("/api/categories/{id}", categoryTest.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("kleren")));
    }

    /*@Test
    public void whenPostCategory_thenReturnJsonEvent() throws Exception{
        Category categoryPost = new Category(new ObjectId().toString(), "schoenen", new Date());


        mockMvc.perform(post("/api/categories")
                .content(mapper.writeValueAsString(categoryPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("schoenen")))
                .andExpect(jsonPath("$.createdAt", is(new Date())));
    }*/

    /*@Test
    public void givenCategory_whenPutCategory_thenReturnJsonEvent() throws Exception{
        Category categoryPut = new Category(new ObjectId().toString(), "penen", new Date());

        given(categoryRepository.findCategoryById(categoryPut.getId())).willReturn(categoryPut);

        Category updatedCategory = new Category(categoryPut.getId(),"pennen", new Date());

        mockMvc.perform(put("/api/categories")
                .content(mapper.writeValueAsString(updatedCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("pennen")))
                .andExpect(jsonPath("$.createdAt", is(new Date())));
    }*/
}
