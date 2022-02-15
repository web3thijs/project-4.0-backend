package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Color;
import fact.it.backend.repository.ColorRepository;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
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

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private ColorRepository colorRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;

    @Test
    public void whenGetAllColors_thenReturnJsonColor() throws Exception{
        List<Color> allColors = colorRepository.findAll();

        given(colorRepository.findAll()).willReturn(allColors);

        mockMvc.perform(get("/api/colors"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllColorsWithParams_thenReturnJsonColor() throws Exception{
        List<Color> allColorsWithParams = colorRepository.findAll();

        given(colorRepository.findAll()).willReturn(allColorsWithParams);

        mockMvc.perform(get("/api/colors?page=0&sort=name&order=desc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));

    }

    @Test
    public void whenGetColorById_thenReturnJsonColor() throws Exception{
        Color colorTest = new Color("rood");

        given(colorRepository.findById(colorTest.getId())).willReturn(Optional.of(colorTest));

        mockMvc.perform(get("/api/colors/{id}", colorTest.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("rood")));
    }

    @Test
    public void whenPostColor_thenReturnJsonColor() throws Exception{
        Color colorPost = new Color("geel");


        mockMvc.perform(post("/api/colors").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .content(mapper.writeValueAsString(colorPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("geel")));
    }

    @Test
    public void whenPostColorNotAuthorized_thenReturnForbidden() throws Exception{
        Color colorPost = new Color("geel");

        mockMvc.perform(post("/api/colors").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                .content(mapper.writeValueAsString(colorPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenColor_whenPutColor_thenReturnJsonColor() throws Exception{
        Color colorPut = new Color("bluaw");

        given(colorRepository.findById(colorPut.getId())).willReturn(Optional.of(colorPut));

        Color updatedColor = new Color(colorPut.getId(),"blauw");

        mockMvc.perform(put("/api/colors").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .content(mapper.writeValueAsString(updatedColor))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("blauw")));
    }

    @Test
    public void whenPutColorNotAuthorized_thenReturnForbidden() throws Exception{
        Color colorPut = new Color("paasr");

        given(colorRepository.findColorById(colorPut.getId())).willReturn(colorPut);

        Color updatedColor = new Color(colorPut.getId(),"paars");

        mockMvc.perform(put("/api/colors").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                .content(mapper.writeValueAsString(updatedColor))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenColor_whenDeleteColor_thenStatusOk() throws Exception {
        Color colorToBeDeleted = new Color("magenta");

        given(colorRepository.findById(colorToBeDeleted.getId())).willReturn(Optional.of(colorToBeDeleted));

        mockMvc.perform(delete("/api/colors/{id}", colorToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenColor_whenDeleteColor_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/api/colors/{id}", 12345789).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteColorNotAuthorized_thenReturnForbidden() throws Exception{
        Color colorToBeDeleted = new Color("magenta");

        given(colorRepository.findColorById(colorToBeDeleted.getId())).willReturn(null);

        mockMvc.perform(delete("/api/colors/{id}", colorToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
