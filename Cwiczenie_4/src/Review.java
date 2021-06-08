import java.util.List;

public class Review {
    private int id;
    private int label3;
    private int label4;
    private float rating;
    private String subj;

    public Review(int id) {
        this.id = id;
    }

    public Review(int id, int label3, int label4, float rating, String subj) {
        this.id = id;
        this.label3 = label3;
        this.label4 = label4;
        this.rating = rating;
        this.subj = subj;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel3(int label3) {
        this.label3 = label3;
    }

    public void setLabel4(int label4) {
        this.label4 = label4;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setSubj(String subj) {
        this.subj = subj;
    }

    public int getId() {
        return id;
    }

    public int getLabel3() {
        return label3;
    }

    public int getLabel4() {
        return label4;
    }

    public float getRating() {
        return rating;
    }

    public String getSubj() {
        return subj;
    }

    public String myToString(List<String> words) {
        StringBuilder sb = new StringBuilder();
        String delimiters = "\\s+|,\\s*|\\.\\s*";
        String[] wordArray = subj.split(delimiters);

        for (String word : wordArray) {
            if (word.length() > 1)
                sb.append(words.indexOf(word)).append(", ").append(this.label3).append(", ").append(this.rating).append("\n");
        }
        return sb.toString();
    }
}
