package service;

import exception.BowlingException;
import model.Frame;
import model.Game;
import model.Roll;
import model.RollLine;
import util.FileScoreReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BowlingScoreEngine {

    public void processScore(){
        int NUM_FRAMES = 10;

        FileScoreReader fr = new FileScoreReader();
        try {
            List<RollLine> rollLines = fr.readFile("Sample.txt");
            Map<String,List<RollLine>> rollLinesByPlayer = rollLines.stream().collect(Collectors.groupingBy(RollLine::getPlayerName));
           // rollLinesByPlayer.entrySet().stream().map(roll -> calculateScore(roll.getValue())).collect(Collectors.toList());
            //this.calculateScore(rollLinesByPlayer);
            //this.printScore();


        } catch (BowlingException e) {
            e.printStackTrace();
        }
    }


    public Game caculateGameScore(List<RollLine> rollLinesByPlayer) {
        Frame frame = new Frame();
        Roll rollItem = null;
        Integer frameNumber = 1;
        Integer tmpScore = 0;
        HashMap<Integer, Frame> frameList = new HashMap<Integer, Frame>();
        Frame previousFrame = null;

        for (RollLine rollLineItem : rollLinesByPlayer) {

            if (frameNumber >= 1) {
                previousFrame = frameList.get(frameNumber - 1);
            }


            if (rollItem.getFirstRoll() == null) {
                frame = new Frame();
                frame.setFrameNumber(frameNumber);
                rollItem = new Roll();
                rollItem.setFirstRoll(Integer.valueOf(rollLineItem.getPinsKnocked()));

                if (previousFrame.isSpare()) {//TODO: check for optional
                    previousFrame.setScore(previousFrame.getScore() + rollItem.getFirstRoll());
                }

                frame.setStrike(rollItem.getFirstRoll() == 10);
                if (frame.isStrike()) {
                    frame.setScore(rollItem.getFirstRoll());
                }
            } else {
                rollItem.setSecondRoll(Integer.valueOf(rollLineItem.getPinsKnocked()));
                frame.setSpare((rollItem.getFirstRoll() + rollItem.getSecondRoll()) == 10);
                frame.setScore(rollItem.getFirstRoll() + rollItem.getSecondRoll());

                if (previousFrame.isStrike()) {
                    previousFrame.setScore(previousFrame.getScore() + frame.getScore());
                    frame.setScore(frame.getScore() + previousFrame.getScore());
                }
                frameNumber++;
                rollItem = null;
            }
            frameList.put(frameNumber, frame);

        }
        return null;

    }

 
}
