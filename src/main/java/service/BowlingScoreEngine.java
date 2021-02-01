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


    private ConcurrentSkipListMap<Integer,Frame> frameMap;
    private Integer frameNumber;
    private Frame frame;


    private Frame getFrameInstance(){
        if(frame == null){
            frame = new Frame();
        }
        return frame;
    }

    /**
     * @param fileName score lines filename
     */
    public Integer processScoreGame(String fileName){

        FileScoreReader fr = new FileScoreReader();
        try {
            List<RollLine> rollLines = fr.readFile(fileName); //TODO: check for a interface

            //Group file entries by player
            Map<String,List<RollLine>> rollLinesByPlayer = rollLines.stream().collect(Collectors.groupingBy(RollLine::getPlayerName));

            for (Map.Entry<String, List<RollLine>> rollLineEntry : rollLinesByPlayer.entrySet()) {
                frameMap = new ConcurrentSkipListMap<>();
                for (RollLine rollLineItem:rollLineEntry.getValue()) {
                    this.setFrameValues(rollLineItem);
                    //TODO: print score card
                }
                this.getCalculatedScore();
                System.out.println(frameMap.lastEntry().getValue().getScore());
            }

        } catch (BowlingException e) {
            e.printStackTrace();
        }
        return frameMap.lastEntry().getValue().getScore(); //TODO:  change for method
    }

    /**
     * This function calculates the score for the current frame if this
     * is a spare
     * @param currentFrame
     * @return score number
     */
    private Integer calculateSpare(Frame currentFrame){
        return Roll.MAX_PIN_NUMBER + frameMap.get(currentFrame.getFrameNumber()+1).getRoll().getFirstRoll();
    }

    private Integer getFrameStandardScore(Frame frame){
        return getFrameStandardScore(frame, false);
    }

    private Integer getFrameStandardScore(Frame frame, boolean strikeValidation){
        if(strikeValidation)
            return Roll.MAX_PIN_NUMBER;
        else
            return Optional.ofNullable(frame.getRoll().getFirstRoll()).orElse(0) + Optional.ofNullable(frame.getRoll().getSecondRoll()).orElse(0);
    }

    private Integer getScoreNextTwoThrows(Frame currentFrame){
        int score = 0;
        int index = 1;
        Frame nextFrame = null;

        while (index <=2){
            nextFrame = frameMap.get(currentFrame.getFrameNumber() + index);
            if(index == 1 && nextFrame.isFinalFrame()) {
                score += getFrameStandardScore(nextFrame);
            }else
                score += getFrameStandardScore(nextFrame, nextFrame.isStrike());
            if(nextFrame.isStrike() && !nextFrame.isFinalFrame())
                index++;
            else
                break;
        }


        return score;
    }

    private Integer calculateStrike(Frame currentFrame) {
        Integer score = 0;

        if (currentFrame.getFrameNumber() > 1) {
            score = score + frameMap.get(currentFrame.getFrameNumber() - 1).getScore();
        }

        if(!currentFrame.isFinalFrame())
            score += getScoreNextTwoThrows(currentFrame) + Roll.MAX_PIN_NUMBER;
        else{
            score += getFrameStandardScore(currentFrame);
            if(currentFrame.isBonusRoll()){
               score+=currentFrame.getRoll().getExtraRoll();
            }
        }
        return score;
    }


    private void getCalculatedScore(){
        int previousScore = 0;
        for (Map.Entry<Integer, Frame> entryFrame : frameMap.entrySet()) {
            Frame frameTmp = entryFrame.getValue();

            if(frameTmp.getFrameNumber() > 1)
                previousScore = frameMap.get(frameTmp.getFrameNumber()-1).getScore();

                if(frameTmp.isStrike()){
                    frameTmp.setScore(calculateStrike(frameTmp));
                }
                else if(frameTmp.isSpare()){
                    frameTmp.setScore(calculateSpare(frameTmp)+previousScore);
                }
                else{
                    frameTmp.setScore(getFrameStandardScore(frameTmp)+previousScore);
                }
            frameMap.replace(frameTmp.getFrameNumber(),frameTmp);
        }

    }


    private void setFrameValues(RollLine line) {

        if (frameMap.isEmpty())
            frameNumber = 1;

        frame = getFrameInstance();
        Optional<Roll> optionalRoll = Optional.ofNullable(frame.getRoll());//TODO: check for improvement
        Roll roll = optionalRoll.orElse(new Roll());

        if (frame.getFrameNumber() == null) {
            frame.setFrameNumber(frameNumber++);
            //frame.setFinalFrame(frame.getFrameNumber().equals(Frame.LAST_FRAME));
        }

        if (roll.getFirstRoll() == null) {
            roll.setFirstRoll(line.getPinsKnocked());
            frame.setStrike(roll.getFirstRoll().equals(Roll.MAX_PIN_NUMBER));
            frame.setRoll(roll);

            if (frame.isStrike()) {
                frame.setBonusRoll(frame.isFinalFrame());
                frameMap.put(frame.getFrameNumber(), frame);
                if(!frame.isFinalFrame())
                    frame = null;
            } else{
                frameMap.put(frame.getFrameNumber(), frame);
            }
        } else if (roll.getSecondRoll() == null) {
            roll.setSecondRoll(Integer.valueOf(line.getPinsKnocked()));
            if (!frame.isBonusRoll()) {
                frame.setSpare(roll.getFirstRoll() + roll.getSecondRoll() == Roll.MAX_PIN_NUMBER);
                frameMap.put(frame.getFrameNumber(), frame);
                frame = null;
            } else {
                frameMap.put(frame.getFrameNumber(), frame);
            }
        } else {
            roll.setExtraRoll(Integer.valueOf(line.getPinsKnocked()));
            frame.setRoll(roll);
            frameMap.put(frame.getFrameNumber(), frame);
            frame = null;
        }

    }

 
}
