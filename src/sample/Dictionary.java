package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by arslan on 11/28/16.
 */
public class Dictionary {

    private File dictionaryFile;
    private HashSet<String> dictionary;

    public Dictionary(String file) {
        dictionaryFile = new File(file);
        dictionary = new HashSet<String>();
    }

    public void readDictionary()    {
        try {
            FileReader fileReader = new FileReader(dictionaryFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Scanner scanner = new Scanner(bufferedReader);

            while(scanner.hasNextLine())    {
                String line = scanner.nextLine().replaceAll(" ", "");
                line = line.toUpperCase();
                dictionary.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean containsWord(String word)    {
        return dictionary.contains(word.toUpperCase());
    }

    public boolean containsPrefix(String prefix)    {
        for (String s: dictionary)  {
            if (s.startsWith(prefix))
                return true;
        }
        return false;
    }
}
