package linked_forneymonegerie;

public interface LinkedForneymonegerieInterface {

    boolean empty ();
    int size ();
    int typeSize ();
    boolean collect (String typeToAdd);
    boolean release (String typeToRelease);
    int countType (String typeToCount);
    void releaseType (String typeToNuke);
    boolean contains (String typeToCheck);
    String rarestType ();
    LinkedForneymonegerie clone ();
    void trade (LinkedForneymonegerie other);
    LinkedForneymonegerie.Iterator getIterator ();
    
}