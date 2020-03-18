package nl.tudelft.cse.sem.pool.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.cse.sem.pool.database.repositories.UserRepository;
import nl.tudelft.cse.sem.pool.models.Greeting;
import nl.tudelft.cse.sem.pool.models.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private transient MockMvc mvc;

    @Autowired
    private transient UserRepository userRepository;

    private transient String ustring;

    private transient User testUser = new User();

    @BeforeEach
    public void setUp() {
        userRepository.setUp(true);

        String username = "test";
        testUser.setUsername(username);
        testUser.setPassword("test");
        ustring = "{\"username\": \"" + testUser.getUsername()
                + "\", \"password\": \"" + testUser.getPassword() + "\"}";
    }

    @Test
    public void signUpTest() throws Exception {
        // Creating the account
        String output = this.mvc.perform(
                post("/signup").contentType(MediaType.APPLICATION_JSON).content(ustring))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User parsedOutput = new ObjectMapper().readValue(output, User.class);

        Assert.assertEquals(testUser.getUsername(), parsedOutput.getUsername());
        Assert.assertEquals("", parsedOutput.getPassword());
    }

    @Test
    public void signUpAndLoginTest() throws Exception {
        // Creating the account
        String output = this.mvc.perform(
                post("/signup").contentType(MediaType.APPLICATION_JSON).content(ustring))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User parsedOutput = new ObjectMapper().readValue(output, User.class);

        Assert.assertEquals(parsedOutput.getUsername(), testUser.getUsername());
        Assert.assertEquals(parsedOutput.getPassword(), "");

        // Logging in with the account
        String login = this.mvc.perform(
                post("/login").contentType(MediaType.APPLICATION_JSON).content(ustring))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getHeader("Authorization");

        Assert.assertNotNull(login);
        Assert.assertTrue(login.startsWith("Bearer "));
    }

    @Test
    public void signUpAndLoginAndHelloTest() throws Exception {
        // Creating the account
        String output = this.mvc.perform(
                post("/signup").contentType(MediaType.APPLICATION_JSON).content(ustring))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User parsedOutput = new ObjectMapper().readValue(output, User.class);

        Assert.assertEquals(testUser.getUsername(), parsedOutput.getUsername());
        Assert.assertEquals("", parsedOutput.getPassword());

        // Logging in with the account
        String login = this.mvc.perform(
                post("/login").contentType(MediaType.APPLICATION_JSON).content(ustring))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getHeader("Authorization");

        Assert.assertNotNull(login);
        Assert.assertTrue(login.startsWith("Bearer "));

        String response = this.mvc.perform(get("/greeting").header(HttpHeaders.AUTHORIZATION,login))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        Greeting mapper = new ObjectMapper().readValue(response, Greeting.class);

        Assert.assertEquals("Hello, World!", mapper.getContent());
    }
}
