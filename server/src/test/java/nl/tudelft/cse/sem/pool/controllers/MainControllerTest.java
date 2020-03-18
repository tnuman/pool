package nl.tudelft.cse.sem.pool.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.cse.sem.pool.database.repositories.ScoreRepository;
import nl.tudelft.cse.sem.pool.database.repositories.UserRepository;
import nl.tudelft.cse.sem.pool.models.Score;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private transient MockMvc mvc;

    @Autowired
    private transient ScoreRepository scoreRepository;

    @Autowired
    private transient UserRepository userRepository;

    private transient Score testScore = new Score();

    private static final transient String test = "test";

    @BeforeEach
    public void setUp() {
        scoreRepository.setUp(true);
        userRepository.setUp(true);
        testScore.setUsername("username");
        testScore.setUserScore(42);

        scoreRepository.save(testScore);
    }

    @Test
    void top5Scores() throws Exception {
        var scores = scoreRepository.getTop5();

        String output = this.mvc.perform(
                get("/topscores"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Score[] parsedOutput = new ObjectMapper().readValue(output, Score[].class);

        Assert.assertArrayEquals(scores.toArray(), parsedOutput);
    }

    @Test
    void addScore() throws Exception {
        User testUser = new User();

        String username = test;
        testUser.setUsername(username);

        var bcrypt = new BCryptPasswordEncoder();
        testUser.setPassword(bcrypt.encode(test));

        userRepository.save(testUser);

        String ustring = "{\"username\": \"" + testUser.getUsername()
                + "\", \"password\": \"" + test + "\"}";

        // Logging in with the account
        String login = this.mvc.perform(
                post("/login").contentType(MediaType.APPLICATION_JSON).content(ustring))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getHeader("Authorization");

        Assert.assertNotNull(login);
        Assert.assertTrue(login.startsWith("Bearer "));

        Score score = new Score(testUser, 124);

        var objectMapper = new ObjectMapper();

        String scorestring = objectMapper.writeValueAsString(score);

        this.mvc.perform(post("/score").contentType(MediaType.APPLICATION_JSON)
                .content(scorestring).header(HttpHeaders.AUTHORIZATION, login))
                .andExpect(status().is2xxSuccessful());

        var savedScore = scoreRepository.findByUsername(test);
        Assert.assertEquals(savedScore.get().getUserScore(), score.getUserScore());
        Assert.assertEquals(savedScore.get().getUsername(), score.getUsername());

        score.setUserScore(200);

        scorestring = objectMapper.writeValueAsString(score);

        this.mvc.perform(post("/score").contentType(MediaType.APPLICATION_JSON)
                .content(scorestring).header(HttpHeaders.AUTHORIZATION, login))
                .andExpect(status().is2xxSuccessful());


        savedScore = scoreRepository.findByUsername(test);
        Assert.assertEquals(savedScore.get().getUserScore(), score.getUserScore());
        Assert.assertEquals(savedScore.get().getUsername(), score.getUsername());
    }
}
