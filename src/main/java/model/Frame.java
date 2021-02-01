package model;

public class Frame {

    public static final Integer LAST_FRAME = 10;
    private Integer frameNumber;
    private Roll roll;
    private Boolean isSpare;
    private Boolean isStrike;
    private boolean bonusRoll;
    private Integer score;
    private boolean isFinalFrame;

    public Frame() {
        this.isSpare = Boolean.FALSE;
        this.isStrike = Boolean.FALSE;

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

    public boolean isFinalFrame() {
        return getFrameNumber() == LAST_FRAME;
    }

    public void setFinalFrame(boolean finalFrame) {
        isFinalFrame = finalFrame;
    }
}
