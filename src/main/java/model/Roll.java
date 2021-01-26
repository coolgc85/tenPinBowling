package model;

public class Roll {

    public static final Integer MAX_PIN_NUMBER = 10;
    private Integer firstRoll;
    private Integer secondRoll;
    private Integer extraRoll;
    private Boolean foulFlag;

    public Roll(Integer firstRoll, Integer secondRoll) {
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
    }

    public Roll() {

    }


    public Integer getFirstRoll() {
        return firstRoll;
    }

    public void setFirstRoll(Integer firstRoll) {
        this.firstRoll = firstRoll;
    }

    public void setFirstRoll(String firstRoll) {
        if(firstRoll == "F") {
            this.setFirstRoll(0);
            foulFlag = Boolean.TRUE;
        }
        this.setFirstRoll(Integer.parseInt(firstRoll));
    }

    public Integer getSecondRoll() {
        return secondRoll;
    }

    public void setSecondRoll(Integer secondRoll) {
        this.secondRoll = secondRoll;
    }

    public Integer getExtraRoll() {
        return extraRoll;
    }

    public void setExtraRoll(Integer extraRoll) {
        this.extraRoll = extraRoll;
    }

    public Boolean getFoulFlag() {
        return foulFlag;
    }

    public void setFoulFlag(Boolean foulFlag) {
        this.foulFlag = foulFlag;
    }
}
