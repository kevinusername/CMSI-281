package cardGame;

/**
 * Extension of the basic ForneymonCard class to add the ability flip face orientation
 *
 * @author Kevin Peters
 */
public class FlippingForneymonCard extends ForneymonCard {
    public boolean faceDown;

    /**
     * Constructor for when no arguments are given
     * Sets default values for all fields
     */
    public FlippingForneymonCard() {
        name = "MissingNu";
        type = "Burnymon";
        faceDown = true;
    }

    /**
     * Constructor for when arguments are given
     * Checks for valid type input
     *
     * @param inName desired name for new card
     * @param inType desired type, must pass checkType()
     * @param inFace accepts "Up" or "Down" to set boolean faceDown
     */
    public FlippingForneymonCard(String inName, String inType, String inFace) {
        checkType(inType);

        name = inName;
        type = inType;

        if (inFace.equals("Up")) {
            faceDown = false;
        } else if (inFace.equals("Down")) {
            faceDown = true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Inverts cards faceDown value
     */
    public void flip() {
        faceDown = !faceDown;
    }

    /**
     * Attemps to check if two cards are the same
     * Has seperate return values for 3 scenarios:
     * 1. Both the cards are face up and both name and type match (returns 1)
     * 2. One of the cards is not face up (returns 2)
     * 3. Else (implying cards are face up and do not match) returns 0
     *
     * @param other another FlippingForneymonCard to compare this one to
     * @return an int (0, 1, or 2) depending on scenarios listed above
     */
    public int match(FlippingForneymonCard other) {
        if (faceDown || other.faceDown) {
            return 2;
        } else if (!faceDown && !other.faceDown && name.equals(other.name) && type.equals(other.type)) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * Special case for if card is down
     *
     * @return Type and name of this Forneymon
     */
    @Override
    public String toString() {
        if (faceDown) {
            return "?: ?";
        }
        return (type + ": " + name);
    }


}
