package cardGame;

/**
 * Basic Forneymon card that holds a given name and type
 *
 * @author Kevin Peters
 */
public class ForneymonCard {
    public String name;
    public String type;

    /**
     * Constructor for if no args are given
     */
    public ForneymonCard() {
        name = "MissingNu";
        type = "Burnymon";
    }

    /**
     * Constructor for when args are given.
     * Uses checkType to test input type value
     * throw error if name is blank
     *
     * @param inName desired name of Forneymon
     * @param inType desired type of Forneymon
     */
    public ForneymonCard(String inName, String inType) {
        checkType(inType);
        if (inName.equals("")) {
            throw new IllegalArgumentException();
        }

        name = inName;
        type = inType;
    }

    /**
     * Ensures that an input "type" value is one of the three valid types
     * 1. Burnymon
     * 2. Dampymon
     * 3. Leafymon
     * Throws an error if type != one of these
     *
     * @param type desired type of Forneymon
     */
    public void checkType(String type) {
        if (!(type.equals("Burnymon") || type.equals("Dampymon") || type.equals("Leafymon"))) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return Prints out "'type': 'name'"
     */
    @Override
    public String toString() {
        return (type + ": " + name);
    }
}
