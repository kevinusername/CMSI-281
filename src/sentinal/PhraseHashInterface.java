package sentinal;

public interface PhraseHashInterface {

    /*
     * Returns the number of phrases contained in
     * the PhraseHash
     */
    public int size ();

    /*
     * Returns true if the PhraseHash is empty,
     * false otherwise
     */
    public boolean isEmpty ();

    /*
     * Inserts the given phrase into the PhraseHash;
     * duplicates should be ignored
     */
    public void put (String s);

    /*
     * Returns the given phrase if it exists in the
     * PhraseHash, null otherwise
     */
    public String get (String s);

    /*
     * Returns the length of the longest phrase stored
     * in the PhraseHash, or 0 if the PhraseHash is empty
     */
    public int longestLength ();

}
