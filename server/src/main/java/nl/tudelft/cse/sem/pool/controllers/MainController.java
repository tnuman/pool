package nl.tudelft.cse.sem.pool.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import nl.tudelft.cse.sem.pool.database.repositories.ScoreRepository;
import nl.tudelft.cse.sem.pool.models.Greeting;
import nl.tudelft.cse.sem.pool.models.Score;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MainController {

    private transient ScoreRepository scoreRepository;

    private static final String HELLO_TEMPLATE = "Hello, %s!";
    private final transient AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(HELLO_TEMPLATE, name));
    }

    @GetMapping("/topscores")
    public List<Score> top5Scores() {
        scoreRepository.setUp(false);
        return scoreRepository.getTop5();
    }

    /**
     * Enables the user to post a new score.
     * @param score the score.
     * @return the score.
     */
    @PostMapping("/score")
    public ResponseEntity<Score> addScore(@RequestBody Score score) {
        scoreRepository.setUp(false);
        
        // if this 0 error
        int status;

        if (scoreRepository.findByUsername(score.getUsername()).orElse(null) != null) {
            status = scoreRepository.update(score);
        } else {
            status = scoreRepository.save(score);
        }

        // 0 means 0 rows changed -> error
        if (status == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.accepted().body(score);
    }
}
