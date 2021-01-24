package model;

public class Frame {

    private Integer frameNumber;
    private Roll roll;
    private boolean isSpare;
    private boolean isStrike;
    private boolean bonusRoll;
    private Integer score;
    private Integer tmpScore;

    public Frame(Integer frameNumber, Roll roll, boolean isSpare, boolean isStrike, boolean bonusRoll, Integer score) {
        this.frameNumber = frameNumber;
        this.roll = roll;
        this.isSpare = isSpare;
        this.isStrike = isStrike;
        this.bonusRoll = bonusRoll;
        this.score = score;
    }

    public Frame() {

    }

    public Integer getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(Integer frameNumber) {
        this.frameNumber = frameNumber;
    }

    public Roll getRoll() {
        return roll;
    }

    public void setRoll(Roll roll) {
        this.roll = roll;
    }

    public boolean isSpare() {
        return isSpare;
    }

    public void setSpare(boolean spare) {
        this.isSpare = spare;
    }

    public boolean isStrike() {
        return isStrike;
    }

    public void setStrike(boolean strike) {
        this.isStrike = strike;
    }

    public boolean isBonusRoll() {
        return bonusRoll;
    }

    public void setBonusRoll(boolean bonusRoll) {
        this.bonusRoll = bonusRoll;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTmpScore() {
        return score;
    }

    public void setTmpScore(Integer tmpScore) {
        this.tmpScore = tmpScore;
    }







}
