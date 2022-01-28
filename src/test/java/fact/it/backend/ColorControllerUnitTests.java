package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Color;
import fact.it.backend.model.Color;
import fact.it.backend.repository.ColorRepository;
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
@RequestMapping(path = "api/colors")
@WithMockUser(username="admin", roles="ADMIN")
public class ColorControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColorRepository colorRepository;

    private ObjectMapper mapper = new ObjectMapper();

    /*@Test
    public void whenGetAllColors_thenReturnJsonColor() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Color> allColors = colorRepository.findAll(requestedPage);

        given(colorRepository.findAll(requestedPage)).willReturn(allColors);

        mockMvc.perform(get("/api/colors"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllColorsWithParams_thenReturnJsonColor() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Color> allColorsWithParams = colorRepository.findAll(requestedPage);

        given(colorRepository.findAll(requestedPage)).willReturn(allColorsWithParams);

        mockMvc.perform(get("/api/colors?page=0&sort=name&order=desc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));

    }*/

    @Test
    public void whenGetColorById_thenReturnJsonColor() throws Exception{
        Color colorTest = new Color(new ObjectId().toString(),"rood", new Date());

        given(colorRepository.findColorById(colorTest.getId())).willReturn(colorTest);

        mockMvc.perform(get("/api/colors/{id}", colorTest.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("rood")));
    }

    @Test
    public void whenPostColor_thenReturnJsonColor() throws Exception{
        Color colorPost = new Color( new ObjectId().toString(),"geel", new Date());


        mockMvc.perform(post("/api/colors").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
                .content(mapper.writeValueAsString(colorPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("geel")));
    }

    @Test
    public void whenPostColorNotAuthorized_thenReturnForbidden() throws Exception{
        Color colorPost = new Color( new ObjectId().toString(),"geel", new Date());

        mockMvc.perform(post("/api/colors").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWNjb3VudEBnbWFpbC5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJ1c2VyX2lkIjoiNjFmMjZlMzkzYTQzYWQ1NGU1MWI2MmIxIiwiZXhwIjoxNjQzNTQwMjk4LCJpYXQiOjE2NDMzNjc0OTh9.5nr_doX1g42mjzunRcqg_vg20Gjj43G79NIUGV6Nz8o")
                .content(mapper.writeValueAsString(colorPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenColor_whenPutColor_thenReturnJsonColor() throws Exception{
        Date date = new Date();
        Color colorPut = new Color(new ObjectId().toString(), "bluaw", date);

        given(colorRepository.findColorById(colorPut.getId())).willReturn(colorPut);

        Color updatedColor = new Color(colorPut.getId(),"blauw", date);

        mockMvc.perform(put("/api/colors").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
                .content(mapper.writeValueAsString(updatedColor))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("blauw")));
    }

    @Test
    public void whenPutColorNotAuthorized_thenReturnForbidden() throws Exception{
        Date date = new Date();
        Color colorPut = new Color(new ObjectId().toString(), "paasr", date);

        given(colorRepository.findColorById(colorPut.getId())).willReturn(colorPut);

        Color updatedColor = new Color(colorPut.getId(),"paars", date);

        mockMvc.perform(put("/api/colors").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWNjb3VudEBnbWFpbC5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJ1c2VyX2lkIjoiNjFmMjZlMzkzYTQzYWQ1NGU1MWI2MmIxIiwiZXhwIjoxNjQzNTQwMjk4LCJpYXQiOjE2NDMzNjc0OTh9.5nr_doX1g42mjzunRcqg_vg20Gjj43G79NIUGV6Nz8o")
                .content(mapper.writeValueAsString(updatedColor))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenColor_whenDeleteColor_thenStatusOk() throws Exception {
        String id = new Object().toString();
        Color colorToBeDeleted = new Color(id,"magenta", new Date());

        given(colorRepository.findColorById(id)).willReturn(colorToBeDeleted);

        mockMvc.perform(delete("/api/colors/{id}", id).header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenColor_whenDeleteColor_thenStatusNotFound() throws Exception {
        given(colorRepository.findColorById("XXX")).willReturn(null);

        mockMvc.perform(delete("/api/colors/{id}", "XXX").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOiI2MWYyNmYzNmVlYWU4OTFhN2RlNWVmZDkiLCJleHAiOjE2NDM1MzY1NjksImlhdCI6MTY0MzM2Mzc2OX0.KAvf7O4sVla9O96oCVSw3QBZWABn8IF_bFb_ADZ_yNQ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteColorNotAuthorized_thenReturnForbidden() throws Exception{
        given(colorRepository.findColorById("XXX")).willReturn(null);

        mockMvc.perform(delete("/api/colors/{id}", "XXX").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWNjb3VudEBnbWFpbC5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJ1c2VyX2lkIjoiNjFmMjZlMzkzYTQzYWQ1NGU1MWI2MmIxIiwiZXhwIjoxNjQzNTQwMjk4LCJpYXQiOjE2NDMzNjc0OTh9.5nr_doX1g42mjzunRcqg_vg20Gjj43G79NIUGV6Nz8o")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
