package model;

public class Frame {

    public static final Integer LAST_FRAME = 10;
    private Integer frameNumber;
    private Roll roll;
    private Boolean isSpare;
    private Boolean isStrike;
    private Boolean bonusRoll;
    private Integer score;
    private Boolean isLast;
    private Boolean isFinalFrame;

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

    public void setSpare(Boolean spare) {
        this.isSpare = spare;
    }

    public boolean isStrike() {
        return isStrike;
    }

    public void setStrike(Boolean strike) {
        this.isStrike = strike;
    }

    public Boolean isBonusRoll() {
        return bonusRoll;
    }

    public void setBonusRoll(Boolean bonusRoll) {
        this.bonusRoll = bonusRoll;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    public Boolean getFinalFrame() {
        return isFinalFrame;
    }

    public void setFinalFrame(Boolean finalFrame) {
        isFinalFrame = finalFrame;
    }
}
