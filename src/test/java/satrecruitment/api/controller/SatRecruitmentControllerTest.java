package satrecruitment.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import sat.recruitment.api.SatRecruitmentApplication;
import sat.recruitment.api.controller.SatRecruitmentController;
import sat.recruitment.api.models.User;
import sat.recruitment.api.repositories.UserRepository;
import sat.recruitment.api.services.UserService;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static sat.recruitment.api.utils.ErrorMessage.DUPLICATED_USER;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"url=/create-user"})
@ContextConfiguration(classes = SatRecruitmentApplication.class)
@WebMvcTest(SatRecruitmentController.class)
public class SatRecruitmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository<User> UserRepository;

    @Value("${url}")
    String url;

    public MvcResult callToCreate(User user) throws Exception {
        String inputJson = mapToJson(user);
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void createUserOk() throws Exception {
        User user = new User("Micaela", "mica@gmail.com", "1234","25 de mayo 123", "Normal", 50.0);

        MvcResult mvcResult = callToCreate(user);

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUser_badRequest_withoutName() throws Exception {
        User user = new User("", "mica@gmail.com", "1234","25 de mayo 123", "Normal", 50.0);

        MvcResult mvcResult = callToCreate(user);

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUser_badRequest_withoutEmail() throws Exception {
        User user = new User("Micaela", "", "1234","25 de mayo 123", "Normal", 50.0);

        MvcResult mvcResult = callToCreate(user);

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUser_badRequest_withoutAddress() throws Exception {
        User user = new User("Micaela", "mica@gmail.com", "1234","", "Normal", 50.0);

        MvcResult mvcResult = callToCreate(user);

        assertEquals(400, mvcResult.getResponse().getStatus());
    }
    @Test
    public void createUser_badRequest_withoutPhoneAndAddress() throws Exception {
        User user = new User("Micaela", "mica@gmail.com", "", "25 de mayo 123","Normal", 50.0);

        MvcResult mvcResult = callToCreate(user);

        assertEquals(400, mvcResult.getResponse().getStatus());
    }
    @Test
    public void createUser_badRequest_withoutUserType() throws Exception {
        User user = new User("Micaela", "mica@gmailcom", "123", "25 de mayo 123","", 50.0);

        MvcResult mvcResult = callToCreate(user);

        assertEquals(400, mvcResult.getResponse().getStatus());
    }
    @Test
    public void createUser_badRequest_withoutMoney() throws Exception {
        User user = new User("Micaela", "mica@gmailcom", "123", "25 de mayo 123","Normal", null);

        MvcResult mvcResult = callToCreate(user);

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUser_Duplicated() throws Exception {
        User user = new User("Juan", "Juan@marmol.com", "+5491154762312", "Peru 2464","Normal", 1234.0);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        when(userService.readFile()).thenReturn(users);
        when(userService.isDuplicated(users, user)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, DUPLICATED_USER.toString()));

        MvcResult mvcResult = callToCreate(user);

        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}