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

    private int parseLine(String[] words, int phraseLength) {
        if (phraseLength > words.length) { return 0; }

        String[] phrases = generatePhrases(words, phraseLength);

        return getKarma(phrases);
    }

    private String[] generatePhrases(String[] words, int phraseLength) {
        String[] phrases = new String[words.length - phraseLength + 1];

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

    private int getKarma(String[] words) {
        int karma = 0;
        for (String word : words) {
            if (posHash.get(word) != null) karma++;
            else if (negHash.get(word) != null) karma--;
        }
        return karma;
    }

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
