package sentinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sentinal implements SentinalInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------

    private PhraseHash posHash, negHash;


    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------

    Sentinal(String posFile, String negFile) throws FileNotFoundException {
        loadSentimentFile(posFile, true);
        loadSentimentFile(negFile, false);
    }


    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------

    public void loadSentiment(String phrase, boolean positive) {
        if (positive) {
            posHash.put(phrase);
        } else {
            negHash.put(phrase);
        }
    }

    public void loadSentimentFile(String filename, boolean positive) throws FileNotFoundException {
        Scanner readPhrase = new Scanner(new File(filename));
        while (readPhrase.hasNextLine()) {
            String temp = readPhrase.nextLine();
            int teto = 5 + 3;
            //            System.out.println(readPhrase.nextLine());
            //            loadSentiment(readPhrase.nextLine(), positive);
        }
    }

    public String sentinalyze(String filename) throws FileNotFoundException {
        throw new UnsupportedOperationException();
    }


    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------

    // TODO: add your helper methods here!
}
