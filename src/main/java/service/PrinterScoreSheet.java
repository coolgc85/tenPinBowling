package service;

import model.Frame;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrinterScoreSheet {

    private StringBuilder builder;
    private String playerName="Jeff";

    public PrinterScoreSheet(){
        builder = new StringBuilder();
    }

    public StringBuilder getBuilder(){
        return builder;
    }

    public void doPringFrameNumbers(){
        List<Integer> frames = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        String framesStr =  frames.stream().map(i->i.toString()).collect(Collectors.joining("\t\t"));
        builder.append(framesStr);
    }

    public void doPrintPinFalls(Frame frame){
        builder.append("\t");

        if(frame.isStrike()){
            builder.append("\tX");
        }else if(frame.isSpare()){
            builder.append("\t/");
        }else{
            builder.append(frame.getRoll().getFirstRoll());
            builder.append("\t");
            builder.append(frame.getRoll().getSecondRoll());
        }
    }

    public void doPrintScore(Frame frame){
        builder.append("\t\t");
        builder.append(frame.getScore());
    }

    private void getPrintedFrameScore(Frame frame){

        if(frame.isStrike()){
            builder.append("\t");
            builder.append("\t");
            builder.append("X");
        }
        else{
            builder.append(frame.getScore());
        }
    }

}
