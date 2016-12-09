package sample;

/**
 * Created by arslan on 11/26/16.
 */
public class Player {

    private BuzzData data;
    private int currentLevel;
    private int currentLevelHighscore;
    private boolean inGame;

    public Player(BuzzData data, int currentLevel, int currentLevelHighscore, boolean inGame)   {
        this.data = data;
        this.currentLevel = currentLevel;
        this.currentLevelHighscore = currentLevelHighscore;
        this.inGame = inGame;
    }

    public Player(BuzzData data) {
        this.data = data;
        this.currentLevelHighscore = -1;
        this.currentLevel = -1;
        this.inGame = false;
    }

    public BuzzData getData() {
        return data;
    }

    public void setData(BuzzData data) {
        this.data = data;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentLevelHighscore() {
        return currentLevelHighscore;
    }

    public void setCurrentLevelHighscore(int currentLevelHighscore) {
        this.currentLevelHighscore = currentLevelHighscore;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

}
