/**
 * Class Tuple used to save term and weight of term that used in RWayTrie class.
 * The Tuple objects encapsulates information that need to RWayTrie structures
 * and these objects are mutable and don't have some functionality that can
 * manage this objects. Tuple class is standard entity class that have only
 * fields and standard methods like hashCode, equals, toString.
 *
 * @author Lomako Artem
 * @version     %I%, %G%
 * @since       1.0
 */
public class Tuple {

    /**
     * Weight of word
     */
    private int weight;

    /**
     * Field that contain some word
     */
    private String word;

    public Tuple(String word, int weight) {
        this.weight = weight;
        this.word = word;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}