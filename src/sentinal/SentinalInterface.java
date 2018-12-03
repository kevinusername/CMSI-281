package sentinal;

import java.io.FileNotFoundException;

public interface SentinalInterface {

    /*
     * Adds a single sentiment phrase to the Sentinal;
     * parameter positive should be true if the phrase is
     * a positive sentiment, and false if negative
     */
    public void loadSentiment (String phrase, boolean positive);

    /*
     * Adds all sentiment phrases located in the given file
     * to the Sentinal; parameter positive should be true if
     * the file contains positive sentiments, and false if negative
     */
    public void loadSentimentFile (String filename, boolean positive) throws FileNotFoundException;

    /*
     * Analyzes the text in the given file and will determine,
     * based on the Sentinal's sentiment database, whether it has
     * a positive, neutral, or negative tone
     */
    public String sentinalyze (String filename) throws FileNotFoundException;

}
