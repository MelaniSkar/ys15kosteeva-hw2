package ua.yandex.shad.tries;

public class Tuple {

    private String term;
    private int weight;

    public Tuple(String term, int weight) {
        this.term = term;
        this.weight = weight;
    }

    public String getTerm() {
        return term;
    }

    public int getWeight() {
        return weight;
    }

    public void setTerm(String t) {
        this.term = t;
    }

    public void setWeight(int w) {
        this.weight = w;
    }

}
