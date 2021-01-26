package service;

import model.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowlingScoreEngineTest {

    Integer PERFECT_SCORE = 300;

    @Test
    void processStandardGame() {
        BowlingScoreEngine engine = new BowlingScoreEngine();
        engine.processScoreGame("sample.txt");
        assertTrue(!engine.frameMap.isEmpty());
    }

    @Test
    void processPerfectGame() {
        BowlingScoreEngine engine = new BowlingScoreEngine();
        engine.processScoreGame("perfect.txt");
        assertTrue(engine.frameMap.get(Frame.LAST_FRAME).getScore() == PERFECT_SCORE);

    }

   /* @Test
    void completeFrames() {


    }*/

    /* @Test
    void failGame() {


    }*/
}