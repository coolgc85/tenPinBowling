package service;

import model.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;



class BowlingScoreEngineTest {

    Integer PERFECT_SCORE = 300;

    @Test
    void processStandardGame() {
        BowlingScoreEngine engine = new BowlingScoreEngine();
        HashMap<String,Integer> response = engine.processScoreGame("TwoPlayers.txt");
        assertTrue(response.size() >= 1);
    }



    @Test
    void processPerfectGame() {
        BowlingScoreEngine engine = new BowlingScoreEngine();
        assertEquals(PERFECT_SCORE,engine.processScoreGame("PerfectMatchSinglePlayer.txt").entrySet().iterator().next().getValue());
    }

}