import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

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

        List<Map<String, Integer>> resultList = new LinkedList<>();
        Map<String, Integer> sumMap = new HashMap<>();
        for (Review review : dataOperations.getReviewList().get(0).getValue()) {
            Map<String, Integer> tempMap = Process.transformStringToWordMap(review.getSubj());
            resultList.add(tempMap);
            Map<String, Integer> finalSumMap = sumMap;
            tempMap.forEach((k, v) -> finalSumMap.merge(k, v, Integer::sum));
        }


        //System.out.println(resultList);
        sumMap = sumMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)
                );
        System.out.println(sumMap);
    }
}
