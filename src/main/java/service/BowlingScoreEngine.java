package service;

import exception.BowlingException;
import model.Frame;
import model.Roll;
import model.RollLine;
import util.FileScoreReader;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class BowlingScoreEngine {
    int MAX_NUM_FRAMES = 10;
    ConcurrentSkipListMap<Integer,Frame> frameMap = new ConcurrentSkipListMap <Integer,Frame>();
    Integer frameNumber = null;
    Frame frame = null;

    private Frame getFrameInstance(){
        if(frame == null){
            frame = new Frame();
        }
        return frame;
    }

    public void processScoreGame(){

        FileScoreReader fr = new FileScoreReader();
        try {
            List<RollLine> rollLines = fr.readFile("Sample.txt"); //TODO: check for a interface

            //Group file entries by player
            Map<String,List<RollLine>> rollLinesByPlayer = rollLines.stream().collect(Collectors.groupingBy(RollLine::getPlayerName));

            


        } catch (BowlingException e) {
            e.printStackTrace();
        }
    }

    public Integer calculateSpare(Frame currentFrame){
        return Roll.MAX_PIN_NUMBER + frameMap.get(currentFrame.getFrameNumber()+1).getRoll().getFirstRoll();
    }

    public Integer calculateStrike(Frame currentFrame){
        int score = Roll.MAX_PIN_NUMBER;
        Frame nextFrame = null;
        int nextIndex = 0;

        if(currentFrame.isBonusRoll()){
            Roll rollTmp = currentFrame.getRoll();
            return rollTmp.getFirstRoll() + rollTmp.getSecondRoll() + rollTmp.getExtraRoll();
        }

        while(!(nextIndex <= 2)){
            nextIndex++;
            nextFrame = frameMap.get(currentFrame.getFrameNumber()+nextIndex);

            if (nextFrame.isStrike()){
                score = score + Roll.MAX_PIN_NUMBER;
            }
            else if(nextIndex < 2){
                score = nextFrame.getRoll().getFirstRoll() + nextFrame.getRoll().getSecondRoll();
                nextIndex++;
            }
            else
                score = nextFrame.getRoll().getFirstRoll();

        }
    return score;
    }


    private void calculateScore(){

        for (Map.Entry<Integer, Frame> entryFrame : frameMap.entrySet()) {
            Frame frameTmp = entryFrame.getValue();

                if(frameTmp.isStrike()){
                    frameTmp.setBonusRoll(frame.getFrameNumber() == Frame.LAST_FRAME);
                    frameTmp.setScore(calculateStrike(frameTmp));
                }
                else if(frameTmp.isSpare()){
                    frameTmp.setScore(calculateSpare(frameTmp));
                }
                else{
                    frameTmp.setScore(frameTmp.getRoll().getFirstRoll() + frameTmp.getRoll().getSecondRoll());
                }
            frameMap.replace(frameTmp.getFrameNumber(),frameTmp);
        }
    }


    private Frame setFrameValues(RollLine line){

        if(frameMap.isEmpty())
            frameNumber = 1;

        frame = getFrameInstance();
        Roll roll = frame.getRoll();

        if (frame.getFrameNumber() == null) {
            frame.setFrameNumber(frameNumber++);
        }

        if(roll.getFirstRoll() == null){
            roll.setFirstRoll(Integer.valueOf(line.getPinsKnocked()));
            frame.setStrike(roll.getFirstRoll() == Roll.MAX_PIN_NUMBER);

            if(frame.isStrike()){
                frame.setBonusRoll(frame.getFrameNumber() == Frame.LAST_FRAME);
                return frame;
            }
        } else if(roll.getSecondRoll() == null){
            roll.setSecondRoll(Integer.valueOf(line.getPinsKnocked()));
            frame.setSpare(roll.getFirstRoll()+roll.getSecondRoll()==Roll.MAX_PIN_NUMBER);
        } else{
            roll.setExtraRoll(Integer.valueOf(line.getPinsKnocked()));
            frame.setRoll(roll);
        }
        return frame;

    }

 
}
