package service;

import model.Frame;
import model.Roll;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrinterScoreSheetTest {

    PrinterScoreSheet p;
    List<Frame> frames = new ArrayList<Frame>();

    @BeforeAll
    void init(){
        p = new PrinterScoreSheet();

        Frame frame = new Frame();
        frame.setStrike(Boolean.TRUE);
        frame.setScore(10);
        frames.add(frame);

        Frame frame2 = new Frame();
        frame2.setScore(7);
        Roll roll = new Roll();
        roll.setFirstRoll(3);
        roll.setSecondRoll(4);
        frame2.setRoll(roll);
        frames.add(frame2);
    }

    @Test
    @Order(1)
    void doPrint() {
        p.printFrameNumbers();
        System.out.println(p.getBuilder().toString());
    }


    @Test
    @Order(2)
    void doPrintPinFalls() {
        PrinterScoreSheet p = new PrinterScoreSheet();

        for (Frame frameTmp: frames) {
            p.printPinFalls(frameTmp);

        }
        System.out.println(p.getBuilder().toString());
    }

    @Test
    @Order(3)
    void doPrintScore() {
        PrinterScoreSheet p = new PrinterScoreSheet();

        for (Frame frameTmp: frames) {
            p.printScore(frameTmp);

        }
        System.out.println(p.getBuilder().toString());
    }
}