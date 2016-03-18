/**
 * Created by Artem_Lomako on 3/15/2016.
 */
public class Tuple {
    private int weight;
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