package fact.it.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class TokenGetService {
    @Autowired
    private MockMvc mockMvc;
    
    public String obtainAccessToken(String email, String password) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthRequest user = new AuthRequest();
        user.setPassword(password);
        user.setEmail(email);

        String json = mapper.writeValueAsString(user);

        ResultActions result
                = mockMvc.perform(post("/api/authenticate")
                        .content(json)
                        .header("Content-Type", "application/json")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }
}
