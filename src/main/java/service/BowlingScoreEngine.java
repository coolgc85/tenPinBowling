package service;

import exception.BowlingException;
import model.Frame;
import model.Roll;
import model.RollLine;
import util.FileScoreReader;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BowlingScoreEngine {


    private ConcurrentSkipListMap<Integer,Frame> frameMap;
    private Integer frameNumber;
    private Frame frame;

    private PrinterScoreSheet buffer;
    private PrinterScoreSheet bufferPin;


    public BowlingScoreEngine(){
        buffer = new PrinterScoreSheet();
    }

    private Frame getFrameInstance(){
        if(frame == null){
            frame = new Frame();
        }
        return frame;
    }

    /** Process a file withe the game lines ir order to get a result
     * @param fileName score lines filename
     */
    public Map<String,Integer> processScoreGame(String fileName){


        HashMap<String,Integer> result = new HashMap<>();
        FileScoreReader fr = new FileScoreReader();
        try {
            List<RollLine> rollLines = fr.readFile(fileName); //TODO: check for a interface

            //Group file entries by player
            Map<String,List<RollLine>> rollLinesByPlayer = rollLines.stream().collect(Collectors.groupingBy(RollLine::getPlayerName));

            for (Map.Entry<String, List<RollLine>> rollLineEntry : rollLinesByPlayer.entrySet()) {
                String playerName = rollLineEntry.getValue().get(0).getPlayerName();
                frameMap = new ConcurrentSkipListMap<>();
                buffer = new PrinterScoreSheet();
                bufferPin = new PrinterScoreSheet();
                if(buffer.gePlayerName() == null) {
                    buffer.setPlayerName(playerName);
                    result.put(playerName,null);
                }
                buffer.printFrameNumbers();
                for (RollLine rollLineItem:rollLineEntry.getValue()) {
                    this.setFrameValues(rollLineItem);
                    //TODO: print score card
                }
                this.getCalculatedScore();
                System.out.println(buffer.getBuilder().toString());
                System.out.println(bufferPin.getBuilder().toString());

                result.put(playerName,frameMap.lastEntry().getValue().getScore());
            }

        } catch (BowlingException e) {
            e.printStackTrace();
        }
        return result;
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

    /**
     * Specific method that calcules the score for the current frame in considering the strike rule
     * @param currentFrame
     * @return calculate score
     */
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

    /**
     * Process all frames and assign the score to each one, updating the map of frams
     */
    private void getCalculatedScore() {
        int previousScore = 0;
        for (Map.Entry<Integer, Frame> entryFrame : frameMap.entrySet()) {
            Frame frameTmp = entryFrame.getValue();

            if (frameTmp.getFrameNumber() > 1)
                previousScore = frameMap.get(frameTmp.getFrameNumber() - 1).getScore();

            if (frameTmp.isStrike()) {
                frameTmp.setScore(calculateStrike(frameTmp));
            } else if (frameTmp.isSpare()) {
                frameTmp.setScore(calculateSpare(frameTmp) + previousScore);
            } else {
                frameTmp.setScore(getFrameStandardScore(frameTmp) + previousScore);
            }
            bufferPin.printPinFalls(frameTmp);
            buffer.printScore(frameTmp);
            frameMap.replace(frameTmp.getFrameNumber(), frameTmp);

        }

    }

    /**
     * Set all values for each throw in all frames
     * @param line roll line read from score file
     */
    private void setFrameValues(RollLine line) {

        if (frameMap.isEmpty())
            frameNumber = 1;

        frame = getFrameInstance();
        Optional<Roll> optionalRoll = Optional.ofNullable(frame.getRoll());
        Roll roll = optionalRoll.orElse(new Roll());

        if (frame.getFrameNumber() == null) {
            frame.setFrameNumber(frameNumber++);
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
            roll.setSecondRoll(line.getPinsKnocked());
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
