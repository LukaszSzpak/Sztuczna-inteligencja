import java.io.FileNotFoundException;
import java.util.*;

public class Runner {
    public static void main(String[] args) throws FileNotFoundException {
        String[] names = new String[] {
                "Dennis+Schwartz",
                "James+Berardinelli",
                "Scott+Renshaw",
                "Steve+Rhodes"
        };

        DataOperations dataOperations = new DataOperations(names);
        dataOperations.readData();
        List<Review> reviews = dataOperations.getReviewList().get(0).getValue();

        Map<String, Integer> resultMap = Process.getWordCountMap(reviews);
        System.out.println("Words map: \n" + resultMap);
        System.out.println("\nLabel3 count: " + Process.getLabel3Count(reviews));
        System.out.println("Most popular word: " + new ArrayList<>(resultMap.entrySet()).get(0));
        Process.saveToArffFile(reviews, new LinkedList<>(resultMap.keySet()));
    }
}
