package service;

import model.Frame;
import model.Roll;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrinterScoreSheet {

    private StringBuilder builder;
    private boolean flagPinfall;
    private boolean flagScore;
    private String playerName;

    public PrinterScoreSheet(){
        builder = new StringBuilder();
    }

    public void printFrameNumbers(){
        builder.append("Frame\t\t");
        List<Integer> frames = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        String framesStr =  frames.stream().map(i->i.toString()).collect(Collectors.joining("\t\t"));
        builder.append(framesStr);
        builder.append("\n");
        builder.append(this.playerName);
    }

    public void printPinFalls(Frame frame){
        if(!flagPinfall){
            builder.append("Pinfalls");
            flagPinfall = true;
        }

        Roll roll = frame.getRoll();

        builder.append("\t");
        if(frame.isStrike()){
            builder.append("\tX");
        }else if(frame.isSpare()){
            if(roll.getFoulFlag())
                builder.append("F");
            else
                builder.append(roll.getFirstRoll());
            builder.append("\t/");
        }else{
            builder.append(roll.getFoulFlag() && roll.getFirstRoll()==0?"F":roll.getFirstRoll());
            builder.append("\t");
            builder.append(roll.getFoulFlag() && roll.getSecondRoll()==0?"F":roll.getSecondRoll());
        }
    }

    public void printScore(Frame frame){
        if(!flagScore) {
            builder.append("\nScore");
            flagScore = true;
        }
        builder.append("\t\t");
        builder.append(frame.getScore());
    }

    public StringBuilder getBuilder(){
        return builder;
    }
    public void setBuilder(StringBuilder builder){
        this.builder = builder;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String gePlayerName() {
        return playerName;
    }
}
