package model;

public class Roll {

    public static final Integer MAX_PIN_NUMBER = 10;
    private Integer firstRoll;
    private Integer secondRoll;
    private Integer extraRoll;
    private boolean foulFlag;

    public Roll() {

    }


    public Integer getFirstRoll() {
        return firstRoll;
    }

    public void setFirstRoll(Integer firstRoll) {
        this.firstRoll = firstRoll;
    }

    public void setFirstRoll(String firstRoll) {
        if(firstRoll.equals("F")) {
            this.setFirstRoll(0);
            foulFlag = true;
        } else{
            this.setFirstRoll(Integer.parseInt(firstRoll));
        }

    }

    public Integer getSecondRoll() {
        return secondRoll;
    }

    public void setSecondRoll(Integer secondRoll) {
        this.secondRoll = secondRoll;
    }

    public void setSecondRoll(String secondRoll) {
        if(secondRoll.equals("F")) {
            this.setSecondRoll(0);
            foulFlag = true;
        } else{
            this.setSecondRoll(Integer.parseInt(secondRoll));
        }
    }

    public Integer getExtraRoll() {
        return extraRoll;
    }

    public void setExtraRoll(Integer extraRoll) {
        this.extraRoll = extraRoll;
    }

    public boolean getFoulFlag() {
        return foulFlag;
    }

    public void setFoulFlag(boolean foulFlag) {
        this.foulFlag = foulFlag;
    }
}
