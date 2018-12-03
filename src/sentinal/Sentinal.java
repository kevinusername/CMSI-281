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

    Sentinal (String posFile, String negFile) throws FileNotFoundException {
        // TODO: load files into PhraseHash fields appropriately
    }


    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------

    public void loadSentiment (String phrase, boolean positive) {
        throw new UnsupportedOperationException();
    }

    public void loadSentimentFile (String filename, boolean positive) throws FileNotFoundException {
        throw new UnsupportedOperationException();
    }

    public String sentinalyze (String filename) throws FileNotFoundException {
        throw new UnsupportedOperationException();
    }


    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------

    // TODO: add your helper methods here!

}
