package sample;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by arslan on 11/28/16.
 */
public class Grid {

    private String[][] grid;
//    private String[][] grid2 =  {{"U", "H", "Y", "T"}, {"G", "E", "T", "T"}, {"O", "H", "O", "V"}, {"M", "E", "S", "O"}};

    private int maxSize;
    private Dictionary dictionary;
    private boolean[][] visitedNodes;
    private static final double[] frequencies = {
            0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
            0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
            0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
            0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
            0.01974, 0.00074
    };
    private static final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int targetScore;
    private HashSet<String> duplicateCheck;

    public Grid(String file)   {
        duplicateCheck = new HashSet<>();
        dictionary = new Dictionary(file);
        maxSize = 4;
        grid = new String[maxSize][maxSize];
        visitedNodes = new boolean[maxSize][maxSize];
        for (int i = 0; i < maxSize; i++)   {
            for (int j = 0; j < maxSize; j++)   {
                int num = discrete(frequencies);
                grid[i][j] = Character.toString(alpha.charAt(num));
            }
        }
//        grid = grid2;
        targetScore = 0;
    }

    public void displayWords()  {
        for (int i = 0; i < maxSize; i++)   {
            for (int j = 0; j < maxSize; j++)   {
                depthFirstSearch("", i, j);
                if (targetScore > 50)   {
                    return;
                }
            }
        }
    }

    public void depthFirstSearch(String prefix, int i, int j)   {

        if (i < 0 || j < 0 || i >= maxSize || j >= maxSize || visitedNodes[i][j] || !dictionary.containsPrefix(prefix))   {
            return;
        }

        visitedNodes[i][j] = true;

        if (grid[i][j].equals("Q")) {
            prefix += "QU";
        }
        else {
            prefix += grid[i][j];
        }

        if (dictionary.containsWord(prefix) && !duplicateCheck.contains(prefix))    {
//            System.out.println(prefix);

            if (prefix.length() < 3)   {
                targetScore += 0;
            }
            else if (prefix.length() >= 3 && prefix.length() <= 4)  {
                targetScore += 1;
            }
            else if (prefix.length() == 5)  {
                targetScore += 2;
            }
            else if (prefix.length() == 6)  {
                targetScore += 3;
            }
            else if (prefix.length() == 7)  {
                targetScore += 4;
            }
            else if (prefix.length() >= 8)  {
                targetScore += 5;
            }

            System.out.println(prefix + ", " + targetScore);

            duplicateCheck.add(prefix);
        }

        depthFirstSearch(prefix, i-1, j-1);
        depthFirstSearch(prefix, i, j-1);
        depthFirstSearch(prefix, i+1, j-1);
        depthFirstSearch(prefix, i-1, j);
        depthFirstSearch(prefix, i+1, j);
        depthFirstSearch(prefix, i-1, j+1);
        depthFirstSearch(prefix, i, j+1);
        depthFirstSearch(prefix, i+1, j+1);

        visitedNodes[i][j] = false;

    }

    public static int discrete(double[] probabilities) {

        double sum;

        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++) {
                sum = sum + probabilities[i];
                if (sum > r) return i;
            }
        }
    }

    public static double uniform() {
        Random random = new Random();
        return random.nextDouble();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void printGrid() {
        for (int i = 0; i < maxSize; i++)   {
            for (int j = 0; j < maxSize; j++)   {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public HashSet<String> getDuplicateCheck() {
        return duplicateCheck;
    }

    public void setDuplicateCheck(HashSet<String> duplicateCheck) {
        this.duplicateCheck = duplicateCheck;
    }
}
