package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreTest {
    HighScore highScore;
    @BeforeEach
    public void initialize(){
        highScore = new HighScore("Jimmy",60);
    }

    /**
     * Check setting the score.
     */
    @Test
    void setScoreTets() {
        highScore.setScore(70);
        assertEquals(70, highScore.getScore());
    }

    /**
     * Check getting the score.
     */
    @Test
    void getScore() {
        assertEquals(60,highScore.getScore());
    }


    /**
     * Check get the name.
     */
    @Test
    void getName() {
        assertEquals("Jimmy",highScore.getName());
    }

    /**
     * Compare two highscore.
     */
    @Test
    void compareTest() {
        assertEquals(0,highScore.compare(new HighScore("Sam",70)));
    }
}