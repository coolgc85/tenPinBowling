package model;

import java.util.List;

public class Game {

    private List<Frame> frames;
    private String playerName;
    private Integer maxFrames;

    public Integer getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(Integer maxFrames) {
        this.maxFrames = maxFrames;
    }

    public Game(List<Frame> frames, String playerName) {
        this.frames = frames;
        this.playerName = playerName;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


}
