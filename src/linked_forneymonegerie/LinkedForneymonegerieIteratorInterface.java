package linked_forneymonegerie;

public interface LinkedForneymonegerieIteratorInterface {
    
    boolean isValid ();
    boolean hasNext ();
    boolean hasPrev ();
    String getType ();
    void next ();
    void prev ();
    void replaceAll (String typeToReplaceWith);
    
}