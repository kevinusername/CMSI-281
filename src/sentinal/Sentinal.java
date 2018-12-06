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
        posHash = new PhraseHash();
        negHash = new PhraseHash();
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
            loadSentiment(readPhrase.nextLine(), positive);
        }
    }

    public String sentinalyze(String filename) throws FileNotFoundException {
        Scanner document = new Scanner(new File(filename));
        int maxPhraseLength = (posHash.longestLength() > negHash.longestLength()) ? posHash.longestLength()
                                                                                  : negHash.longestLength();
        int karmaScore = 0;

        while (document.hasNextLine()) {
            String[] words = document.nextLine().split(" ");
            for (int i = 0; i < maxPhraseLength; i++) {
                karmaScore += parseLine(words, i);
            }
        }

        return judgement(karmaScore);
    }


    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------

    /**
     * Takes in a line and returns the sum of scores for phrases of all lengths up to the maximum.
     *
     * @param words        all words, separated by " ", in the line
     * @param phraseLength The length of the longest phrase in posHash and negHash
     * @return Net score of positive and negative phrases
     */
    private int parseLine(String[] words, int phraseLength) {
        if (phraseLength > words.length) { return 0; }

        String[] phrases = generatePhrases(words, phraseLength);

        return getKarma(phrases);
    }

    /**
     * @param words        all words, separated by " ", in the line
     * @param phraseLength The length of the longest phrase in posHash and negHash
     * @return an array containing every String of adjacent words of length phraseLength
     */
    private String[] generatePhrases(String[] words, int phraseLength) {
        String[] phrases = new String[words.length - phraseLength + 1];

        // Iterate through each word, and append [phraseLength] following words to create new String
        for (int i = 0; i <= words.length - phraseLength; i++) {
            StringBuilder phrase = new StringBuilder();
            for (int j = i; j < i + phraseLength; j++) {
                phrase.append(words[j]);
                phrase.append(" ");
            }
            phrases[i] = phrase.toString().trim();
        }

        return phrases;
    }

    /**
     * @param phrases the list of phrases to check for positivity/negativity
     * @return Net score of positive and negative phrases for [phrases]
     */
    private int getKarma(String[] phrases) {
        int karma = 0;
        for (String word : phrases) {
            if (posHash.get(word) != null) karma++;
            else if (negHash.get(word) != null) karma--;
        }
        return karma;
    }

    /**
     * Given an integer karmaScore, returns a String indicating if the overall score was positive, negative, or neutral
     *
     * @param karmaScore positivity/negativity score to evaluate
     * @return a String indicating if the overall score was positive, negative, or neutral
     */
    private String judgement(int karmaScore) {
        if (karmaScore > 0) {
            return "positive";
        } else if (karmaScore < 0) {
            return "negative";
        } else {
            return "neutral";
        }
    }
}
