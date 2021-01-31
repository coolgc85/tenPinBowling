package service;

import model.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;



class BowlingScoreEngineTest {

    Integer PERFECT_SCORE = 300;

    @Test
    void processStandardGame() {
        BowlingScoreEngine engine = new BowlingScoreEngine();
        assertTrue(engine.processScoreGame("OnePlayer.txt") < 300 );
    }



    @Test
    void processPerfectGame() {
        BowlingScoreEngine engine = new BowlingScoreEngine();
        engine.processScoreGame("PerfectMatchSinglePlayer.txt");
        assertSame(PERFECT_SCORE,engine.processScoreGame("PerfectMatchSinglePlayer.txt"));
    }

     /*
    @Test
 void invalidScoreMoreFrames() {


    }*/

   /* @Test
    void invalidScoreCheatBonus() {


    }*/

    /* @Test
    void incompleteGame() {


    }*/
}