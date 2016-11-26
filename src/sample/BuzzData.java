package sample;

/**
 * Created by arslan on 11/24/16.
 */
public class BuzzData {

    private String username;
    private String password;
    private int[][] modes;

    public BuzzData(String username, String password, int[][] modes)    {
        this.username = username;
        this.password = password;
        this.modes = modes;
    }

    public BuzzData()   {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[][] getModes() {
        return modes;
    }

    public void setModes(int[][] modes) {
        this.modes = modes;
    }
}
